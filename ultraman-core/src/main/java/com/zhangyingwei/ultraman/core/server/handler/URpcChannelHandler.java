package com.zhangyingwei.ultraman.core.server.handler;

import com.zhangyingwei.ultraman.core.server.encoder.UDecoder;
import com.zhangyingwei.ultraman.core.server.encoder.UEncoder;
import com.zhangyingwei.ultraman.core.server.filter.AddressFilter;
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
    private AddressFilter addressFilter;

    public URpcChannelHandler(ServiceManager serviceManager,AddressFilter addressFilter) {
        this.serviceManager = serviceManager;
        this.addressFilter = addressFilter;
    }

    @Override
    protected void initChannel(io.netty.channel.socket.SocketChannel socketChannel) {
        socketChannel.pipeline().addLast("decoder", new UDecoder());
        socketChannel.pipeline().addLast("encoder", new UEncoder());
        socketChannel.pipeline().addLast(new SessionHandler(serviceManager, addressFilter));
    }
}