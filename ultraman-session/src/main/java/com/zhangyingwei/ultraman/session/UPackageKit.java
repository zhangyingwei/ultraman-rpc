package com.zhangyingwei.ultraman.session;


import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.zhangyingwei.ultraman.common.exceptions.ClassIsNotUSessionException;
import com.zhangyingwei.ultraman.common.exceptions.USessionNotSupportException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author zhangyw
 * @date: 2019/1/18
 * @desc:
 */
public class UPackageKit {

    public byte[] pack(IUSession session) throws USessionNotSupportException {
        try {
            if (session instanceof URequest) {
                return this.packRequest((URequest) session);
            } else if (session instanceof UResponse) {
                return this.packResponse((UResponse) session);
            } else {
                throw new USessionNotSupportException(session.getClass().getName());
            }
        } catch (Exception e) {
            throw new USessionNotSupportException(e);
        }
    }

    private byte[] packResponse(UResponse response) throws IOException {
        return this.objectToBytes(response);
    }

    private byte[] packRequest(URequest request) throws IOException {
        return this.objectToBytes(request);
    }

    private byte[] objectToBytes(Object object) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Hessian2Output hessian2Output = new Hessian2Output(outputStream);
        hessian2Output.writeObject(object);
        hessian2Output.flush();
        return outputStream.toByteArray();
    }

    private Object bytesToObject(byte[] bytes) throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        Hessian2Input hessian2Input = new Hessian2Input(inputStream);
        return hessian2Input.readObject();
    }

    public IUSession unPack(byte[] bytes,Class clazz) throws ClassIsNotUSessionException, IOException {
        if (URequest.class.equals(clazz)) {
            return this.unPackRequest(bytes);
        } else if (UResponse.class.equals(clazz)) {
            return this.unPackResponse(bytes);
        } else {
            throw new ClassIsNotUSessionException(clazz.getName());
        }
    }

    private UResponse unPackResponse(byte[] bytes) throws IOException {
        return (UResponse) this.bytesToObject(bytes);
    }

    private URequest unPackRequest(byte[] bytes) throws IOException {
        return (URequest) this.bytesToObject(bytes);
    }

    public Object cloneObject(Object from) throws IOException {
        return this.bytesToObject(this.objectToBytes(from));
    }
}