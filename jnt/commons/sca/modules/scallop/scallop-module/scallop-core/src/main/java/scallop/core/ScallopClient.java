package scallop.core;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.Client;

import org.restlet.data.Method;
import org.restlet.data.Protocol;
import org.restlet.data.Response;

import org.apache.log4j.Logger;

import scallop.core.exception.ScallopRemoteException;

/**
 * scallop client rest implement
 * 
 *@author songkun1
 */
public class ScallopClient implements Subject {
//	private static final Logger logger = Logger.getLogger(ScallopClient.class
//			.getName());

	private static Logger logger = Logger.getLogger(ScallopClient.class);
	private static final String SUFFIX = "}";
	private static final String PREFIX = "${";
	private static final String SCALLOP_PROPERTIES = "scallop.properties";
	private static final String SPLIT = "|||";
	private static final String COMMA = ",";
	private static final String SLASH = "/";
	private static final String SCALLOP_REGISTRY_CENTER = "scallop.registry.center";
	private static final String SCALLOP_PERFORMACE_RMI_PROCESSTIME_THRESHOLD = "scallop.performace.rmi.processTimeThreshold";
	private static final String SCALLOP_PERFORMACE_RMI_SHOW_PARAMETERS = "scallop.performace.rmi.showParameters";
	private static final String SCALLOP_PERFORMACE_RMI_EXECUTE_TIMES_PRINT_INTERVAL = "scallop.performace.rmi.executeTimesPrintInterval";
	private String projectId;
	private String projectName;

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	private String clientIp;
	private String secretKey;
	protected Map<String, Resource> registryCenterMap = new ConcurrentHashMap<String, Resource>();

	// failover:(10.10.82.102:8089,10.10.82.103:8089)?max_fails=2&fail_interval=30000;failover:(10.10.82.101:8089,10.10.82.103:8089)?max_fails=2&fail_interval=30000
	private Properties properties = new Properties();

