package scallop.hello;


import java.rmi.server.ServerCloneException;
import java.util.List;

import org.oasisopen.sca.annotation.Remotable;


@Remotable
public interface Helloworld {

    String sayHello(String name) throws ServerCloneException;
    
    String sayHelloMultiParams(String name, List<String> list) throws ServerCloneException;

    String sayHelloArrays(String[] names) throws ServerCloneException;
    
}
