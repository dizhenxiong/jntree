package scallop.core.loadbalance;

public class ResourceLoader<T> {

    private int id;
    private T resource;
    public ResourceLoader(int id, T resource) {
        this.id = id;
        this.resource = resource;
    }
    
    public int getId() {
        return id;
    }
    
    public T getResource() {
        return resource;
    }
    
}
