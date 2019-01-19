package com.zhangyingwei.ultraman.client.service;

import com.zhangyingwei.ultraman.client.net.USessionClient;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * service factory
 * used to create proxy of service
 */
public class ServiceFactory {

    public <T>T bulidBean(Class clazz, USessionClient sessionClient) {
        ServiceMethodHandler serviceMethodHandler = new ServiceMethodHandler(sessionClient);
        return (T) Proxy.newProxyInstance(
                clazz.getClassLoader(),
                new Class[]{clazz},
                serviceMethodHandler
        );
    }
}
