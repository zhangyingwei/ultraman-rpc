package com.zhangyingwei.ultraman.common.exceptions;

/**
 * @author zhangyw
 * @date: 2019/1/18
 * @desc:
 */
public class NullResponseExceotion extends Exception {
    public NullResponseExceotion() {
        super();
    }

    public NullResponseExceotion(String message) {
        super(message);
    }

    public NullResponseExceotion(String message, Throwable cause) {
        super(message, cause);
    }

    public NullResponseExceotion(Throwable cause) {
        super(cause);
    }

    protected NullResponseExceotion(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
