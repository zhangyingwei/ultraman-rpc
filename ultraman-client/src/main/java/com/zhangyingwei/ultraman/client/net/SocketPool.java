package com.zhangyingwei.ultraman.client.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ArrayBlockingQueue;

public class SocketPool {
    private String host;
    private int port;
    private int initSize = 1024;

    private ArrayBlockingQueue<SocketChannel> socketPool = new ArrayBlockingQueue<SocketChannel>(initSize);


    public SocketPool(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public SocketChannel getSocket() throws IOException {
        SocketChannel socketChannel = socketPool.poll();
        if (socketChannel == null || !socketChannel.isConnected()) {
            return this.createOneSocket();
        }
        return socketChannel;
    }

    private SocketChannel createOneSocket() throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress(this.host, this.port));
        return socketChannel;
    }

    public void returnSocket(SocketChannel socketChannel) throws IOException {
        if (socketChannel != null) {
            boolean result = this.socketPool.offer(socketChannel);
            if (!result) {
                this.closeSocketChannel(socketChannel);
            }
        }
    }

    private void closeSocketChannel(SocketChannel socketChannel) throws IOException {
        socketChannel.close();
    }
}
