package scallop.core;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import org.apache.log4j.Level;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.Client;
//import org.restlet.Request;
import org.restlet.data.Method;
import org.restlet.data.Protocol;
import org.restlet.data.Response;
//import org.restlet.Response;

import scallop.core.exception.ScallopRemoteException;

/**
 * scallop security rest implement
 * 
 * @author songkun1
 */
public class ScallopSecurityProcessor {
	private static final Logger logger = Logger.getLogger(ScallopSecurityProcessor.class);
	protected Map<String, Security> securityMap = new ConcurrentHashMap<String, Security>();

	private ScallopSecurityProcessor() {

	}

	static class SingletonHolder {
		static ScallopSecurityProcessor instance = new ScallopSecurityProcessor();
	}

	private static ScallopSecurityProcessor instance;
	protected final ScheduledExecutorService exec = Executors
			.newSingleThreadScheduledExecutor();

	public synchronized static ScallopSecurityProcessor getInstance() {
		if (instance == null) {
			instance = new ScallopSecurityProcessor();
			try {
				instance.securityMap = instance.getSecurityMap(false);
			} catch (ScallopRemoteException e1) {
				e1.printStackTrace();
			}
			Runnable task = new Runnable() {
				@Override
				public void run() {
					try {
						Map<String, Security> map = instance
								.getSecurityMap(true);
						instance.securityMap = map;
					} catch (ScallopRemoteException e) {
						e.printStackTrace();
					}
				}
			};
			instance.exec.scheduleAtFixedRate(task, 5, 5, TimeUnit.MINUTES);
		}
		return instance;
	}

	public Security getSecurity(String projectId) {

		if (projectId == null) {
			throw new NullPointerException("projectId is null!");
		}
		Security security = securityMap.get(projectId);
		return security;
	}

	public Map<String, Security> getSecurityMap(boolean isFromSchedule)
			throws ScallopRemoteException {
		Map<String, Security> map = new ConcurrentHashMap<String, Security>();
		Client client = new Client(Protocol.HTTP);
		String url = ScallopConstants.NEW_REGISTRY_CENTER+ ScallopConstants.NEW_REGISTRY_CENTER_PORT+ ScallopConstants.REST_PROJECT;
//		Request request = new Request(Method.GET, url);
		Response response = client.get(url);
		if (response.getStatus().isSuccess()) {
			if (response.isEntityAvailable()) {
				try {
					JSONObject jsonObjects = new JSONObject(response
							.getEntity().getText());
					JSONArray jsonArray = (JSONArray) jsonObjects
							.get("projects");
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObject = jsonArray.getJSONObject(i);
						Security security = new Security();
						security.setId(jsonObject.getLong("id"));
						security.setSecretKey(jsonObject.getString("secretKey"));
						security.setName(jsonObject.getString("name"));
						map.put(security.getId().toString(), security);
					}
					return map;
				} catch (IOException e) {
					logger.info("IOException from scallopsecurityprocessor!");
					if (isFromSchedule) {
						return securityMap;
					}
					throw new ScallopRemoteException(e.getMessage(), e);
				} catch (JSONException e) {
					logger.info("JSONException from scallopsecurityprocessor!");
					if (isFromSchedule) {
						return securityMap;
					}
					throw new ScallopRemoteException(e.getMessage(), e);
				}
			} else {
				if(logger.isDebugEnabled())
					logger.debug("response.isEntityAvailable()==false!");
				if (isFromSchedule) {
					return securityMap;
				}
			}
		} else {
			if (isFromSchedule) {
				if(logger.isDebugEnabled())
					logger.debug("response.getStatus().isSuccess()==false!");
				return securityMap;
			}
		}
		if(client != null && !client.isStopped()){
			try{
				client.stop();
			}catch(Exception e){}
		}
		throw new ScallopRemoteException(
				"get projects error. newRegistryCenter:"
						+ ScallopConstants.NEW_REGISTRY_CENTER
						+ ScallopConstants.NEW_REGISTRY_CENTER_PORT);
	}

}
