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
package scallop.sca.binding.rmi.impl;

import javax.xml.namespace.QName;

import org.apache.tuscany.sca.assembly.OperationSelector;
import org.apache.tuscany.sca.assembly.WireFormat;
import scallop.sca.binding.rmi.RMIBinding;

/**
 * Represents a binding to an RMI service.
 * @author songkun1
 * @version 2.0
 */
public class RMIBindingImpl implements RMIBinding {
    private String uri;
    private String name;
	private String registryCenter;
	private String registryName;
	private String security;
	private String serviceName;
	private String mode;
	
	
	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getRegistryCenter() {
		return registryCenter;
	}

	public void setRegistryCenter(String registryCenter) {
		this.registryCenter = registryCenter;
	}

	public String getSecurity() {
		return security;
	}

	public void setSecurity(String security) {
		this.security = security;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getRegistryName() {
		return registryName;
	}

	public void setRegistryName(String registryName) {
		this.registryName = registryName;
	}

	public String getURI() {
        return uri;
    }

    public void setURI(String uri) {
        this.uri = uri;
    }


    public boolean isUnresolved() {
        return false;
    }

    public void setUnresolved(boolean unresolved) {
        // The binding is always resolved
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public QName getType() {
        return TYPE;
    }

    public WireFormat getRequestWireFormat() {
        return null;
    }
    
    public void setRequestWireFormat(WireFormat wireFormat) {  
    }
    
    public WireFormat getResponseWireFormat() {
        return null;
    }
    
    public void setResponseWireFormat(WireFormat wireFormat) {
    }
    
    public OperationSelector getOperationSelector() {
        return null;
    }
    
    public void setOperationSelector(OperationSelector operationSelector) {
    }
}
