package scallop.core.loadbalance;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class RoundRobinLoadBalancer<T> implements LoadBalancer<T> {
    private static final Logger logger = Logger.getLogger(RoundRobinLoadBalancer.class.getName());
    // Can be shared by multiple threads so access to the counter
    // needs to be threadsafe.
    private final AtomicInteger nextIndex = new AtomicInteger();
    
    private List<T> resourceList = new ArrayList<T>();

    @Override
    public List<T> getResources() {
        return resourceList;
    }

    public RoundRobinLoadBalancer(List<T> resourceList) {
        super();
        this.resourceList = new ArrayList<T>(resourceList);
    }

    @Override
    public ResourceLoader<T> get() {
        int index = Math.abs(nextIndex.getAndIncrement() % this.resourceList.size());
      //  logger.info("RoundRobinLoadBalancer index="+index);
        T resource = resourceList.get(index);
        return new ResourceLoader<T>(index, resource);
    }

    @Override
    public ResourceLoader<T> get(ResourceLoader<T> proxy) {
        //FIXME 
        int index = (proxy.getId() + 1) % this.resourceList.size();
     //   logger.info("RoundRobinLoadBalancer index="+index);
        T resource = resourceList.get(index);
        return new ResourceLoader<T>(index, resource);
    }

    @Override
    public int getResourceSize() {
        return this.resourceList.size();
    }
}
