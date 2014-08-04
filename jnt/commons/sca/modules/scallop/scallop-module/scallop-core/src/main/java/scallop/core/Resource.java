package scallop.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Resource implements Serializable {

    private static final long serialVersionUID = 4199177211491371268L;

    private String address;
    private String name;
    private List<String>  resource = new ArrayList<String>();

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<String> getResource() {
        return resource;
    }
    public void setResource(List<String> resource) {
        this.resource = resource;
    }
    
    public String toString(){
    	StringBuffer sb = new StringBuffer();
    	sb.append("address = ").append( address).append(";");
    	sb.append("name = ").append( name).append(";");
      	sb.append("resource = ").append( resource.toString()).append(";");
      	return sb.toString();
    }

}
