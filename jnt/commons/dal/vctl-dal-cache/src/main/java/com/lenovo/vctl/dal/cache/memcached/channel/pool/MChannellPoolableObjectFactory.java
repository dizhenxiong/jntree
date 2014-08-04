package com.lenovo.vctl.dal.cache.memcached.channel.pool;

import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.log4j.Logger;

import com.lenovo.vctl.dal.cache.memcached.channel.MemcachedChannel;

public class MChannellPoolableObjectFactory implements PoolableObjectFactory {
    private static Logger logger = Logger.getLogger(MChannellPoolableObjectFactory.class);
    protected MChannelFactory channelFactory = null;
    protected ObjectPool pool = null;

    public MChannellPoolableObjectFactory(MChannelFactory channelFactory, ObjectPool pool) {
        this.pool = pool;
        pool.setFactory(this);
        this.channelFactory = channelFactory;
    }

    /**
     * 这个方法用于将对象“激活”――设置为适合开始使用的状态
     */
    public void activateObject(Object obj) throws Exception {
        // TODO Auto-generated method stub
    }
    
    /**
     * 这个方法用于销毁被validateObject判定为已失效的对象。 
     */
    public void destroyObject(Object obj) throws Exception {
        if (obj instanceof MemcachedChannelPoolableObject) {
            MemcachedChannelPoolableObject poolableObject = (MemcachedChannelPoolableObject) obj;
            poolableObject.trueClose();
        }

    }

    /**
     * 产生新的对象,对象能放回池中
     */
    public Object makeObject() throws Exception {
        MemcachedChannel channel = this.channelFactory.createMemcachedChannel();
        //
        if (channel != null) {
            channel = new MemcachedChannelPoolableObject(channel, this.pool);
        }
        return channel;
    }

    /**
     * 这个方法用于将对象“挂起”――设置为适合开始休眠的状态。 
     */
    public void passivateObject(Object obj) throws Exception {
        if (obj instanceof MemcachedChannelPoolableObject) {
            MemcachedChannelPoolableObject poolableObject = (MemcachedChannelPoolableObject) obj;
            if (!poolableObject.isHealth()) {
                throw new Exception("MemcachedChannelPoolableObject passivate fail.");
            }
        }
    }

    /**
     * 这个方法用于校验一个具体的对象是否仍然有效，已失效的对象会被自动交给destroyObject方法销毁
     */
    public boolean validateObject(Object obj) {
        if (obj instanceof MemcachedChannelPoolableObject) {
            MemcachedChannelPoolableObject poolableObject = (MemcachedChannelPoolableObject) obj;
            return poolableObject.isHealth();
        } else {
            return false;
        }
        
    }

}
