package com.lenovo.vctl.dal.dao.util;

import java.util.List;

import com.lenovo.vctl.dal.cache.NullObjectContent;
import com.lenovo.vctl.dal.dao.model.MapInfo;

public class NullObjectHelper {

	public static void removeNullMapsOfObject(Object object) throws Exception{
		List<MapInfo> newMaps = ObjectUtil.getMapInfoList(object);
		for (MapInfo info : newMaps) {
			NullObjectContent.remove(Constants.NullObjectPrefix+"_"+info.getRegion()+"_"+info.getKey()+"");
		}
	}
}
