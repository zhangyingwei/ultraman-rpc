package com.zhangyingwei.ultraman.core.server.handler;

import com.zhangyingwei.ultraman.core.service.ServiceManager;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;


/**
 * @author zhangyw
 * @date: 2019/1/18
 * @desc:
 */
public class URpcChannelHandler extends ChannelInitializer<SocketChannel> {
    private ServiceManager serviceManager;

    public URpcChannelHandler(ServiceManager serviceManager) {
        this.serviceManager = serviceManager;
    }

    @Override
    protected void initChannel(io.netty.channel.socket.SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));
        socketChannel.pipeline().addLast("decoder",new ByteArrayDecoder());
        socketChannel.pipeline().addLast("encoder",new ByteArrayEncoder());
        socketChannel.pipeline().addLast(new SessionHandler(serviceManager));
    }
}