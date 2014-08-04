package scallop.hello;


import java.rmi.server.ServerCloneException;
import java.util.List;

import org.oasisopen.sca.annotation.Reference;

public class HelloworldClientImpl implements Helloworld{

    private Helloworld helloWorldService;
	
	@Reference
    public void setHelloWorldService(Helloworld helloworld) {
        this.helloWorldService = helloworld;
    }

    @Override
    public String sayHello(String name) throws ServerCloneException {
        return helloWorldService.sayHello(name);
    }

    @Override
    public String sayHelloMultiParams(String name, List<String> list) throws ServerCloneException {
        return helloWorldService.sayHelloMultiParams(name, list);
    }

    @Override
    public String sayHelloArrays(String[] names) throws ServerCloneException {
    	System.out.println("helloworld" + helloWorldService);
        return helloWorldService.sayHelloArrays(names);
    }

}
