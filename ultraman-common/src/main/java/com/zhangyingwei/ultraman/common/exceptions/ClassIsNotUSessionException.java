package com.zhangyingwei.ultraman.common.exceptions;

/**
 * @author zhangyw
 * @date: 2019/1/18
 * @desc:
 */
public class ClassIsNotUSessionException extends Exception {
    public ClassIsNotUSessionException() {
        super();
    }

    public ClassIsNotUSessionException(String message) {
        super(message);
    }

    public ClassIsNotUSessionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClassIsNotUSessionException(Throwable cause) {
        super(cause);
    }

    protected ClassIsNotUSessionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
