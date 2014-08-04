package com.lenovo.vctl.dal.cache.memcached.channel;

import java.io.DataInputStream;
import java.io.IOException;

import com.lenovo.vctl.dal.cache.memcached.stream.LineInputStream;


public interface MemcachedChannel extends LineInputStream {
    /**
     * 关闭
     */
    public void close() throws IOException;

    /**
     * 打开
     * 
     * @return
     */
    public boolean isConnected();

    /**
     * 是否能用
     * 
     * @return
     */
    public boolean isAlive();

    /**
     * 刷新
     * 
     * @throws java.io.IOException
     */
    public void flush() throws IOException;

    /**
     * 
     * @param b
     * @throws java.io.IOException
     */
    public void write(byte[] b) throws IOException;

    public DataInputStream getIn();

    public void setHealth(boolean health);
}
