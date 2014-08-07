package scallop.core.loadbalance;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;


import scallop.core.ScallopConstants;

public class FailOverLoadBalancer<T> implements LoadBalancer<T> {
    private static final Logger logger = Logger.getLogger(FailOverLoadBalancer.class);
    // Can be shared by multiple threads so access to the counter
    // needs to be threadsafe.
    // failover:(10.10.82.102:8089,10.10.82.103:8089)?max_fails=2&fail_interval=30000;failover:(10.10.82.101:8089,10.10.82.103:8089)?max_fails=2&fail_interval=30000
    private int max_fails = ScallopConstants.DEFAULT_MAX_FAIL;
    private long fail_interval = ScallopConstants.DEFAULT_FAIL_INTERVAL;
    private long check_interval = ScallopConstants.DEFAULT_RETRY_CHECK_INTERVAL;

    private int fails = 0;
    private long fail_starttime = System.currentTimeMillis();
    private long check_starttime = System.currentTimeMillis();
    private final AtomicInteger nextIndex = new AtomicInteger();
    private final Object updateMutex = new Object();
    private final Object checkMutex = new Object();

    private List<T> resourceList = new ArrayList<T>();

    public FailOverLoadBalancer(List<T> resourceList, int max_fails, long fail_interval, long check_interval) {
        super();
        this.resourceList = new ArrayList<T>(resourceList);
        this.max_fails = max_fails;
        this.fail_interval = fail_interval;
        this.check_interval = check_interval;
    }

    @Override
    public ResourceLoader<T> get() {
        if ((System.currentTimeMillis() - check_starttime) > check_interval) {
            synchronized (checkMutex) {
                check_starttime = System.currentTimeMillis();
                nextIndex.set(0);
            }
        }

        int index = nextIndex.get();
        T resource = resourceList.get(index);
        return new ResourceLoader<T>(index, resource);
    }

    @Override
    public ResourceLoader<T> get(ResourceLoader<T> proxy) {
        synchronized (updateMutex) {

            fails++;
            Long interval = System.currentTimeMillis() - fail_starttime;
            if(logger.isDebugEnabled())
            	logger.debug("fails:" + fails + ",interval:" + interval);
            if (max_fails == 1) {
                int index = (proxy.getId() + 1) % this.resourceList.size();
                T resource = resourceList.get(index);
                nextIndex.set(index);
                if(logger.isDebugEnabled())
                	logger.debug("fails:" + fails + ",interval:" + interval + ",index=" + index);
                return new ResourceLoader<T>(index, resource);
            } else {
                if (interval <= fail_interval) {
                    if (fails >= max_fails) {
                        int index = (proxy.getId() + 1) % this.resourceList.size();
                        T resource = resourceList.get(index);
                        nextIndex.set(index);
                        if(logger.isDebugEnabled())
                        	logger.debug("fails:" + fails + ",interval:" + interval + ",index=" + index);
                        fails = 0;
                        fail_starttime = System.currentTimeMillis();
                        return new ResourceLoader<T>(index, resource);
                    } else {
                    	if(logger.isDebugEnabled())
                    		logger.debug("fails:" + fails + ",interval:" + interval + ",index=" + nextIndex.get());
                        return proxy;
                    }

                } else {// 超过间隔时间,重新计数
                	if(logger.isDebugEnabled())
                		logger.debug("fails:" + fails + ",interval:" + interval + ",index=" + nextIndex.get());
                    fail_starttime = System.currentTimeMillis();
                    fails = 1;
                    return proxy;
                }
            }
        }

    }

    @Override
    public int getResourceSize() {
        return this.resourceList.size();
    }

    @Override
    public List<T> getResources() {
        // TODO Auto-generated method stub
        return resourceList;
    }

}
