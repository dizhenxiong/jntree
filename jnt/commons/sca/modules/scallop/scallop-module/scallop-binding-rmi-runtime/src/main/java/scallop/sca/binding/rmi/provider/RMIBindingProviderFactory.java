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

import scallop.sca.binding.rmi.RMIBinding;

import org.apache.tuscany.sca.core.DefaultExtensionPointRegistry;
import org.apache.tuscany.sca.core.DefaultFactoryExtensionPoint;
import org.apache.tuscany.sca.core.ExtensionPointRegistry;

import scallop.sca.host.rmi.DefaultRMIHost;
import scallop.sca.host.rmi.DefaultRMIHostExtensionPoint;
import scallop.sca.host.rmi.ExtensibleRMIHost;
import scallop.sca.host.rmi.RMIHost;
import scallop.sca.host.rmi.RMIHostExtensionPoint;
import org.apache.tuscany.sca.provider.BindingProviderFactory;
import org.apache.tuscany.sca.provider.ReferenceBindingProvider;
import org.apache.tuscany.sca.provider.ServiceBindingProvider;
import org.apache.tuscany.sca.runtime.RuntimeEndpoint;
import org.apache.tuscany.sca.runtime.RuntimeEndpointReference;

/**
 * RMI Binding Provider Factory
 * @author songkun1
 */
public class RMIBindingProviderFactory implements BindingProviderFactory<RMIBinding> {

    private RMIHost rmiHost;
    
    public RMIBindingProviderFactory(ExtensionPointRegistry extensionPoints) {
        RMIHostExtensionPoint rmiHosts = extensionPoints.getExtensionPoint(RMIHostExtensionPoint.class);
        if(rmiHosts == null)
        	rmiHosts = new DefaultRMIHostExtensionPoint();
        this.rmiHost = new ExtensibleRMIHost(rmiHosts);
    }
    
    public ReferenceBindingProvider createReferenceBindingProvider(RuntimeEndpointReference endpointReference) {
        
        return new RMIReferenceBindingProvider(endpointReference, rmiHost);
    }

    public ServiceBindingProvider createServiceBindingProvider(RuntimeEndpoint endpoint) {
        return new RMIServiceBindingProvider(endpoint, rmiHost);
    }

    public Class<RMIBinding> getModelType() {
        return RMIBinding.class;
    }

}
