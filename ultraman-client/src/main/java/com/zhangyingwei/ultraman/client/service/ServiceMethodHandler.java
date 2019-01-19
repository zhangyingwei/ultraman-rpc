package com.zhangyingwei.ultraman.client.service;

import com.zhangyingwei.ultraman.client.exception.USessionExecuteException;
import com.zhangyingwei.ultraman.client.net.USessionClient;
import com.zhangyingwei.ultraman.session.URequest;
import com.zhangyingwei.ultraman.session.UResponse;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * service factory
 * used to create proxy of service
 */
public class ServiceMethodHandler<T> implements InvocationHandler {

    private final USessionClient sessionClient;

    public ServiceMethodHandler(USessionClient sessionClient) {
        this.sessionClient = sessionClient;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable  {
        String className = method.getDeclaringClass().getName();
        String[] argTypes = this.getArgTypes(args);
        URequest request = new URequest(className, method.getName(), argTypes, args);
        UResponse response = this.sessionClient.execute(request);
        if (UResponse.State.SUCCESS.equals(response.getState())) {
            return response.getResult();
        } else {
            return null;
        }
    }

    private String[] getArgTypes(Object[] args) {
        if (args == null) {
            return null;
        }
        String[] argTypes = new String[args.length];
        for (int i = 0; i < args.length; i++) {
            argTypes[i] = args[i].getClass().getName();
        }
        return argTypes;
    }
}
