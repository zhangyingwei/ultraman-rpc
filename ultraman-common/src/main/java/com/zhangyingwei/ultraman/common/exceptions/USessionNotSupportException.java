package com.zhangyingwei.ultraman.common.exceptions;

/**
 * @author zhangyw
 * @date: 2019/1/18
 * @desc:
 */
public class USessionNotSupportException extends Exception {
    public USessionNotSupportException() {
        super();
    }

    public USessionNotSupportException(String message) {
        super(message);
    }

    public USessionNotSupportException(String message, Throwable cause) {
        super(message, cause);
    }

    public USessionNotSupportException(Throwable cause) {
        super(cause);
    }

    protected USessionNotSupportException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
