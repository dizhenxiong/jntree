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

package scallop.sca.binding.rmi.provider;

import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tuscany.sca.assembly.EndpointReference;
import scallop.sca.binding.rmi.RMIBinding;
import scallop.sca.host.rmi.RMIHost;
import org.apache.tuscany.sca.interfacedef.InterfaceContract;
import org.apache.tuscany.sca.interfacedef.Operation;
import org.apache.tuscany.sca.interfacedef.java.JavaInterface;
import org.apache.tuscany.sca.interfacedef.java.impl.JavaInterfaceUtil;
import org.apache.tuscany.sca.invocation.Invoker;
import org.apache.tuscany.sca.provider.ReferenceBindingProvider;
import org.apache.tuscany.sca.runtime.RuntimeComponentReference;
import org.oasisopen.sca.ServiceRuntimeException;

/**
 * Implementation of the RMI Binding Provider for References
 * 
 * @author songkun1
 * @version 2.0
 */
public class RMIReferenceBindingProvider implements ReferenceBindingProvider {
	private static final String NOTIFY = "notify"; 
	private static final Log log = LogFactory.getLog(RMIReferenceBindingProvider.class);
    private RuntimeComponentReference reference;
    private RMIBinding binding;
    private RMIHost rmiHost;
    
    public RMIReferenceBindingProvider(EndpointReference endpointReference,
                                       RMIHost rmiHost) {
           this.reference = (RuntimeComponentReference)endpointReference.getReference();
           this.binding = (RMIBinding)endpointReference.getBinding();
           this.rmiHost = rmiHost;
    }

    public InterfaceContract getBindingInterfaceContract() {
        return reference.getInterfaceContract();
    }
    
    public Invoker createInvoker(Operation operation) {
        Class<?> iface = ((JavaInterface)reference.getInterfaceContract().getInterface()).getJavaClass();
        Method remoteMethod;
        try {
            remoteMethod = JavaInterfaceUtil.findMethod(iface, operation);
            if(binding.getMode() != null && !binding.getMode().isEmpty() && binding.getMode().trim().equals(NOTIFY)){
            	try{
            		return  new RMIBindingNotifyInvoker(rmiHost,
							binding.getRegistryCenter(),
							binding.getRegistryName(),
							binding.getServiceName(), remoteMethod);
            	}catch (Exception e) {
					throw new ServiceRuntimeException(operation.toString(), e);
				}
            }
            if(binding.getRegistryName() != null && !binding.getRegistryName().isEmpty()){
            	try {
					return new RMIBindingMasterSlaveLoadBalanceInvoker(rmiHost,
							binding.getRegistryCenter(),
							binding.getRegistryName(),
							binding.getServiceName(), remoteMethod);
				} catch (Exception e) {
					throw new ServiceRuntimeException(operation.toString(), e);
				}
            }
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException(e);
        } 
        
        return new RMIBindingInvoker(rmiHost, binding.getURI(), remoteMethod);
    }

    public void start() {
    }

    public void stop() {
    }

    public boolean supportsOneWayInvocation() {
        return false;
    }

}
