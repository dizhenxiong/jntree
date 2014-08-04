package com.lenovo.vctl.dal.cache.binding;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.lenovo.vctl.dal.cache.binding.classcatalog.SNSClassCatalog;
import com.sleepycat.bind.EntryBinding;
import com.sleepycat.bind.serial.ClassCatalog;
import com.sleepycat.bind.serial.SerialBinding;
import com.sleepycat.je.DatabaseEntry;

public class BindingHelper {
	private static Logger logger = Logger.getLogger(BindingHelper.class);
	private static ClassCatalog catalog = new SNSClassCatalog();
	private static Map<String, EntryBinding<Object>> entryBindingMap = new ConcurrentHashMap<String, EntryBinding<Object>>();

	@SuppressWarnings("unchecked")
	public static EntryBinding<Object> getEntryBinding(Class clazz) {
		if (clazz != null) {
			String className = clazz.getName();
			EntryBinding<Object> binding = entryBindingMap.get(className);
			if (binding == null) {
				binding = new SerialBinding<Object>(catalog, clazz);
				entryBindingMap.put(className, binding);
			}
			return binding;

		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public static EntryBinding<Object> getEntryBinding(String className) {
		if (StringUtils.isNotBlank(className)) {
			EntryBinding<Object> binding = entryBindingMap.get(className);
			if (binding == null) {
				try {
					Class clazz = Class.forName(className);
					binding = new SerialBinding<Object>(catalog, clazz);
					entryBindingMap.put(className, binding);
				} catch (ClassNotFoundException e) {
					logger.error(e.getMessage());
					e.printStackTrace(System.err);
				}
			}
			return binding;
		} else {
			return null;
		}

	}

	@SuppressWarnings("unchecked")
	public static byte[] getByteFromObject(Object o) {
		if (o != null) {
			//EntryBinding dataBinding = BindingHelper.getEntryBinding(o.getClass());
		    EntryBinding dataBinding = BindingHelper.getEntryBinding(Serializable.class);
			DatabaseEntry entry = new DatabaseEntry();
			dataBinding.objectToEntry(o, entry);
			byte[] bytes = new byte[entry.getSize()];
			System.arraycopy(entry.getData(), entry.getOffset(), bytes, 0, entry.getSize());
			return bytes;
		} else {
			return null;
		}
	}
	
	public static Object getObjectFromByte(byte[] bytes, String className) {
		if (bytes != null && bytes.length >0) {
			DatabaseEntry entry = new DatabaseEntry(bytes);
			//EntryBinding dataBinding = BindingHelper.getEntryBinding(className);
			EntryBinding dataBinding = BindingHelper.getEntryBinding(Serializable.class.getName());
			return dataBinding.entryToObject(entry);
		} else {
			return null;
		}
	}	

}
