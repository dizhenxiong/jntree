package scallop.core.loadbalance;

import java.util.List;

public class MasterSlaveLoadBalancer <T> implements LoadBalancer<T> {

	
	private List<T> list;
	private T master;
	private T slave;
	
	public MasterSlaveLoadBalancer(List<T> l){
		this.list = l;
		if(l != null){
			master = l.get(0);
			if(l.size() > 1){
				slave = l.get(1);
			}else{
				slave = master;
			}
		}
	}
	
	
	@Override
	public ResourceLoader<T> get() {
		// TODO Auto-generated method stub
	       return new ResourceLoader<T>(0, master);
	}

	@Override
	public ResourceLoader<T> get(ResourceLoader<T> proxy) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getResourceSize() {
		// TODO Auto-generated method stub
		if(list != null)return list.size();
		return 0;
	}

	@Override
	public List<T> getResources() {
		// TODO Auto-generated method stub
		return list;
	}
	
	public T getMaster(){
		return master;
	}
	
	
	public T getSlave(){
	      return slave;
	}

	@Override
	public boolean equals(Object balancer) {
		// TODO Auto-generated method stub
		if(balancer instanceof MasterSlaveLoadBalancer){
			MasterSlaveLoadBalancer<T> nb = (MasterSlaveLoadBalancer<T>)balancer;
			return nb == null ? false : master.equals(nb.getMaster()) && slave.equals(nb.getSlave());
		}
		return false;
	}
	
}
