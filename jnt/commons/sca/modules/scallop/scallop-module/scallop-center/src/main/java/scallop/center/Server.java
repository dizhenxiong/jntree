package scallop.center;

import org.restlet.Component;
import org.restlet.data.Protocol;
import org.restlet.ext.jaxrs.JaxRsApplication;

import scallop.center.rest.RestApplication;

public class Server {

    private static final int PORT = 8182;
    private static final String IP = "127.0.0.1";

    public static void main(String[] args) throws Exception {
        int port = PORT;
        String ip = IP;
        if (args.length > 0) {
            for (String arg :args) {
                if (arg.startsWith("-p=")) {
                    port = Integer.parseInt(arg.replace("-p=",""));
                }
                if(arg.startsWith("-l=")){
                	ip = arg.replace("-l=","");
                }
            }
        }
        
        Component component = new Component();
        component.getServers().add(Protocol.HTTP, ip, port);
        JaxRsApplication application = new JaxRsApplication(component.getContext().createChildContext());
        application.add(new RestApplication());
        component.getDefaultHost().attach(application);
        component.start();
        System.out.println("registry center is started. address is "+ip+", port is " + port);
    }
}