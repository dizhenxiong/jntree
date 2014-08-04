package com.lenovo.vctl.dal.cache.memcached.stream;

import java.util.*;
import java.util.zip.*;
import java.io.*;
/**
 * 
 * 
 * 
 * @author allenshen
 * date: Nov 26, 2008 3:46:59 PM
 * Copyright 2008 Sohu.com Inc. All Rights Reserved.
 */
public class ContextObjectInputStream extends ObjectInputStream { 

	ClassLoader mLoader;
    
	public ContextObjectInputStream( InputStream in, ClassLoader loader ) throws IOException, SecurityException {
		super( in );
		mLoader = loader;
	}
	
	protected Class resolveClass( ObjectStreamClass v ) throws IOException, ClassNotFoundException {
		if ( mLoader == null )
			return super.resolveClass( v );
		else
			return Class.forName( v.getName(), true, mLoader );
	}
}
