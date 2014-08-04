package scallop.core.loadbalance;


import java.util.HashSet;
import java.util.Set;

public class DefaultLoadBalancer<T> implements LoadBalancer<T> {
	private Set<T> resources;
	
	public DefaultLoadBalancer(Set<T> set){
		this.resources = new HashSet<T>();
		this.resources.addAll(set);
	}
	
	@Override
	public ResourceLoader<T> get() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResourceLoader<T> get(ResourceLoader<T> proxy) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getResourceSize() {
		// TODO Auto-generated method stub
		return resources.size();
	}

	@Override
	public Set<T> getResources() {
		// TODO Auto-generated method stub
		return resources;
	}

}
