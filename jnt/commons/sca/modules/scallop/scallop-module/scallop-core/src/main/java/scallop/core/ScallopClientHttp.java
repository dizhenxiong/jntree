package scallop.core;

import java.io.IOException;
import java.io.InputStream;
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
//import org.restlet.Request;
import org.restlet.data.Method;
import org.restlet.data.Protocol;
//import org.restlet.Response;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.log4j.Logger;

import scallop.core.exception.ScallopRemoteException;

/**
 * scallop client rest implement
 * 
 *@author songkun1
 */
public class ScallopClientHttp implements Subject {
//	private static final Logger logger = Logger.getLogger(ScallopClient.class
//			.getName());

	private static Logger logger = Logger.getLogger(ScallopClientHttp.class);
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

	private ScallopClientHttp() {
		InputStream inputStream = getDefaultClassLoader().getResourceAsStream(
				SCALLOP_PROPERTIES);
		try {
			if (inputStream != null) {
				properties.load(inputStream);
				setSecretKey(properties.getProperty("secretKey", null));
				setProjectId(properties.getProperty("projectId", null));
				try {
					setClientIp(InetAddress.getLocalHost().getHostAddress());
					String path = ScallopClientHttp.class.getProtectionDomain().getCodeSource().getLocation().getFile();
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
		static ScallopClientHttp instance = new ScallopClientHttp();
	}

	private static ScallopClientHttp instance;

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

	public synchronized static ScallopClientHttp getInstance() {
		if (instance == null) {
			instance = new ScallopClientHttp();
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

//		Client client = new Client(Protocol.HTTP);

		String[] strings = registryCenter.split(COMMA);
		HttpClient client = createDefaultHttpClient();
		for (String rc : strings) {
			String url = new StringBuffer(rc).append(SLASH).append(
					ScallopConstants.RESOURCES_HTTP).append(
					registryName).toString();
			
			HttpGet get = new HttpGet(url);
			HttpResponse response = null;
			try {
				response = client.execute(get);
			} catch (ClientProtocolException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
//			Request request = new Request(Method.GET, url);
			if (response != null && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				if (response.getEntity() != null) {
					try {
						JSONObject jsonObject = new JSONObject(response
								.getEntity().getContent());
						// logger.log(Level.INFO, "resource:"+
						// jsonObject.toString());
						resource = parser.parse(jsonObject);
						if(resource != null){
							registryCenterMap.put(new StringBuffer(registryCenter)
							.append(SPLIT).append(registryName).toString(),
							resource);
							return resource;
						}
					} catch (Exception e) {
						logger.error("registrycenter IOException", e);
//						throw new ScallopRemoteException(e.getMessage(), e);
					}finally {
						if(get != null)get.releaseConnection();
						if(client != null)client.getConnectionManager().closeExpiredConnections();
					}
				}
			}
		}
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
		if(client != null)client.getConnectionManager().closeExpiredConnections();
		throw new ScallopRemoteException("get resources error. registryCenter:"
				+ registryCenter + " registryName:" + registryName);
	}

	private List<Observer> observers = new ArrayList<Observer>();

	@Override
	public void notifyObservers() {
		// logger.info("observers size:"+observers.size());
		for (Observer o : observers) {
			try {
				// logger.log(Level.INFO,"observers.notifyObservers(o)");
				o.update(true);
			} catch (ScallopRemoteException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public Observer registerObserver(Observer o) {

		observers.add(o);
		return o;
	}

	@Override
	public void removeObserver(Observer o) {
		int i = observers.indexOf(o);
		if (i > -1) {
			observers.remove(i);
		}
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
			cl = ScallopClientHttp.class.getClassLoader();
		}
		return cl;
	}
	
	private  static HttpClient createDefaultHttpClient(){
		HttpClient client = new DefaultHttpClient();
		return client;
	}
	
	

}
