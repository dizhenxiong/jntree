package scallop.sca.binding.rmi.provider;

import org.json.JSONException;
import org.json.JSONObject;

import scallop.core.Resource;
import scallop.core.ResourceParser;


/**
 * 
 * @author songkun1
 *
 */
public class RMIResourceParser implements ResourceParser {

    private static final String REGEX = ";";

    @Override
    public Resource parse(JSONObject jsonObject) throws JSONException {
        Resource resource = new Resource();
        resource.setName(jsonObject.getString(ResourceParser.NAME));
        String resourceString = jsonObject.getString(ResourceParser.RESOURCE);
        for (String rs : resourceString.split(REGEX)) {
            resource.getResource().add(rs);
        }
        return resource;
    }

}
