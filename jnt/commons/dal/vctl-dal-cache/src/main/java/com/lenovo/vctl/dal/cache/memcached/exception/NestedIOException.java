/**
 * 
 */
package com.lenovo.vctl.dal.cache.memcached.exception;

import java.io.*;

public class NestedIOException extends IOException {

     public NestedIOException( Throwable cause ) {
        super( cause.getMessage() );
        super.initCause( cause );
    }

    public NestedIOException( String message, Throwable cause ) {
        super( message );
        initCause( cause );
    }
}
