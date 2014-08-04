package com.lenovo.vctl.dal.cache.memcached.channel.pool;

import java.io.DataInputStream;
import java.io.IOException;

import org.apache.commons.pool.ObjectPool;

import com.lenovo.vctl.dal.cache.memcached.channel.MemcachedChannel;

public class MemcachedChannelPoolableObject implements MemcachedChannel {
    private MemcachedChannel channel;
    private ObjectPool pool;
    private boolean health = true;

    public boolean isHealth() {
        return health;
    }

    public void setHealth(boolean health) {
        this.health = health;
    }

    public MemcachedChannelPoolableObject(MemcachedChannel channel, ObjectPool pool) {
        this.pool = pool;
        this.channel = channel;
    }

    public void close() throws IOException {
        // check this status
        try {
            this.pool.returnObject(this);
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    public void flush() throws IOException {
        try {
            channel.flush();
        } catch (IOException e) {
            this.setHealth(false);
            throw e;
        }
    }

    public boolean isAlive() {
        boolean bResult = channel.isAlive();
        if (!bResult) {
            this.setHealth(false);
        }
        return bResult;
    }

    public boolean isConnected() {
        boolean bResult = channel.isConnected();
        if (!bResult) {
            this.setHealth(false);
        }
        return bResult;
    }

    public void write(byte[] b) throws IOException {
        try {
            channel.write(b);
        } catch (IOException e) {
            this.setHealth(false);
            throw e;
        }
    }

    public void clearEOL() throws IOException {
        try {
            channel.clearEOL();
        } catch (IOException e) {
            this.setHealth(false);
            throw e;
        }
    }

    public int read(byte[] buf) throws IOException {
        try {
            return channel.read(buf);
        } catch (IOException e) {
            this.setHealth(false);
            throw e;
        }
    }

    public String readLine() throws IOException {
        try {
            return channel.readLine();
        } catch (IOException e) {
            this.setHealth(false);
            throw e;
        }
    }

    public void trueClose() throws Exception {
        // check this status
        channel.close();
    }

    @Override
    public void clearEnd() throws IOException {
        try {
            channel.clearEnd();
        } catch (IOException e) {
            this.setHealth(false);
            throw e;
        }
    }

    @Override
    public String readKeys(String keys) throws IOException {
        try {
            return channel.readKeys(keys);
        } catch (IOException e) {
            this.setHealth(false);
            throw e;
        }
    }

    @Override
    public DataInputStream getIn() {
        return channel.getIn();
    }

}
