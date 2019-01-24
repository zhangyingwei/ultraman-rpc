package com.zhangyingwei.ultraman.core.server;

import com.zhangyingwei.ultraman.common.banner.BannerPrinter;
import com.zhangyingwei.ultraman.core.server.filter.AddressFilter;
import com.zhangyingwei.ultraman.core.server.filter.BusFilter;
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

import java.util.Optional;
import java.util.concurrent.ThreadFactory;

/**
 * @author zhangyw
 * @date: 2019/1/17
 * @desc:
 */
@Slf4j
public class UltramanRpcServer {
    private ThreadLocal<Integer> bossId = new ThreadLocal<Integer>();
    private ThreadLocal<Integer> workerId = new ThreadLocal<Integer>();
    private EventLoopGroup bossGroup = new NioEventLoopGroup(10, runnable -> {
        Thread thread = new Thread(runnable);
        Integer id = Optional.ofNullable(bossId.get()).orElse(0);
        thread.setName("boss-" + id);
        bossId.set(id++);
        return thread;
    });
    private EventLoopGroup workerGroup = new NioEventLoopGroup(10, runnable -> {
        Thread thread = new Thread(runnable);
        Integer id = Optional.ofNullable(workerId.get()).orElse(0);
        thread.setName("worker-" + id);
        bossId.set(id++);
        return thread;
    });
    private ServerBootstrap bootstrap = new ServerBootstrap();

    private ServiceManager serviceManager;
    private BusFilter busFilter = new BusFilter();

    public UltramanRpcServer() {
        this.serviceManager = new ServiceManager();
        BannerPrinter.print();
    }

    public UltramanRpcServer authorize(String ip) {
        ((AddressFilter)this.busFilter.get(BusFilter.Type.ADDRESS)).authorize(ip);
        return this;
    }

    public UltramanRpcServer bindService(String serviceName,Object service) {
        this.serviceManager.bindService(serviceName,service);
        return this;
    }

    public void bind(int port) throws InterruptedException {
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childHandler(new URpcChannelHandler(serviceManager,busFilter));

        ChannelFuture future = bootstrap.bind(port).sync();
        log.info("start at {}...", port);
        future.channel().closeFuture().sync();
    }
}
