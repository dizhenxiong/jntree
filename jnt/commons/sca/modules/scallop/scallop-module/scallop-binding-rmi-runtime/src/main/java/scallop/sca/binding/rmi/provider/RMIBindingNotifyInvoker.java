package scallop.sca.binding.rmi.provider;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ConnectException;
import java.rmi.Remote;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.tuscany.sca.invocation.DataExchangeSemantics;
import org.apache.tuscany.sca.invocation.Invoker;
import org.apache.tuscany.sca.invocation.Message;
import org.oasisopen.sca.ServiceRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.remoting.rmi.RmiInvocationHandler;
import org.springframework.remoting.support.RemoteInvocation;

import scallop.core.ScallopClient;
import scallop.core.exception.ScallopRemoteException;
import scallop.core.loadbalance.DefaultLoadBalancer;
import scallop.core.util.StringSupport;
import scallop.sca.binding.rmi.exception.NotifyException;
import scallop.sca.host.rmi.RMIHost;

public class RMIBindingNotifyInvoker implements Invoker, DataExchangeSemantics, IInvoker{
	private final static Logger logger = LoggerFactory
			.getLogger(RMIBindingNotifyInvoker.class);
	private RMIHost rmiHost;
	private String registryCenter;
	private String registryName;
	private String svcName;
	private Method remoteMethod;
	private RMINotifyObserver observer;
	private static final String POINT = ".";
	private static final int retry = 2;//防止 dao 因为 mysql时间过长 connection断掉 重试 一次

	public RMIBindingNotifyInvoker(RMIHost rmiHost,
			String registryCenter, String registryName, String svcName,
			Method remoteMethod) throws ConnectException,
			NumberFormatException, ScallopRemoteException {
		this.rmiHost = rmiHost;
		this.remoteMethod = remoteMethod;
		this.svcName = svcName;
		ScallopClient sc = ScallopClient.getInstance();
		this.registryName = registryName;
		this.registryCenter = sc.getRealRegistryCenter(registryCenter);
		observer = new RMINotifyObserver(this.registryCenter, this.registryName);
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

	public Object invokeTarget(final Object payload)throws NotifyException, InvocationTargetException, SecurityException,NoSuchMethodException, IllegalArgumentException, IllegalAccessException{
		long startTime = 0L;
		if (logger.isInfoEnabled()) {
			startTime = System.currentTimeMillis();
			statMethodExecute(svcName, remoteMethod.getName());
		}
		Object obj = null;
		boolean result = true;//表明最终结果
		StringBuffer sb = new StringBuffer("Notify invokeTarget Exception: ");
		DefaultLoadBalancer<RMIRemote> rr = observer.getLoadBalancer();
		for(RMIRemote rrm : rr.getResources()){
			boolean tmpFlag = false;
			int nu = retry;
			while((nu-- > 0) && !tmpFlag){//如果不成功重试两次
				Remote proxy = null;
				try {
					if(proxy == null){
						proxy = rmiHost.findService(StringSupport.getURI(rrm.getHost(), rrm.getPort(), svcName));
					}
					if(logger.isDebugEnabled())
						logger.debug("call notify Mode  proxy = " + rrm.getHost() + "; " + rrm.getPort());
					if (proxy instanceof RmiInvocationHandler) {
						RemoteInvocation invocation = new RemoteInvocation(remoteMethod.getName(), remoteMethod.getParameterTypes(), (Object[]) payload);
						Method method = proxy.getClass().getMethod(WRAPPED_METHOD_NAME, new Class[] { RemoteInvocation.class });
						obj = method.invoke(proxy, invocation);
					} else {
						remoteMethod = proxy.getClass().getMethod(remoteMethod.getName(), remoteMethod.getParameterTypes());
						if(payload != null && !payload.getClass().isArray()){
							obj = remoteMethod.invoke(proxy, payload);
						}else{
							obj = remoteMethod.invoke(proxy, (Object[]) payload);
						}
				
			
					}
					tmpFlag = true;//成功了
				} catch (Exception exp) {
					sb.append(svcName).append(POINT).append(remoteMethod.getName()).append(" ").append(rrm.getHost()).append(" : ").append(rrm.getPort()).append(" time: ").append(System.currentTimeMillis() - startTime).append("; ");
					logger.error(new StringBuffer("RMIBindingMasterSlave invokeTarget Exception: ").append(svcName).append(POINT).append(remoteMethod.getName()).append(" ").append(rrm.getHost()).append(" : ").append(rrm.getPort()).append(" time: ").append(System.currentTimeMillis() - startTime).toString(), exp);
				}
			}
			result = result & tmpFlag;// tmpFlag表明每个notifyserver的最终执行状态 ，result表明 本次执行有没有那台机器没有通知成功
	
		}
		if(!result){//只要失败一个就抛出异常
			throw new NotifyException(sb.toString());
		}
		return obj;
			
	}

	public boolean allowsPassByReference() {
		// RMI always pass by value
		return true;
	}
	
	
}
