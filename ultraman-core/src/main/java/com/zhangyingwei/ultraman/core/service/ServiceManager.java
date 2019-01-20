package com.zhangyingwei.ultraman.core.service;

import com.zhangyingwei.ultraman.common.exceptions.UServiceNotFoundException;
import com.zhangyingwei.ultraman.session.UPackageKit;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zhangyw
 * @date: 2019/1/18
 * @desc:
 */

@Slf4j
public class ServiceManager {
    private Lock lock = new ReentrantLock();
    private UPackageKit packageKit = new UPackageKit();

    /**
     * class name : object
     */
    private Map<String, Object> clazzObjectMap = new ConcurrentHashMap<String, Object>();

    /**
     * get or create service instance in cache
     * @param serviceClassName
     * @param <T>
     * @return
     * @throws UServiceNotFoundException
     */
    public <T>T getService(String serviceClassName) throws UServiceNotFoundException {
        final Lock getLock = lock;
        getLock.lock();
        try {
            Object serviceObject =clazzObjectMap.get(serviceClassName);
            if (serviceObject == null) {
                throw new UServiceNotFoundException(serviceClassName);
            } else {
                return (T) this.cloneObject(serviceObject);
            }
        }catch (Exception e){
            throw new UServiceNotFoundException(e);
        }finally {
            getLock.unlock();
        }
    }

    /**
     * 通过序列化进行对象深度克隆
     * @param serviceObject
     * @return
     */
    private Object cloneObject(Object serviceObject) throws IOException {
        return this.packageKit.cloneObject(serviceObject);
    }


    private String getObjectKey(String serviceClassName, Class[] argTypes) {
        return serviceClassName.concat(Optional.ofNullable(argTypes).orElse(new Class[0]).length+"");
    }

    private Object newInstance(String className, Class[] argTypes, Object[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        if (argTypes == null || argTypes.length == 0) {
            return Class.forName(className).newInstance();
        }
        Constructor<?> constructor = Class.forName(className).getConstructor(argTypes);
        return constructor.newInstance(args);
    }

    public void bindService(String name,Object service) {
        this.clazzObjectMap.put(name, service);
        log.info("bind service: {} - {}", name, service);
    }
}
