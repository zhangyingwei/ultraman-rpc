package com.zhangyingwei.ultraman.session;


import com.zhangyingwei.ultraman.common.exceptions.ClassIsNotUSessionException;
import com.zhangyingwei.ultraman.common.exceptions.USessionNotSupportException;

import java.util.List;

/**
 * @author zhangyw
 * @date: 2019/1/18
 * @desc:
 */
public class UPackageKit {

    public byte[] pack(IUSession session) throws USessionNotSupportException {
        if (session instanceof URequest) {
            return this.packRequest((URequest) session);
        } else if (session instanceof UResponse) {
            return this.packResponse((UResponse) session);
        } else {
            throw new USessionNotSupportException(session.getClass().getName());
        }
    }

    private byte[] packResponse(UResponse response) {
        return response.toBytes();
    }

    private byte[] packRequest(URequest request) {
        return request.toBytes();
    }

    public IUSession unPack(byte[] bytes,Class clazz) throws ClassIsNotUSessionException, ClassNotFoundException {
        if (URequest.class.equals(clazz)) {
            return (IUSession) this.unPackRequest(bytes);
        } else if (UResponse.class.equals(clazz)) {
            return (IUSession) this.unPackResponse(bytes);
        } else {
            throw new ClassIsNotUSessionException(clazz.getName());
        }
    }

    private UResponse unPackResponse(byte[] bytes) {
        return new UResponse(bytes);
    }

    private URequest unPackRequest(byte[] bytes) throws ClassNotFoundException {
        return new URequest(bytes);
    }
}