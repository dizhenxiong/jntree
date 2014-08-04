package scallop.sca.binding.rmi.provider;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import scallop.core.Observer;
import scallop.core.Resource;
import scallop.core.ScallopClient;
import scallop.core.exception.ScallopRemoteException;
import scallop.core.loadbalance.LoadBalancer;
import scallop.core.loadbalance.MasterSlaveLoadBalancer;
import scallop.core.loadbalance.RoundRobinLoadBalancer;
import scallop.core.util.StringSupport;
import scallop.core.util.StringSupport.CompositeData;

public class RMIMasterSlaveObserver implements Observer {
	private final static Logger logger = LoggerFactory
			.getLogger(RMIMasterSlaveObserver.class);
	private RoundRobinLoadBalancer<MasterSlaveLoadBalancer<RMIRemote>> loadBalancer;
	private String registryCenter;
	private String registryName;
	private static final String COLON = ":";
	
	public RMIMasterSlaveObserver(String registryCenter, String registryName) {
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
		List<MasterSlaveLoadBalancer<RMIRemote>> mslbs = new ArrayList<MasterSlaveLoadBalancer<RMIRemote>>();
		for (String res : (List<String>)(resource.getResource())) {
			MasterSlaveLoadBalancer<RMIRemote> mslb = createMasterSlaveLoadBalancer(res);
			if (mslb != null) {
				mslbs.add(mslb);
			}
		}

		if (mslbs.size() == 0) {
			throw new ScallopRemoteException(
					"can not parse resource. resource name:"
							+ this.registryName);
		}

		if (loadBalancer == null) {
			loadBalancer = new RoundRobinLoadBalancer<MasterSlaveLoadBalancer<RMIRemote>>(mslbs);

		} else {
			List<MasterSlaveLoadBalancer<RMIRemote>> newMslbs = new ArrayList<MasterSlaveLoadBalancer<RMIRemote>>();
			List<MasterSlaveLoadBalancer<RMIRemote>> oldMslbs = loadBalancer.getResources();
			for (MasterSlaveLoadBalancer<RMIRemote> rr : mslbs) {
				if (oldMslbs.indexOf(rr) == -1) {
					// new items
//					 logger.info("new folbs:"+rr.get().getResource().getHost());
					newMslbs.add(rr);
				}
			}
			List<MasterSlaveLoadBalancer<RMIRemote>> uselessFolbs = new ArrayList<MasterSlaveLoadBalancer<RMIRemote>>();
			for (MasterSlaveLoadBalancer<RMIRemote> rr : oldMslbs) {
				if (mslbs.indexOf(rr) == -1) {
					// useless items
//					 logger.info("useless folbs:"+rr.get().getResource().getHost());
					uselessFolbs.add(rr);
				}
			}
			if (!newMslbs.isEmpty()) {
				// add new items
//				 logger.info("newFolbs size:"+newMslbs.size());
				oldMslbs.addAll(newMslbs);
			}
			if (!uselessFolbs.isEmpty()) {
				// remove useless items
				oldMslbs.removeAll(uselessFolbs);
//				logger.info("uselessFolbs size:"+uselessFolbs.size());
			}

			// logger.info("oldFolbs size:"+oldFolbs.size());
		}
	}

	private static MasterSlaveLoadBalancer<RMIRemote> createMasterSlaveLoadBalancer(
			String res) throws ScallopRemoteException {
		CompositeData cd = StringSupport.parseComposite(res);
		String[] st = cd.getComponents();
		ArrayList<RMIRemote> l = new ArrayList<RMIRemote>();
		for (String rs : st) {
			String[] hostPort = rs.split(COLON);
			l.add(new RMIRemote(hostPort[0], hostPort[1]));
		}
		MasterSlaveLoadBalancer<RMIRemote> mslb = new MasterSlaveLoadBalancer<RMIRemote>(l);
		return mslb;

	}
	
	public static MasterSlaveLoadBalancer<RMIRemote> getMasterSlaveLoadBalancer(
			String str) {
		return null;
	}


	public LoadBalancer<MasterSlaveLoadBalancer<RMIRemote>> getLoadBalancer() {
		return loadBalancer;
	}
}
