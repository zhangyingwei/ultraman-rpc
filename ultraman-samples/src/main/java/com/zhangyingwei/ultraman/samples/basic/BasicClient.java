package com.zhangyingwei.ultraman.samples.basic;

import com.zhangyingwei.ultraman.common.exceptions.USessionNotSupportException;
import com.zhangyingwei.ultraman.samples.basic.services.IHelloWorldService;
import com.zhangyingwei.ultraman.session.UPackageKit;
import com.zhangyingwei.ultraman.session.URequest;
import com.zhangyingwei.ultraman.session.UResponse;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangyw
 * @date: 2019/1/18
 * @desc:
 */
public class BasicClient {
    public static void main(String[] args) throws IOException, USessionNotSupportException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 8000));

        socketChannel.write(ByteBuffer.wrap(getOutPutBytes()));

        //read
        InputStream in = socketChannel.socket().getInputStream();
        ReadableByteChannel readChannel = Channels.newChannel(in);
        ByteBuffer lengthBuffer = ByteBuffer.allocate(4);
        while (readChannel.read(lengthBuffer) != -1) {
            lengthBuffer.flip();
            int length = lengthBuffer.getInt();
            lengthBuffer.clear();
            byte[] bytes = readBytes(readChannel, length);
            UResponse response = receive(bytes);
            break;
        }
        readChannel.close();
    }

    private static byte[] readBytes(ReadableByteChannel readChannel, int length) throws IOException {
        int readLength = 0;
        ByteBuffer bodyBuffer = ByteBuffer.allocate(length);
        ByteBuffer readBuffer = ByteBuffer.allocate(length - readLength);
        while (readChannel.read(readBuffer) != -1 && readLength < length) {
            readBuffer.flip();
            int remaining = readBuffer.remaining();
            readLength += remaining;
            bodyBuffer.put(readBuffer);
            readBuffer.clear();
            readBuffer.limit(length - readLength);
        }
        return bodyBuffer.array();
    }

    private static UResponse receive(byte[] bytes) {
        UResponse response = new UResponse(bytes);
        System.out.println("receive: " + response.getState());
        return response;
    }

    private static byte[] getOutPutBytes() throws USessionNotSupportException {
        List<String> names = new ArrayList<>();
        for (int i = 0; i < 1000000; i++) {
            names.add("name+i");
        }
        URequest request = new URequest(IHelloWorldService.class.getName(),"say",null,new Object[]{names});
//        System.out.println("send: " + request);
        byte[] bytes = new UPackageKit().pack(request);
        ByteBuf byteBuf = Unpooled.buffer(bytes.length + 4);
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
        byteBuf.asReadOnly();
        System.out.println("length:" + bytes.length);
        return byteBuf.array();
    }
}
