package com.lenovo.vctl.dal.dao.exception;

public class StrategyException extends Exception {
    /**
     * 
     */
    private static final long serialVersionUID = 8188585329675336173L;

    public StrategyException(Exception e) {
        super(e);
    }

    public StrategyException(String message) {
        super(message);

    }
}
