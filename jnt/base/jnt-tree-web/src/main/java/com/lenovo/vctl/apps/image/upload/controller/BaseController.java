package com.lenovo.vctl.apps.image.upload.controller;


import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lenovo.vctl.apps.image.upload.util.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.ser.StdSerializerProvider;


public abstract class BaseController {

	private static final Log statusLogger = LogFactory.getLog("scribe"); //BI日志
    //上传相关参数
    public static final String PIC = "picUrl";
    public static final String PIC_OBJECT = "pic";
    public static final String VIDEO = "videoUrl";
    public static final String AUDIO = "audioUrl";
    public static final String CREATEAT = "createAt";
    public static final String ERROR_CODE = "error_code";
    public static final String ERROR_INFO = "error_info";
    public static final String ACCOUNT_ID = "accountId";
    public static final String TID = "tid";
    public static final String RECORD_ID = "recordId";
    public static final String USER_ID = "userId";
    public static final String TO_USER_ID = "toUserid";
    public static final String PIC_FTP = "pic";

    public static final String WriterKey = "writer";
    public static final String UserKey = "user";
    
    
    public JsonCallBackTemplate getJsonCallBackTemplate() {
        return new JsonCallBackTemplate();
    }


	private static final Log logger = LogFactory.getLog(BaseController.class);

    public abstract FileUpload getUpload();

    public void doResponse(final String type, final Map<String, Object> model, final HttpServletResponse response) {
        JsonCallBackTemplate template = getJsonCallBackTemplate();
        template.setCallback(new JsonCallbackAdapter(model) {
            @Override
            public void setProperties(JsonGenerator generator) {
                try {
                    if (getMap().get(ERROR_CODE) != null && getMap().get(ERROR_CODE).equals(ErrCode.NO_ERROR)) {
                        if (type.equals(VIDEO)) {
                            generator.writeStringField(RECORD_ID, (getMap().get(RECORD_ID)) + "");
                            generator.writeStringField(type, (String) (getMap().get(type)) + "");
                            generator.writeStringField(ACCOUNT_ID, (getMap().get(ACCOUNT_ID)) + "");
                            generator.writeStringField(CREATEAT, (getMap().get(CREATEAT)) + "");
                            generator.writeStringField("uid", (getMap().get("uid")) + "");
                        } else if (type.equals(AUDIO)) {
                            generator.writeStringField(TID, (getMap().get(TID)) + "");
                            generator.writeStringField(CREATEAT, (getMap().get(CREATEAT)) + "");
                            generator.writeStringField(type, (String) (getMap().get(type)) + "");
                        } else if(type.equals(PIC) || type.equals(PIC_OBJECT)){
                        	Set<String> keys = model.keySet();
                        	for(String key : keys){
                        		if(PIC_OBJECT.equals(key)){
                        			generator.writeObjectField(key, getMap().get(key));
                        		}else {
                        			if(!ERROR_CODE.equals(key)){
                        				generator.writeStringField(key, (String)(getMap().get(key)));
                        			}else{
                        				generator.writeNumberField("status", 200);
                        			}
                        			
                        		}
                        	}
                        }
                    } else {
                        generator.writeStringField(ERROR_CODE, (getMap().get(ERROR_CODE)) + "");
                        generator.writeStringField(ERROR_INFO, (getMap().get(ERROR_INFO)) + "");
                    
                    }
                } catch (Exception e) {
                    this.getLog().error(e.getMessage(), e);
                }
            }
        }
        );
        template.sendJson(response);
    }
    
