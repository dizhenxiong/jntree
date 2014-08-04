package scallop.hello;


import java.rmi.server.ServerCloneException;
import java.util.List;

//import scallop.core.util.SecurityContextHolder;

public class 
HelloworldImpl implements Helloworld {

    public String sayHello(String name) throws ServerCloneException {
        return "HelloworldImpl:Hello "+ name;
    }

    @Override
    public String sayHelloMultiParams(String name, List<String> list) throws ServerCloneException {
        return "HelloworldImpl:hello "+ name + " list :" + list.toString();
    }

    @Override
    public String sayHelloArrays(String[] names) throws ServerCloneException {
        return "HelloworldImpl:hello "+ names.toString();
    }
}
