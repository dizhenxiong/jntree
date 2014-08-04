package com.lenovo.vctl.apps.image.upload.util;


import java.io.InputStream;
import java.util.Properties;


public class StorageProperty extends Properties{
	private static String CONFIG_FILE = "storage.properties";
	private static StorageProperty p; 
	private StorageProperty(){
		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream is = classLoader.getResourceAsStream(CONFIG_FILE);
			load(is);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static StorageProperty getInstance(){
		if(p == null){
			synchronized(StorageProperty.class){
				if(p == null){
					p = new StorageProperty();
				}
			}
		}
		return p;
	}


}
