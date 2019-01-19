package com.zhangyingwei.ultraman.client.exception;

public class USessionExecuteException extends Exception {
    public USessionExecuteException() {
        super();
    }

    public USessionExecuteException(String message) {
        super(message);
    }

    public USessionExecuteException(String message, Throwable cause) {
        super(message, cause);
    }

    public USessionExecuteException(Throwable cause) {
        super(cause);
    }

    protected USessionExecuteException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
