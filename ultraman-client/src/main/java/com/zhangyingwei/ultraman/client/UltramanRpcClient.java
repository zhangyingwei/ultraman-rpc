package com.zhangyingwei.ultraman.client;

import com.zhangyingwei.ultraman.session.URequest;
import com.zhangyingwei.ultraman.session.UResponse;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangyw
 * @date: 2019/1/17
 * @desc:
 */
public class UltramanRpcClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost",8000);
        OutputStream os=socket.getOutputStream();//字节输出流
        new Thread(() -> {
            while (true) {
                try {
                    os.write(getOutPutBytes());
                    os.flush();
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
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

    private static byte[] getOutPutBytes() {
        URequest request = new URequest(null,null,null,null);
        return request.toBytes();
    }
}