    public void doResponseWithUserAgent(final String type, final Map<String, Object> model, final HttpServletResponse response, final HttpServletRequest request) {
    	JsonFactory factory = new JsonFactory();
		JsonGenerator generator;
		try {
			generator = factory.createJsonGenerator(response.getWriter());
			generator.setCodec(new ObjectMapper());
			generator.writeStartObject();
            if (model.get(ERROR_CODE) != null && model.get(ERROR_CODE).equals(ErrCode.NO_ERROR)) {
                if (type.equals(VIDEO)) {
                    generator.writeStringField(RECORD_ID, (model.get(RECORD_ID)) + "");
                    generator.writeStringField(type, (String) (model.get(type)) + "");
                    generator.writeStringField(ACCOUNT_ID, (model.get(ACCOUNT_ID)) + "");
                    generator.writeStringField(CREATEAT, (model.get(CREATEAT)) + "");
                    generator.writeStringField("uid", (model.get("uid")) + "");
                } else if (type.equals(AUDIO)) {
                    generator.writeStringField(TID, (model.get(TID)) + "");
                    generator.writeStringField(CREATEAT, (model.get(CREATEAT)) + "");
                    generator.writeStringField(type, (String) (model.get(type)) + "");

                } else if(type.equals(PIC) || type.equals(PIC_OBJECT)){
                	Set<String> keys = model.keySet();
                	for(String key : keys){
                		if(PIC_OBJECT.equals(key)){
                			generator.writeObjectField(key, model.get(key));
                		}else{
                			generator.writeStringField(key, (String)(model.get(key)));
                		}
                	}
                }
            } else {
                generator.writeStringField(ERROR_CODE, (model.get(ERROR_CODE)) + "");
                generator.writeStringField(ERROR_INFO, (model.get(ERROR_INFO)) + "");
            }
			generator.writeEndObject();
			generator.close();
		}catch (IOException e) {
//			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}

    }

    public void fillFileItemAndRequestParameter(HttpServletRequest req, Map<String, String> requestParamenters, List<DiskFileItem> fileItems)
            throws FileUploadException, UnsupportedEncodingException {
        List /* FileItem */items = getUpload().parseRequest(req);
        if (logger.isDebugEnabled()) {
            logger.debug("items is not empty = " + CollectionUtils.isNotEmpty(items));
        }
        if (CollectionUtils.isNotEmpty(items)) { // 有文件存在
            for (Iterator iterator = items.iterator(); iterator.hasNext(); ) {
                DiskFileItem fileItem = (DiskFileItem) iterator.next();
                if (fileItem.isFormField()) {
                    String name = fileItem.getFieldName();
                    String value = fileItem.getString("UTF-8");
                    System.out.println("fileItem.name = " + fileItem.getName() + "; " + fileItem.getString());
                    logger.info("fileItem.name = " + fileItem.getName() + "; " + fileItem.getString());
                    requestParamenters.put(name, value);
                } else {
                    fileItems.add(fileItem);
                }
            }
        }
    }
    
    



	public Map<String, Object> checkAndGetWriter(String token, int tokenOrigin,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> infoMap = null;
		PrintWriter writer = null;
		HashMap<String, String> errorMap = new HashMap<String, String>();
		try {
			writer = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace(System.out); // To change body of catch statement
											// use File | Settings | File
											// Templates.
		}
		// 第一步 检验 从 reponse 中获取PrintWrite 是否有效
		if (null == writer) {
			errorMap.put(ResultPropertyes.ERROR_CODE, ErrCode.ERROR_NULL_RESULT);
			errorMap.put(ResultPropertyes.ERROR_INFO,
					ErrCode.ERROR_NULL_RESULT_INFO);
			writeConent(writer, false, null, null, null, errorMap);
			return null;
		}
		// 第二步 检验 token 是否合法
		infoMap = new HashMap<String, Object>();
		infoMap.put(WriterKey, writer);
		return infoMap;
	}

	public String writeConent(PrintWriter writer, boolean bSuccess,
			Map<String, String> stringInfoMap, Map<String, Long> numberInfoMap,
			Map<String, Object> objInfoMap, Map<String, String> errorMap) {
		JsonGenerator generator = null;
		// First: need a custom serializer provider
		StdSerializerProvider sp = new StdSerializerProvider();
		sp.setNullValueSerializer(new EmptySerializer());
		try {
			JsonFactory factory = new JsonFactory();
			generator = factory.createJsonGenerator(writer);
			generator.setCodec(new ObjectMapper().setSerializerProvider(sp));
			generator.writeStartObject();
			// 修改成功
			if (bSuccess) {
				if (null != stringInfoMap) {
					Iterator<String> strIter = stringInfoMap.keySet()
							.iterator();
					while (strIter.hasNext()) {
						String key = strIter.next();
						generator.writeStringField(key, ObjectUtils.toString(
								stringInfoMap.get(key), ""));

					}
				}
				if (null != numberInfoMap) {
					Iterator<String> numIter = numberInfoMap.keySet()
							.iterator();
					while (numIter.hasNext()) {
						String key = numIter.next();
						generator.writeNumberField(key, numberInfoMap.get(key));
					}
				}
				if (null != objInfoMap) {
					Iterator<String> objIter = objInfoMap.keySet().iterator();
					while (objIter.hasNext()) {
						String key = objIter.next();
						generator.writeObjectField(key, objInfoMap.get(key));
					}
				}
				generator.writeNumberField("status", 200);
			} else {
				generator.writeStringField(ResultPropertyes.ERROR_CODE,
						(String) errorMap.get(ResultPropertyes.ERROR_CODE));
				generator.writeStringField(ResultPropertyes.ERROR_INFO,
						(String) errorMap.get(ResultPropertyes.ERROR_INFO));
			}
			generator.writeEndObject();
			generator.close();
		} catch (IOException e) {
			e.printStackTrace(System.out);
		}
		return null;
	}
	static class EmptySerializer extends JsonSerializer<Object> {

		@Override
		public void serialize(Object value, JsonGenerator jgen,SerializerProvider provider) throws IOException,JsonProcessingException {
			jgen.writeString("");
		}

	}

}
