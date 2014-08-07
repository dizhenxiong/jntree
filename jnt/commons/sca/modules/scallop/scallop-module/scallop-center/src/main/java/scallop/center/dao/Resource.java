package scallop.center.dao;

import java.io.Serializable;

public class Resource implements Serializable {

    private static final long serialVersionUID = -1980400279647385980L;

    private Long id;
    private String name;
    private String resource;
    private String strategy;
    
    public String getStrategy() {
        return strategy;
    }
    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getResource() {
        return resource;
    }
    public void setResource(String resource) {
        this.resource = resource;
    }
}
