package scallop.center.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

public class RestApplication extends Application {

	public Set<Class<?>> getClasses() {
		Set<Class<?>> rrcs = new HashSet<Class<?>>();
		rrcs.add(ResourcesService.class);
		rrcs.add(RootResource.class);
		return rrcs;
	}

}
