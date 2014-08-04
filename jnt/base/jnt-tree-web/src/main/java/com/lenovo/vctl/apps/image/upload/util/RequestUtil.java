package com.lenovo.vctl.apps.image.upload.util;


import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

public class RequestUtil {
    /**
     * UserAgent 各位表示：
     *
     * Software：软件标识
     * Version：软件版本
     * OemTag：软件渠道标识
     * Platform：平台标识，比如PC-Win7、PC-METRO、PHONE,TV,PAD等
     * DeviceVendor：设备生产厂商，如Lenovo、Samsung
     * DeviceModel：产品型号，如K860i
     * Device_TYPE:设备类型(PC,PHONE,PAD,TV)
     * @param request
     * @return
     */
    public static Map<String, String> getHeaders(HttpServletRequest request) {

        Map<String, String> headers = new HashMap<String, String>();
        @SuppressWarnings("unchecked")
        Enumeration<String> headerNames = request.getHeaderNames();
        if (null == headerNames) return headers;

        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.put(headerName, request.getHeader(headerName));
        }

        return headers;
    }

    /**
     * 获取全部headers并转为json串
     * @param request
     * @return
     */
    public static String getHeadersJsonString(HttpServletRequest request) {
        String json = StringUtils.EMPTY;

        Map<String, String> headers = getHeaders(request);
        if (MapUtils.isNotEmpty(headers)) {
            json = GsonUtil.toJson(headers);
        }

        return json;
    }

    /**
     * 获取全部headers
     * @param request
     * @return
     */
    public static Map<String, String> getHeadersIgnoreCase(HttpServletRequest request) {

        Map<String, String> headers = new HashMap<String, String>();
        @SuppressWarnings("unchecked")
        Enumeration<String> headerNames = request.getHeaderNames();
        if (null == headerNames) return headers;

        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.put(headerName.toLowerCase(), request.getHeader(headerName));
        }
        return headers;
    }

    /**
     * 获取全部headers
     * @param request
     * @return
     */
    public static String getOemTag(HttpServletRequest request) {
        Map<String, String> map = RequestUtil.getHeadersIgnoreCase(request);
        return map.get("oem-tag");
    }

    /**
     * 获取user-agent
     * @param request
     * @return
     */
    public static String getUserAgent(HttpServletRequest request) {
        Map<String, String> map = RequestUtil.getHeadersIgnoreCase(request);
        return map.get("user-agent");
    }

    //取Device值
    public static String getDeviceFromHeader(HttpServletRequest request) {
        Map<String, String> map = RequestUtil.getHeaders(request);
        return map.get("Platform");
    }

}
