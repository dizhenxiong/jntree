package com.lenovo.vctl.dal.cache.memcached.handler;

import java.util.Date;
import org.apache.log4j.Logger;

import com.lenovo.vctl.dal.cache.memcached.client.MemCachedClientImpl;


/**
 * 
 * 
 * 
 * @author allenshen date: Nov 26, 2008 3:48:34 PM Copyright 2008 Sohu.com Inc.
 *         All Rights Reserved.
 */
public class NativeHandler {

    // logger
    private static Logger log = Logger.getLogger(NativeHandler.class.getName());

    /**
     * Detemine of object can be natively serialized by this class.
     * 
     * @param value
     *            Object to test.
     * @return true/false
     */
    public static boolean isHandled(Object value) {

        return (value instanceof Byte || value instanceof Boolean || value instanceof Integer || value instanceof Long
                || value instanceof Character || value instanceof String || value instanceof StringBuffer
                || value instanceof Float || value instanceof Short || value instanceof Double || value instanceof Date
                || value instanceof StringBuilder || value instanceof byte[]) ? true : false;
    }

    /**
     * Returns the flag for marking the type of the byte array.
     * 
     * @param value
     *            Object we are storing.
     * @return int marker
     */
    public static int getMarkerFlag(Object value) {

        if (value instanceof Byte)
            return MemCachedClientImpl.MARKER_BYTE;

        if (value instanceof Boolean)
            return MemCachedClientImpl.MARKER_BOOLEAN;

        if (value instanceof Integer)
            return MemCachedClientImpl.MARKER_INTEGER;

        if (value instanceof Long)
            return MemCachedClientImpl.MARKER_LONG;

        if (value instanceof Character)
            return MemCachedClientImpl.MARKER_CHARACTER;

        if (value instanceof String)
            return MemCachedClientImpl.MARKER_STRING;

        if (value instanceof StringBuffer)
            return MemCachedClientImpl.MARKER_STRINGBUFFER;

        if (value instanceof Float)
            return MemCachedClientImpl.MARKER_FLOAT;

        if (value instanceof Short)
            return MemCachedClientImpl.MARKER_SHORT;

        if (value instanceof Double)
            return MemCachedClientImpl.MARKER_DOUBLE;

        if (value instanceof Date)
            return MemCachedClientImpl.MARKER_DATE;

        if (value instanceof StringBuilder)
            return MemCachedClientImpl.MARKER_STRINGBUILDER;

        if (value instanceof byte[])
            return MemCachedClientImpl.MARKER_BYTEARR;

        return -1;
    }

    /**
     * Encodes supported types
     * 
     * @param value
     *            Object to encode.
     * @return byte array
     * 
     * @throws Exception
     *             If fail to encode.
     */
    public static byte[] encode(Object value) throws Exception {

        if (value instanceof Byte)
            return encode((Byte) value);

        if (value instanceof Boolean)
            return encode((Boolean) value);

        if (value instanceof Integer)
            return encode(((Integer) value).intValue());

        if (value instanceof Long)
            return encode(((Long) value).longValue());

        if (value instanceof Character)
            return encode((Character) value);

        if (value instanceof String)
            return encode((String) value);

        if (value instanceof StringBuffer)
            return encode((StringBuffer) value);

        if (value instanceof Float)
            return encode(((Float) value).floatValue());

        if (value instanceof Short)
            return encode((Short) value);

        if (value instanceof Double)
            return encode(((Double) value).doubleValue());

        if (value instanceof Date)
            return encode((Date) value);

        if (value instanceof StringBuilder)
            return encode((StringBuilder) value);

        if (value instanceof byte[])
            return encode((byte[]) value);

        return null;
    }

    protected static byte[] encode(Byte value) {
        byte[] b = new byte[1];
        b[0] = value.byteValue();
        return b;
    }

    protected static byte[] encode(Boolean value) {
        byte[] b = new byte[1];

        if (value.booleanValue())
            b[0] = 1;
        else
            b[0] = 0;

        return b;
    }

    protected static byte[] encode(int value) {
        return getBytes(value);
    }

    protected static byte[] encode(long value) throws Exception {
        return getBytes(value);
    }

    protected static byte[] encode(Date value) {
        return getBytes(value.getTime());
    }

    protected static byte[] encode(Character value) {
        return encode(value.charValue());
    }

    protected static byte[] encode(String value) throws Exception {
        return value.getBytes("UTF-8");
    }

    protected static byte[] encode(StringBuffer value) throws Exception {
        return encode(value.toString());
    }

    protected static byte[] encode(float value) throws Exception {
        return encode((int) Float.floatToIntBits(value));
    }

    protected static byte[] encode(Short value) throws Exception {
        return encode((int) value.shortValue());
    }

    protected static byte[] encode(double value) throws Exception {
        return encode((long) Double.doubleToLongBits(value));
    }

    protected static byte[] encode(StringBuilder value) throws Exception {
        return encode(value.toString());
    }

    protected static byte[] encode(byte[] value) {
        return value;
    }

    protected static byte[] getBytes(long value) {
        byte[] b = new byte[8];
        b[0] = (byte) ((value >> 56) & 0xFF);
        b[1] = (byte) ((value >> 48) & 0xFF);
        b[2] = (byte) ((value >> 40) & 0xFF);
        b[3] = (byte) ((value >> 32) & 0xFF);
        b[4] = (byte) ((value >> 24) & 0xFF);
        b[5] = (byte) ((value >> 16) & 0xFF);
        b[6] = (byte) ((value >> 8) & 0xFF);
        b[7] = (byte) ((value >> 0) & 0xFF);
        return b;
    }

