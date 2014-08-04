package scallop.core.loadbalance;

import java.util.Collection;


public interface LoadBalancer<T> {

    /**
     * @return the next proxy
     */
    ResourceLoader<T> get();
    
    /**
     * @param proxy balance
     * @return
     */
    ResourceLoader<T> get(ResourceLoader<T> proxy);
    
    int getResourceSize();

    Collection<T> getResources();
}
