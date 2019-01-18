package com.zhangyingwei.ultraman.samples.basic;

import com.zhangyingwei.ultraman.common.exceptions.USessionNotSupportException;
import com.zhangyingwei.ultraman.samples.basic.services.IHelloWorldService;
import com.zhangyingwei.ultraman.session.UPackageKit;
import com.zhangyingwei.ultraman.session.URequest;
import com.zhangyingwei.ultraman.session.UResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangyw
 * @date: 2019/1/18
 * @desc:
 */
public class BasicClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost",8000);
        OutputStream os=socket.getOutputStream();//字节输出流
        new Thread(() -> {
            while (true) {
                try {
                    os.write(getOutPutBytes());
                    os.flush();
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (USessionNotSupportException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        InputStream in = socket.getInputStream();
        new Thread(() -> {
            while (true) {
                byte[] bytes = new byte[0];
                try {
                    bytes = new byte[in.available()];
                    int index = in.read(bytes);
                    if (index > 0) {
                        System.out.println(receive(bytes));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private static UResponse receive(byte[] bytes) {
        UResponse response = new UResponse(bytes);
        return response;
    }

    private static byte[] getOutPutBytes() throws USessionNotSupportException {
        URequest request = new URequest(IHelloWorldService.class.getName(),"say",null,null);
//        URequest request = new URequest(IHelloWorldService.class.getName(),"say",
//                new String[]{String.class.getName()},new Object[]{null});
        return new UPackageKit().pack(request);
    }
}
