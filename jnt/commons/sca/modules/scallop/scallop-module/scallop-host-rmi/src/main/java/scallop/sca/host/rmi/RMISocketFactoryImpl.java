/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.    
 */

package scallop.sca.host.rmi;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.server.RMISocketFactory;

import javax.net.ServerSocketFactory;
import javax.net.SocketFactory;

class RMISocketFactoryImpl extends RMISocketFactory {
    private int clientTimeout;
    private SocketFactory factory;
    private ServerSocketFactory serverSocketFactory;
    private String host = null; 

    /**
     * @param timeout
     */
    public RMISocketFactoryImpl(int clientTimeout) {
        super();
        this.clientTimeout = clientTimeout;
        this.factory = SocketFactory.getDefault();
        this.serverSocketFactory = ServerSocketFactory.getDefault();
    }
    public RMISocketFactoryImpl(String host, int clientTimeout) {
    	this(clientTimeout);
    	this.host = host;
    }
    public Socket createSocket(String host, int port) throws IOException {
        Socket socket = factory.createSocket(host, port);
        socket.setSoTimeout(clientTimeout);
        return socket;
    }

    @Override
    public ServerSocket createServerSocket(int port) throws IOException {
//        ServerSocket socket = serverSocketFactory.createServerSocket(port);
//    	InetAddress[] addresses = InetAddress.getAllByName(host);
//    	if(addresses == null || addresses.length != 1)
//    		return createServerSocket(port);
    	if(host == null)return serverSocketFactory.createServerSocket(port);
    	else return createServerSocket(host, port);
    }
    

    public ServerSocket createServerSocket(String host, int port) throws IOException{
    	InetAddress[] addresses = InetAddress.getAllByName(host);
    	if(addresses == null || addresses.length != 1)
    		return serverSocketFactory.createServerSocket(port);
    	ServerSocket socket = serverSocketFactory.createServerSocket(port, -1, addresses[0]);
        return socket;
    }

}
