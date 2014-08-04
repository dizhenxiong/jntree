package com.lenovo.vctl.dal.cache.binding.classcatalog;

import java.io.ObjectStreamClass;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.sleepycat.bind.serial.ClassCatalog;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.util.UtfOps;

public class SNSClassCatalog implements ClassCatalog {
	private static Logger logger = Logger.getLogger(SNSClassCatalog.class);

	private static Map<String, ObjectStreamClass> idToDescMap = new ConcurrentHashMap<String, ObjectStreamClass>();
	private static Map<String, byte[]> nameToIdMap = new ConcurrentHashMap<String, byte[]>();
	static {
		registerClassInfo(Boolean.class.getName());
		registerClassInfo(Byte.class.getName());
		registerClassInfo(Character.class.getName());
		registerClassInfo(Double.class.getName());
		registerClassInfo(Float.class.getName());
		registerClassInfo(Integer.class.getName());
		registerClassInfo(Short.class.getName());
		registerClassInfo(String.class.getName());
		registerClassInfo(Long.class.getName());
		registerClassInfo(Number.class.getName());
	}

	@Override
	public void close() throws DatabaseException {

	}

	@Override
	public ObjectStreamClass getClassFormat(byte[] classID) throws DatabaseException, ClassNotFoundException {
		String strId = new String(classID);
		ObjectStreamClass desc = (ObjectStreamClass) idToDescMap.get(strId);
		if (desc == null) {
			logger.error(strId + " don't find id, please registerClassInfo, Auto register");
			desc = getObjectStreamClass(strId);
			if (desc != null) {
				registerClassInfo(strId);
			}
		}
		return desc;
	}

	@Override
	public byte[] getClassID(ObjectStreamClass classDesc) throws DatabaseException, ClassNotFoundException {
		String className = classDesc.getName();
		byte[] id = (byte[]) nameToIdMap.get(className);
		if (id == null) {
			logger.error(className + " don't find id, please registerClassInfo, Auto register");
			id = getByte(className);
			if (id !=null) {
				registerClassInfo(className);
			}
			
		}
		return id;
	}

	public static void registerClassInfo(String className) {
		if (StringUtils.isNotEmpty(className)) {
			if (nameToIdMap.containsKey(className)) {
				logger.warn(className + " " + " exist!, please don't register it");
			} else {
				ObjectStreamClass osc = getObjectStreamClass(className);
				if (osc != null) {
					byte[] id = getByte(className);
					nameToIdMap.put(className, id);
					idToDescMap.put(new String(id), osc);
				}
			}
		} else {
			logger.error("clazzname is null or tempty");
		}
	}

	
	
	
	private static byte[] getByte(String className) {
		char[] nameChars = className.toCharArray();
		byte[] keyBytes = new byte[UtfOps.getByteLength(nameChars)];
		UtfOps.charsToBytes(nameChars, 0, keyBytes, 0, nameChars.length);
		return keyBytes;
	}


	private static ObjectStreamClass getObjectStreamClass(String className) {
		Class clazz = null;
		ObjectStreamClass osc = null;
		try {
			clazz = Class.forName(className);
			osc = ObjectStreamClass.lookup(clazz);
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage());
		}
		return osc;
	}
	public static void main(String[] args) {
		System.out.println(getObjectStreamClass("com.sohu.test.model.Personal"));
		
	}
}
