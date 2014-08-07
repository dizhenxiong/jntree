package scallop.core;

import org.json.JSONException;
import org.json.JSONObject;

public interface ResourceParser {

    public static final String RESOURCE = "resource";
    public static final String NAME = "name";
    public static final String STRATEGY= "strategy";
    
    Resource parse(JSONObject jsonObject) throws JSONException;
}
