
package scallop.core.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import scallop.core.exception.ScallopRemoteException;
/**
 * @author songkun1
 *
 */
public class StringSupport {

        public static class CompositeData {
            private String components[];
            private Map<String, String> parameters;
            
            public String[] getComponents() {
                return components;
            }
            
            public Map<String, String> getParameters() {
                return parameters;
            }
        }
        /**
         * 解析配置参数
         * @param uri
         * @return
         * @throws ScallopRemoteException
         */  
        public static Map<String, String> parseQuery(String uri) throws ScallopRemoteException {
            try {
                Map<String, String> rc = new HashMap<String, String>();
                if (uri != null) {
                    String[] parameters = uri.split("&");
                    for (int i = 0; i < parameters.length; i++) {
                        int p = parameters[i].indexOf("=");
                        if (p >= 0) {
                            String name = parameters[i].substring(0, p);
                            String value = parameters[i].substring(p + 1);
                            rc.put(name, value);
                        } else {
                            rc.put(parameters[i], null);
                        }
                    }
                }
                return rc;
            } catch (Exception e) {
                throw new ScallopRemoteException(e);
            }
        }
        
        @SuppressWarnings("unchecked")
        private static Map<String, String> emptyMap() {
            return Collections.EMPTY_MAP;
        }

        
        /**
         * 
         * @param uri
         * @return
         * @throws ScallopRemoteException
         */
        public static CompositeData parseComposite(String uri) throws ScallopRemoteException {
            CompositeData rc = new CompositeData();
//            if(uri.startsWith("failover:") || uri.startsWith("masterslave:") || uri.startsWith("notify:")){
            String ssp = stripPrefix(stripPrefix(uri, "failover:"), "masterslave:").trim();
            if(!ssp.equals(uri)){
                parseComposite(rc, ssp);
            }else{//未配置failover
                rc.parameters=emptyMap();
                String [] ss={uri};
                rc.components=ss;                
            }            
            return rc;
        }
        
        public static void main(String[] args){
        	try {
				System.out.println(StringSupport.parseComposite("masterslave:(abc)").components[0]);
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
        
//        public static CompositeData parseMasterSlaveComposite(String uri) throws ScallopRemoteException {
//            CompositeData rc = new CompositeData();
//            String ssp = stripPrefix(uri, "masterslave:").trim();
//            if(!ssp.equals(uri)){
//                parseComposite(rc, ssp);
//            }else{//未配置masterslave
//                rc.parameters=emptyMap();
//                String [] ss={uri};
//                rc.components=ss;                
//            }            
//            return rc;
//        }
        

        /**
         * @param uri
         * @param rc
         * @param ssp
         * @param p
         * @throws Exception
         */
        private static void parseComposite(CompositeData rc, String ssp) throws ScallopRemoteException {
            String componentString;
            String params;

            int p;
            int intialParen = ssp.indexOf("(");
            if (intialParen == 0) {
                p = ssp.lastIndexOf(")");
                componentString = ssp.substring(intialParen + 1, p);
                params = ssp.substring(p + 1).trim();
            } else {
               throw new ScallopRemoteException("( and ) is not match");
            }

            String components[] = splitComponents(componentString);
            rc.components = new String[components.length];
            for (int i = 0; i < components.length; i++) {
                rc.components[i] = new String(components[i].trim());
            }

            p = params.indexOf("?");
            if (p >= 0) {
                rc.parameters = parseQuery(params.substring(p + 1));
            } else {
                rc.parameters = emptyMap();
            }
        }

        /**
         * @param componentString
         * @return
         */
        private static String[] splitComponents(String str) {
            List<String> l = new ArrayList<String>();

            int last = 0;
            int depth = 0;
            char chars[] = str.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                switch (chars[i]) {
                case '(':
                    depth++;
                    break;
                case ')':
                    depth--;
                    break;
                case ',':
                    if (depth == 0) {
                        String s = str.substring(last, i);
                        l.add(s);
                        last = i + 1;
                    }
                    break;
                default:
                }
            }

            String s = str.substring(last);
            if (s.length() != 0) {
                l.add(s);
            }

            String rc[] = new String[l.size()];
            l.toArray(rc);
            return rc;
        }

        public static String stripPrefix(String value, String prefix) {
            if (value.startsWith(prefix)) {
                return value.substring(prefix.length());
            }
            return value;
        }

        public static boolean checkParenthesis(String str) {
            boolean result = true;
            if (str != null) {
                int open = 0;
                int closed = 0;

                int i = 0;
                while ((i = str.indexOf('(', i)) >= 0) {
                    i++;
                    open++;
                }
                i = 0;
                while ((i = str.indexOf(')', i)) >= 0) {
                    i++;
                    closed++;
                }
                result = open == closed;
            }
            return result;
        }

    
    public static String getURI(String host, String port, String serviceName){
    	return "rmi://"+host+":"+port+"/"+serviceName;
    }




}
