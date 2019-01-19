package com.zhangyingwei.ultraman.core.server;

import com.zhangyingwei.ultraman.core.server.handler.URpcChannelHandler;
import com.zhangyingwei.ultraman.core.service.ServiceManager;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.concurrent.EventExecutorGroup;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangyw
 * @date: 2019/1/17
 * @desc:
 */
@Slf4j
public class UltramanRpcServer {
    private EventLoopGroup bossGroup = new NioEventLoopGroup();
    private EventLoopGroup workerGroup = new NioEventLoopGroup();
    private ServerBootstrap bootstrap = new ServerBootstrap();

    private ServiceManager serviceManager;

    public UltramanRpcServer(ServiceManager serviceManager) {
        this.serviceManager = serviceManager;
    }

    public void bind(int port) throws InterruptedException {
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childHandler(new URpcChannelHandler(serviceManager));

        ChannelFuture future = bootstrap.bind(8000).sync();
        log.info("start at 8000...");
        future.channel().closeFuture().sync();
    }
}
