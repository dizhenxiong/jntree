package com.lenovo.vctl.dal.cache.memcached.channel.pool;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import org.apache.log4j.Logger;

import com.lenovo.vctl.dal.cache.memcached.channel.MemcachedChannel;
import com.lenovo.vctl.dal.cache.memcached.channel.MemcachedChannelImpl;

public class MChannelFactoryImpl implements MChannelFactory {
    private static Logger logger = Logger.getLogger(MChannelFactoryImpl.class);
    private String host;
    private int port;
    private int timeout;

    public MChannelFactoryImpl(String host, int port, int timeout) {
        this.host = host;
        this.port = port;
        this.timeout = timeout;
    }


//    public MemcachedChannel createMemcachedChannel() throws IOException {
//        if (logger.isDebugEnabled()) {
//            logger.debug("begin create MemcachedChannel...");
//        }
//        SocketChannel sock = SocketChannel.open();
//        //sock.configureBlocking(false);
//        Socket socket = sock.socket();
//        
//        socket.connect(new InetSocketAddress(host, port), timeout);
//
//        if (this.timeout >= 0)
//            socket.setSoTimeout(this.timeout);
//
//        // testing only
//        socket.setTcpNoDelay(true);
//
//        return new MemcachedChannelImpl(socket);
//    }
    
    public MemcachedChannel createMemcachedChannel() throws IOException {
        if (logger.isDebugEnabled()) {
            logger.debug("begin create MemcachedChannel...");
        }
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress(host, port), timeout);

        if (this.timeout >= 0)
            socket.setSoTimeout(this.timeout);

        // testing only
        socket.setTcpNoDelay(true);

        return new MemcachedChannelImpl(socket);
    }    

}
