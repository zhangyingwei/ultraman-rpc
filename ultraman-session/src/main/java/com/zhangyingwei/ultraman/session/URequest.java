package com.zhangyingwei.ultraman.session;

import lombok.Data;
import lombok.ToString;

/**
 * request of rpc
 * @author zhangyw
 * @date: 2019/1/18
 * @desc:
 */
@Data
@ToString
public class URequest implements IUSession {
    private String serviceClassName;
    private String methodName;
    private Object[] args;
    private String[] argsTypes;
    private final static String KEY_CLAZZ = "className";
    private static final String KEY_METHOD = "methodName";
    private final static String KEY_ARGS = "args";
    private static final String KEY_ARG_TYPES = "argTypes";

    public URequest(String serviceClassName, String methodName, String[] argsTypes, Object[] args) {
        this.serviceClassName = serviceClassName;
        this.methodName = methodName;
        this.args = args;
        this.argsTypes = argsTypes;
    }
}