	private ScallopClient() {
		InputStream inputStream = getDefaultClassLoader().getResourceAsStream(
				SCALLOP_PROPERTIES);
		try {
			if (inputStream != null) {
				properties.load(inputStream);
				setSecretKey(properties.getProperty("secretKey", null));
				setProjectId(properties.getProperty("projectId", null));
				try {
					setClientIp(InetAddress.getLocalHost().getHostAddress());
					String path = ScallopClient.class.getProtectionDomain().getCodeSource().getLocation().getFile();
					if (path.contains("lib/")) {
						path = path.substring(0, path.lastIndexOf("lib/"));
					}
					path = path.replace("WEB-INF/", "");
					if (path.endsWith("/")) {
						path = path.substring(0, path.length() - 1);
					}
					path = path.substring(path.lastIndexOf("/") + 1);
					setProjectName(path);
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	static class SingletonHolder {
		static ScallopClient instance = new ScallopClient();
	}

	private static ScallopClient instance;

	protected final ScheduledExecutorService exec = Executors
			.newSingleThreadScheduledExecutor();

	public String getProperty(String name) {
		return properties.getProperty(name, null);
	}

	public long getRmiProcessTimeThreshold() {
		String str = getProperty(SCALLOP_PERFORMACE_RMI_PROCESSTIME_THRESHOLD);
		if (str == null) {
			return 50;
		} else {
			return Long.parseLong(str);
		}
	}

	public boolean getRmiShowParameters() {
		String str = getProperty(SCALLOP_PERFORMACE_RMI_SHOW_PARAMETERS);
		if ("true".equalsIgnoreCase(str)) {
			return true;
		} else {
			return false;
		}
	}

	public long getRmiExecuteTimesPrintInterval() {
		String str = getProperty(SCALLOP_PERFORMACE_RMI_EXECUTE_TIMES_PRINT_INTERVAL);
		if (str == null) {
			return 3600000;
		} else {
			return Long.parseLong(str);
		}
	}

	public String getRealRegistryCenter(String registryCenter) {
		if (registryCenter == null) {
			registryCenter = getProperty(SCALLOP_REGISTRY_CENTER);
		} else if (registryCenter.startsWith(PREFIX)
				&& registryCenter.endsWith(SUFFIX)) {
			registryCenter = getProperty(registryCenter.replace(PREFIX, "")
					.replace(SUFFIX, ""));
		}
		return registryCenter;
	}

	public synchronized static ScallopClient getInstance() {
		if (instance == null) {
			instance = new ScallopClient();
			Runnable task = new Runnable() {
				@Override
				public void run() {
					// instance.registryCenterMap.clear();
					instance.notifyObservers();
				}
			};
			instance.exec.scheduleAtFixedRate(task, 5, 5, TimeUnit.MINUTES);
		}
		return instance;
	}

	public Resource getResource(String registryCenter, String registryName,
			boolean isFromSchedule, ResourceParser parser)
			throws ScallopRemoteException {
		if (registryName == null || registryCenter == null) {
			throw new NullPointerException(
					"registryCenter or registryName is null. registryCenter:"
							+ registryCenter + "registryName:" + registryName);
		}
		Resource resource = null;
		if (!isFromSchedule) {
			resource = registryCenterMap.get(new StringBuffer(registryCenter)
					.append(SPLIT).append(registryName).toString());
			if (resource != null) {
				if(logger.isDebugEnabled())
					logger.debug("resource = " + resource.toString());
				return resource;
			}
		}

		// logger.log(Level.INFO,
		// "get resource form registry center. registryCenter:" + registryCenter
		// + " registryName:" + registryName);

		Client client = new Client(Protocol.HTTP);
		String[] strings = registryCenter.split(COMMA);
		for (String rc : strings) {
			String url = new StringBuffer(rc).append(SLASH).append(
					ScallopConstants.RESOURCES).append(SLASH).append(
					registryName).toString();
//			Request request = new Request(Method.GET, url);
			Response response = client.get(url);
			if (response.getStatus().isSuccess()) {
				if (response.isEntityAvailable()) {
					try {
						JSONObject jsonObject = new JSONObject(response
								.getEntity().getText());
						// logger.log(Level.INFO, "resource:"+
						// jsonObject.toString());
						resource = parser.parse(jsonObject);
						if(resource != null){
							registryCenterMap.put(new StringBuffer(registryCenter).append(SPLIT).append(registryName).toString(), resource);
							if(response != null)
								response.release();
//							if(request != null)
//								request.release();
							break;
						}
					} catch (Exception e) {
						logger.error("registrycenter IOException", e);
					}
				}else{
					if(response != null)
						response.release();
				}
			}
			//释放连接
			if(response != null)
				response.release();
//			if(request != null)
//				request.release();
		}
		
		try {// 用完关闭连接
			if(client != null && !client.isStopped()){
				client.stop();
			}
		} catch (Exception e) {
			logger.error("client is stopped error", e);
		}
		if(resource != null)return resource;
		if (isFromSchedule) {
			resource = registryCenterMap.get(new StringBuffer(
					registryCenter).append(SPLIT).append(registryName)
					.toString());
			if (resource != null) {
				if(logger.isDebugEnabled())
					logger.debug("service center exception! resource from registryCenterMap!");
				return resource;
			}
		}

		throw new ScallopRemoteException("get resources error. registryCenter:"
				+ registryCenter + " registryName:" + registryName);
	}

	private List<WeakReference<Observer>> observers = new ArrayList<WeakReference<Observer>>();

	@Override
	public void notifyObservers() {
		// logger.info("observers size:"+observers.size());
		List<WeakReference<Observer>> remove = new ArrayList<WeakReference<Observer>>();
		for (WeakReference<Observer> ref : observers) {
			try {
				// logger.log(Level.INFO,"observers.notifyObservers(o)");
				if(ref != null){
					Observer o = ref.get();
					if(o != null)
						o.update(true);
					else
						remove.add(ref);
				}
			} catch (ScallopRemoteException e) {
				e.printStackTrace();
			}
		}
		if(remove != null && remove.size() > 0)
			observers.removeAll(remove);
		
	}

	@Override
	public Observer registerObserver(Observer o) {
		WeakReference<Observer> refrence = new WeakReference<Observer>(o); 
		observers.add(refrence);
		return o;
	}

	@Override
	public void removeObserver(Observer o) {
//		int i = observers.indexOf(o);
//		if (i > -1) {
//			observers.remove(i);
//		}
	}

	/**
	 * Return the default ClassLoader to use: typically the thread context
	 * ClassLoader, if available; the ClassLoader that loaded the ClassUtils
	 * class will be used as fallback.
	 * <p>
	 * Call this method if you intend to use the thread context ClassLoader in a
	 * scenario where you absolutely need a non-null ClassLoader reference: for
	 * example, for class path resource loading (but not necessarily for
	 * <code>Class.forName</code>, which accepts a <code>null</code> ClassLoader
	 * reference as well).
	 * 
	 * @return the default ClassLoader (never <code>null</code>)
	 * @see Thread#getContextClassLoader()
	 */
	private static ClassLoader getDefaultClassLoader() {
		ClassLoader cl = null;
		try {
			cl = Thread.currentThread().getContextClassLoader();
		} catch (Exception ex) {
			// Cannot access thread context ClassLoader - falling back to system
			// class loader...
		}
		if (cl == null) {
			// No thread context class loader -> use class loader of this class.
			cl = ScallopClient.class.getClassLoader();
		}
		return cl;
	}

}
