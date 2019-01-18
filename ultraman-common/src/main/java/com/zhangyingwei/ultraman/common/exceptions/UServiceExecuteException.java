package com.zhangyingwei.ultraman.common.exceptions;

/**
 * @author zhangyw
 * @date: 2019/1/18
 * @desc:
 */
public class UServiceExecuteException extends Exception {
    public UServiceExecuteException() {
        super();
    }

    public UServiceExecuteException(String message) {
        super(message);
    }

    public UServiceExecuteException(String message, Throwable cause) {
        super(message, cause);
    }

    public UServiceExecuteException(Throwable cause) {
        super(cause);
    }

    protected UServiceExecuteException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
