package com.zhangyingwei.ultraman.client;

import com.zhangyingwei.ultraman.client.net.USessionClient;
import com.zhangyingwei.ultraman.client.service.ServiceFactory;
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
    private ServiceFactory serviceFactory;
    private USessionClient sessionClient;

    public UltramanRpcClient(String host, int port) {
        this.sessionClient = new USessionClient(host, port);
        this.serviceFactory = new ServiceFactory();
    }

    public <T>T getBean(Class clazz) {
        return this.serviceFactory.bulidBean(clazz,this.sessionClient);
    }

}
