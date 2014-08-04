package com.lenovo.vctl.sca.client;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.tuscany.sca.assembly.Component;
import org.apache.tuscany.sca.assembly.ComponentService;
import org.apache.tuscany.sca.assembly.Composite;
import org.apache.tuscany.sca.node.Contribution;
import org.apache.tuscany.sca.node.ContributionLocationHelper;
import org.apache.tuscany.sca.node.Node;
import org.apache.tuscany.sca.node.NodeFactory;
import org.apache.tuscany.sca.node.extensibility.NodeExtension;
import org.springframework.beans.factory.FactoryBean;

public class SCAServiceFactory implements FactoryBean<Object> {
	private Logger logger = Logger.getLogger(SCAServiceFactory.class);
	private static Node clientNode = null;

	private String serviceName;
	private Class serviceInterface;

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public Class getServiceInterface() {
		return serviceInterface;
	}

	public void setServiceInterface(Class serviceInterface) {
		this.serviceInterface = serviceInterface;
	}

	public void init() throws Exception {
		if (clientNode == null) {
			synchronized (SCAServiceFactory.class) {
				if (clientNode != null) {
					return;
				}
				if (logger.isDebugEnabled()) {
					logger.debug("serviceFactory afterPropertiesSet");
				}
				String contribution = ContributionLocationHelper.getContributionLocation("client.composite");
				clientNode = NodeFactory.newInstance().createNode("client.composite",
						new Contribution("lenovo", contribution));
				
				
				clientNode.start();

				logger.info("clientNode is running.... ok");

				if (logger.isDebugEnabled()) {
					if (clientNode instanceof NodeExtension) {
						Composite composite = ((NodeExtension) clientNode).getDomainComposite();
						if (composite != null) {
							List<Component> components = composite.getComponents();
							if (CollectionUtils.isNotEmpty(components)) {
								for (Component component : components) {
									List<ComponentService> componentServices = component.getServices();
									for (ComponentService componentService : componentServices) {
										String serviceName = componentService.getName();
										logger.info("sca service name:" + serviceName);
									}
								}
							}
						} else {
							logger.warn("client composite is null");
						}
					} else {
						logger.warn("clientNode is not NodeExtension ");
					}
				}
			}

		}
	}

	public void destroy() {
		clientNode.stop();
		logger.info("clientNode stoped.... ok");
	}

	@Override
	public Object getObject() throws Exception {
		if (clientNode == null) {
			throw new RuntimeException("clientNode must init, please call init method before call this method");
		}

		if (StringUtils.isEmpty(this.getServiceName())) {
			throw new RuntimeException("ServiceName must set value");
		}

		if (this.getServiceInterface() == null) {
			throw new RuntimeException("ServiceInterface must set value");
		}

		return clientNode.getService(this.getServiceInterface(), this.getServiceName());
	}

	@Override
	public Class<?> getObjectType() {
		return this.getServiceInterface();
	}

	@Override
	public boolean isSingleton() {
		// TODO Auto-generated method stub
		return true;
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(ContributionLocationHelper.getContributionLocation("client.composite"));
	}
}
