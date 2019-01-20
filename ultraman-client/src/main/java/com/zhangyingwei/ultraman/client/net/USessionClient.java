package com.zhangyingwei.ultraman.client.net;

import com.zhangyingwei.ultraman.client.exception.USessionExecuteException;
import com.zhangyingwei.ultraman.common.exceptions.ClassIsNotUSessionException;
import com.zhangyingwei.ultraman.common.exceptions.USessionNotSupportException;
import com.zhangyingwei.ultraman.session.UPackageKit;
import com.zhangyingwei.ultraman.session.URequest;
import com.zhangyingwei.ultraman.session.UResponse;
import lombok.extern.slf4j.Slf4j;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SocketChannel;

@Slf4j
public class USessionClient {
    private SocketPool pool;
    private UPackageKit packageKit;

    public USessionClient(String host,int port) {
        this.pool = new SocketPool(host, port);
        this.packageKit = new UPackageKit();
    }

    public UResponse execute(URequest request) throws USessionExecuteException {
        SocketChannel socketChannel = null;
        try {
            socketChannel = this.pool.getSocket();
            return this.execute(socketChannel, request);
        } catch (IOException | USessionNotSupportException | ClassIsNotUSessionException e) {
            throw new USessionExecuteException(e);
        }finally {
            try {
                this.pool.returnSocket(socketChannel);
            } catch (IOException e) {
                log.error(e.getLocalizedMessage());
            }
        }
    }

    private UResponse execute(SocketChannel socketChannel, URequest request) throws USessionNotSupportException, IOException, ClassIsNotUSessionException {
        socketChannel.write(ByteBuffer.wrap(this.convertToBytes(request)));
        UResponse response = null;
        //read
        InputStream in = socketChannel.socket().getInputStream();
        ReadableByteChannel readChannel = Channels.newChannel(in);
        ByteBuffer lengthBuffer = ByteBuffer.allocate(4);
        try {
            while (readChannel.read(lengthBuffer) != -1) {
                lengthBuffer.flip();
                int length = lengthBuffer.getInt();
                lengthBuffer.clear();
                byte[] bytes = readBytes(readChannel, length);
                response = this.convertToResponse(bytes);
                break;
            }
        }finally {
            readChannel.close();
        }
        return response;
    }

    private byte[] readBytes(ReadableByteChannel readChannel, int length) throws IOException {
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

    private byte[] convertToBytes(URequest request) throws USessionNotSupportException {
        byte[] bytes = this.packageKit.pack(request);
        ByteBuffer outByteBuffer = ByteBuffer.allocate(bytes.length + 4);
        outByteBuffer.putInt(bytes.length);
        outByteBuffer.put(bytes);
        return outByteBuffer.array();
    }

    private UResponse convertToResponse(byte[] bytes) throws IOException, ClassIsNotUSessionException {
        return (UResponse) this.packageKit.unPack(bytes, UResponse.class);
    }
}
