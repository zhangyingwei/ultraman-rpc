package com.zhangyingwei.ultraman.core.executor;

import com.zhangyingwei.ultraman.common.exceptions.UServiceExecuteException;
import com.zhangyingwei.ultraman.common.exceptions.UServiceNotFoundException;
import com.zhangyingwei.ultraman.core.service.ServiceManager;
import com.zhangyingwei.ultraman.session.URequest;
import com.zhangyingwei.ultraman.session.UResponse;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

/**
 * @author zhangyw
 * @date: 2019/1/18
 * @desc:
 */
public class MsgExecutor implements IUExecutor {
    private ServiceManager serviceManager;

    public MsgExecutor(ServiceManager serviceManager) {
        this.serviceManager = serviceManager;
    }

    @Override
    public UResponse execure(URequest request) throws UServiceNotFoundException, NoSuchMethodException, ClassNotFoundException, UServiceExecuteException {
        Object service = this.serviceManager.getService(request.getServiceClassName());
        UResponse response = null;
        try {
            Object result = this.doService(service, request.getMethodName(), request.getArgsTypes(), request.getArgs());
            response = new UResponse(result, UResponse.State.SUCCESS);
        } catch (InvocationTargetException | IllegalAccessException e) {
            response = new UResponse(e.getLocalizedMessage(), UResponse.State.ERROR);
            throw new UServiceExecuteException(e);
        }finally {
            return response;
        }
    }

    private Object doService(Object service, String methodName, String[] argsTypes, Object[] args) throws NoSuchMethodException, ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        Method method = service.getClass().getMethod(methodName, this.getArgsClass(argsTypes));
        return method.invoke(service, args);
    }

    private Class[] getArgsClass(String[] argsTypes) throws ClassNotFoundException {
        argsTypes = Optional.ofNullable(argsTypes).orElse(new String[0]);
        Class[] argClass = new Class[argsTypes.length];
        for (int i = 0; i < argsTypes.length; i++) {
            argClass[i] = Class.forName(argsTypes[i]);
        }
        return argClass;
    }
}
