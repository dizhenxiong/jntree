package scallop.sca.binding.rmi.provider;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.rmi.Remote;
import java.rmi.UnexpectedException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.tuscany.sca.invocation.DataExchangeSemantics;
import org.apache.tuscany.sca.invocation.Invoker;
import org.apache.tuscany.sca.invocation.Message;
import org.oasisopen.sca.ServiceRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.remoting.rmi.RmiInvocationHandler;
import org.springframework.remoting.support.RemoteInvocation;

import scallop.core.ScallopClient;
import scallop.core.ScallopConstants;
import scallop.core.exception.ScallopRemoteException;
import scallop.core.loadbalance.MasterSlaveLoadBalancer;
import scallop.core.loadbalance.ResourceLoader;
import scallop.core.util.StringSupport;
import scallop.sca.host.rmi.RMIHost;

public class RMIBindingMasterSlaveLoadBalanceInvoker implements Invoker, DataExchangeSemantics, IInvoker {
	private final static Logger logger = LoggerFactory
			.getLogger(RMIBindingMasterSlaveLoadBalanceInvoker.class);
	private RMIHost rmiHost;
	private String registryCenter;
	private String registryName;
	private String svcName;
	private Method remoteMethod;
	private RMIMasterSlaveObserver observer;
	private static final String POINT = ".";
	private static final int retry = 2;//防止 dao 因为 mysql时间过长 connection断掉 重试 一次

	private final static ScheduledExecutorService scheduler = Executors
			.newSingleThreadScheduledExecutor();
	static {
		if (logger.isInfoEnabled()) {
			scheduler.scheduleAtFixedRate(
					new Runnable() {
						@Override
						public void run() {
							for (Entry<String, Long> entry : methodExecuteMap
									.entrySet()) {
								logger
										.info("method invoke stat: "
												+ entry.getKey()
												+ " count: "
												+ entry.getValue()
												+ " interval: "
												+ ScallopClient
														.getInstance()
														.getRmiExecuteTimesPrintInterval()
												+ " rate: "
												+ entry.getValue()
												/ (ScallopClient
														.getInstance()
														.getRmiExecuteTimesPrintInterval() / 60)
												+ " t/m");
							}
							methodExecuteMap = new ConcurrentHashMap<String, Long>();
						}
					}, ScallopClient.getInstance()
							.getRmiExecuteTimesPrintInterval(),
					ScallopClient.getInstance()
							.getRmiExecuteTimesPrintInterval(),
					TimeUnit.SECONDS);
		}
	}

	public RMIBindingMasterSlaveLoadBalanceInvoker(RMIHost rmiHost,
			String registryCenter, String registryName, String svcName,
			Method remoteMethod) throws ConnectException,
			NumberFormatException, ScallopRemoteException {
		this.rmiHost = rmiHost;
		this.remoteMethod = remoteMethod;
		this.svcName = svcName;
		ScallopClient sc = ScallopClient.getInstance();
		this.registryName = registryName;
		this.registryCenter = sc.getRealRegistryCenter(registryCenter);
		observer = new RMIMasterSlaveObserver(this.registryCenter, this.registryName);
		sc.registerObserver(observer);
		observer.update(false);
	}

	public Message invoke(Message msg) {
		try {
			Object[] args = msg.getBody();
			Object resp = invokeTarget(args);
			msg.setBody(resp);
		} catch (InvocationTargetException e) {
			if (e.getCause() instanceof ServiceRuntimeException) {
				msg.setFaultBody(e.getCause().getCause());
			} else {
				msg.setFaultBody(e.getCause());
			}
		} catch (Exception e) {
			msg.setFaultBody(e);
		}

		return msg;
	}

	private static Map<String, Long> methodExecuteMap = new ConcurrentHashMap<String, Long>();

	private void statMethodExecute(String svcName, String method) {
		String key = new StringBuffer(svcName).append(POINT).append(method)
				.toString();
		Long counter = methodExecuteMap.get(key);
		if (counter == null) {
			counter = 1L;
		} else {
			counter++;
		}
		methodExecuteMap.put(key, counter);
	}

	public Object invokeTarget(final Object payload)throws InvocationTargetException, SecurityException,NoSuchMethodException, IllegalArgumentException, IllegalAccessException{
		long startTime = 0L;
		if (logger.isInfoEnabled()) {
			startTime = System.currentTimeMillis();
			statMethodExecute(svcName, remoteMethod.getName());
		}
		ResourceLoader<MasterSlaveLoadBalancer<RMIRemote>> rr = observer.getLoadBalancer().get();
		MasterSlaveLoadBalancer<RMIRemote> mslb = rr.getResource();
		for(RMIRemote rrm : mslb.getResources()){
			for(int re = 0; re < retry; re++){//防止 mysql connection 断掉
				Remote proxy = null; //proxy cant be cached 
				try {
					if(proxy == null){
						proxy = rmiHost.findService(StringSupport.getURI(rrm.getHost(), rrm.getPort(), svcName));
					}
					Object obj = null;
					if (proxy instanceof RmiInvocationHandler) {
						RemoteInvocation invocation = new RemoteInvocation(remoteMethod.getName(), remoteMethod.getParameterTypes(), (Object[]) payload);
						Method method = proxy.getClass().getMethod(WRAPPED_METHOD_NAME, new Class[] { RemoteInvocation.class });
						obj = method.invoke(proxy, invocation);
						if(logger.isDebugEnabled())
							logger.debug("proxy is RmiInvocationHandler " + rrm.getHost()+"; "+ rrm.getPort()+"; "+ svcName + "; " +remoteMethod.getName() + "; obj=" + obj);
					} else {
						remoteMethod = proxy.getClass().getMethod(remoteMethod.getName(), remoteMethod.getParameterTypes());
						if(payload != null &&  !payload.getClass().isArray()){
							obj = remoteMethod.invoke(proxy, payload);
						}else{
							obj = remoteMethod.invoke(proxy, (Object[]) payload);
						}
						if(logger.isDebugEnabled())
							logger.debug("proxy is not RmiInvocationHandler " + rrm.getHost()+"; "+ rrm.getPort()+"; "+ svcName + "; " +remoteMethod.getName() + "; obj="+obj);
					}
					if (logger.isInfoEnabled()) {
						long time = System.currentTimeMillis() - startTime;
						if (time > ScallopClient.getInstance().getRmiProcessTimeThreshold()) {
							Object[] objs = (Object[]) payload;
							if (ScallopClient.getInstance().getRmiShowParameters()) {
								StringBuffer sb = new StringBuffer();
								for (int i = 0, length = objs.length; i < length; i++) {
									sb.append(i).append(":").append(objs[i])
											.append(")");
								}
								logger.info(new StringBuffer("method process stat: ").append(svcName).append(POINT).append(remoteMethod.getName()).append(" time: ").append(time).append(" params:").append(sb.toString()).toString());
							} else {
								logger.info(new StringBuffer("method process stat: ").append(svcName).append(POINT).append(remoteMethod.getName()).append(" time: ").append(time).toString());
							}
						}
					}
					return obj;
				} catch (Exception exp) {
					logger.error(new StringBuffer("RMIBindingMasterSlave invokeTarget Exception: ").append(svcName).append(POINT).append(remoteMethod.getName()).append(" time: ").append(System.currentTimeMillis() - startTime).toString(), exp);
				}
			}

		}
		throw new InvocationTargetException(new Exception("invokeTarget RMI master slaver error serviceName = "+svcName + "; memtod = " + remoteMethod.getName()));
	}

	public boolean allowsPassByReference() {
		// RMI always pass by value
		return true;
	}
	
	
	
	
}
