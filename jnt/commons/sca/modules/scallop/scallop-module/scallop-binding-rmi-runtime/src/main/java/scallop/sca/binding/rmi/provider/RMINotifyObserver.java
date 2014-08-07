package scallop.sca.binding.rmi.provider;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import scallop.core.Observer;
import scallop.core.Resource;
import scallop.core.ScallopClient;
import scallop.core.exception.ScallopRemoteException;
import scallop.core.loadbalance.DefaultLoadBalancer;
import scallop.core.loadbalance.LoadBalancer;
import scallop.core.util.StringSupport;
import scallop.core.util.StringSupport.CompositeData;

public class RMINotifyObserver implements Observer {
	private final static Logger logger = LoggerFactory
			.getLogger(RMINotifyObserver.class);
	private DefaultLoadBalancer<RMIRemote> loadBalancer;
	private String registryCenter;
	private String registryName;
	private static final String COLON = ":";
	
	public RMINotifyObserver(String registryCenter, String registryName) {
		this.registryCenter = registryCenter;
		this.registryName = registryName;
	}

	@Override
	public void update(boolean isFromSchedule) throws ScallopRemoteException {
		Resource resource = ScallopClient.getInstance().getResource(
				this.registryCenter, this.registryName, isFromSchedule,
				new RMIResourceParser());
		if(logger.isDebugEnabled() && resource != null){
			logger.debug("RMIObserver = " + resource.toString());
		}
	
		if (resource == null || resource.getResource() == null || resource.getResource().size() == 0) {
			logger.info("can not get resource. resource name:"
					+ this.registryName);
			throw new ScallopRemoteException(
					"can not get resource. resource name:" + this.registryName);
		}
		Set<RMIRemote> mslbs = new HashSet<RMIRemote>();
		for (String res : (List<String>)(resource.getResource())) {
			Set mslb = createResource(res);
			if (mslb != null) {
				mslbs.addAll(mslb);
			}
		}

		if (mslbs.size() == 0) {
			throw new ScallopRemoteException(
					"can not parse resource. resource name:"
							+ this.registryName);
		}

		if (loadBalancer == null) {
			loadBalancer = new DefaultLoadBalancer<RMIRemote>(mslbs);

		} else {
			Set<RMIRemote> newMslbs = new HashSet<RMIRemote>();
			Set<RMIRemote> oldMslbs = loadBalancer.getResources();
			for (RMIRemote rr : mslbs) {
				if (!oldMslbs.contains(rr)) {
					newMslbs.add(rr);
				}
			}
			Set<RMIRemote> uselessFolbs = new HashSet<RMIRemote>();
			for (RMIRemote rr : oldMslbs) {
				if (!mslbs.contains(rr)) {
					uselessFolbs.add(rr);
				}
			}
			if (!newMslbs.isEmpty()) {
				oldMslbs.addAll(newMslbs);
			}
			if (!uselessFolbs.isEmpty()) {
				oldMslbs.removeAll(uselessFolbs);
			}
		}
	}

	private static Set<RMIRemote> createResource(
			String res) throws ScallopRemoteException {
		CompositeData cd = StringSupport.parseComposite(res);
		String[] st = cd.getComponents();
		Set<RMIRemote> set = new HashSet<RMIRemote>();
		for (String rs : st) {
			String[] hostPort = rs.split(COLON);
			set.add(new RMIRemote(hostPort[0], hostPort[1]));
		}
		return set;

	}

	public DefaultLoadBalancer<RMIRemote> getLoadBalancer() {
		return loadBalancer;
	}
}
