package com.zhangyingwei.ultraman.common.exceptions;

/**
 * @author zhangyw
 * @date: 2019/1/18
 * @desc:
 */
public class UServiceNotFoundException extends Exception {
    public UServiceNotFoundException() {
        super();
    }

    public UServiceNotFoundException(String message) {
        super(message);
    }

    public UServiceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public UServiceNotFoundException(Throwable cause) {
        super(cause);
    }

    protected UServiceNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