    protected static byte[] getBytes(int value) {
        byte[] b = new byte[4];
        b[0] = (byte) ((value >> 24) & 0xFF);
        b[1] = (byte) ((value >> 16) & 0xFF);
        b[2] = (byte) ((value >> 8) & 0xFF);
        b[3] = (byte) ((value >> 0) & 0xFF);
        return b;
    }

    /**
     * Decodes byte array using memcache flag to determine type.
     * 
     * @param b
     * @param marker
     * @return
     * @throws Exception
     */
    public static Object decode(byte[] b, int flag) throws Exception {

        if (b.length < 1)
            return null;

        if ((flag & MemCachedClientImpl.MARKER_BYTE) == MemCachedClientImpl.MARKER_BYTE)
            return decodeByte(b);

        if ((flag & MemCachedClientImpl.MARKER_BOOLEAN) == MemCachedClientImpl.MARKER_BOOLEAN)
            return decodeBoolean(b);

        if ((flag & MemCachedClientImpl.MARKER_INTEGER) == MemCachedClientImpl.MARKER_INTEGER)
            return decodeInteger(b);

        if ((flag & MemCachedClientImpl.MARKER_LONG) == MemCachedClientImpl.MARKER_LONG)
            return decodeLong(b);

        if ((flag & MemCachedClientImpl.MARKER_CHARACTER) == MemCachedClientImpl.MARKER_CHARACTER)
            return decodeCharacter(b);

        if ((flag & MemCachedClientImpl.MARKER_STRING) == MemCachedClientImpl.MARKER_STRING)
            return decodeString(b);

        if ((flag & MemCachedClientImpl.MARKER_STRINGBUFFER) == MemCachedClientImpl.MARKER_STRINGBUFFER)
            return decodeStringBuffer(b);

        if ((flag & MemCachedClientImpl.MARKER_FLOAT) == MemCachedClientImpl.MARKER_FLOAT)
            return decodeFloat(b);

        if ((flag & MemCachedClientImpl.MARKER_SHORT) == MemCachedClientImpl.MARKER_SHORT)
            return decodeShort(b);

        if ((flag & MemCachedClientImpl.MARKER_DOUBLE) == MemCachedClientImpl.MARKER_DOUBLE)
            return decodeDouble(b);

        if ((flag & MemCachedClientImpl.MARKER_DATE) == MemCachedClientImpl.MARKER_DATE)
            return decodeDate(b);

        if ((flag & MemCachedClientImpl.MARKER_STRINGBUILDER) == MemCachedClientImpl.MARKER_STRINGBUILDER)
            return decodeStringBuilder(b);

        if ((flag & MemCachedClientImpl.MARKER_BYTEARR) == MemCachedClientImpl.MARKER_BYTEARR)
            return decodeByteArr(b);

        return null;
    }

    // decode methods
    protected static Byte decodeByte(byte[] b) {
//        return new Byte(b[0]);
    	return Byte.valueOf(b[0]);//edit by songkun
    }

    protected static Boolean decodeBoolean(byte[] b) {
        boolean value = b[0] == 1;
        return (value) ? Boolean.TRUE : Boolean.FALSE;
    }

    protected static Integer decodeInteger(byte[] b) {
//        return new Integer(toInt(b));
    	return Integer.valueOf(toInt(b));
    }

    protected static Long decodeLong(byte[] b) throws Exception {
//        return new Long(toLong(b));
    	return Long.valueOf(toLong(b));
    }

    protected static Character decodeCharacter(byte[] b) {
//        return new Character((char) decodeInteger(b).intValue());
    	return Character.valueOf((char)decodeInteger(b).intValue());
        
    }

    protected static String decodeString(byte[] b) throws Exception {
        return new String(b, "UTF-8");
    }

    protected static StringBuffer decodeStringBuffer(byte[] b) throws Exception {
        return new StringBuffer(decodeString(b));
    }

    protected static Float decodeFloat(byte[] b) throws Exception {
        Integer l = decodeInteger(b);
        return new Float(Float.intBitsToFloat(l.intValue()));
    }

    protected static Short decodeShort(byte[] b) throws Exception {
//        return new Short((short) decodeInteger(b).intValue());
    	return Short.valueOf((short) decodeInteger(b).intValue());
    }

    protected static Double decodeDouble(byte[] b) throws Exception {
        Long l = decodeLong(b);
        return new Double(Double.longBitsToDouble(l.longValue()));
    }

    protected static Date decodeDate(byte[] b) {
        return new Date(toLong(b));
    }

    protected static StringBuilder decodeStringBuilder(byte[] b) throws Exception {
        return new StringBuilder(decodeString(b));
    }

    protected static byte[] decodeByteArr(byte[] b) {
        return b;
    }

    /**
     * This works by taking each of the bit patterns and converting them to ints
     * taking into account 2s complement and then adding them..
     * 
     * @param b
     * @return
     */
    protected static int toInt(byte[] b) {
        return (((((int) b[3]) & 0xFF) << 32) + ((((int) b[2]) & 0xFF) << 40) + ((((int) b[1]) & 0xFF) << 48) + ((((int) b[0]) & 0xFF) << 56));
    }

    protected static long toLong(byte[] b) {
        return ((((long) b[7]) & 0xFF) + ((((long) b[6]) & 0xFF) << 8) + ((((long) b[5]) & 0xFF) << 16)
                + ((((long) b[4]) & 0xFF) << 24) + ((((long) b[3]) & 0xFF) << 32) + ((((long) b[2]) & 0xFF) << 40)
                + ((((long) b[1]) & 0xFF) << 48) + ((((long) b[0]) & 0xFF) << 56));
    }
}
