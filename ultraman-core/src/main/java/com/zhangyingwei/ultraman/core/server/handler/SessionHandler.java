package com.zhangyingwei.ultraman.core.server.handler;

import com.zhangyingwei.ultraman.common.exceptions.NullResponseExceotion;
import com.zhangyingwei.ultraman.core.executor.MsgExecutor;
import com.zhangyingwei.ultraman.core.server.filter.AddressFilter;
import com.zhangyingwei.ultraman.core.server.filter.BusFilter;
import com.zhangyingwei.ultraman.core.service.ServiceManager;
import com.zhangyingwei.ultraman.session.UPackageKit;
import com.zhangyingwei.ultraman.session.URequest;
import com.zhangyingwei.ultraman.session.UResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;

/**
 * @author zhangyw
 * @date: 2019/1/18
 * @desc:
 */
@Slf4j
public class SessionHandler extends ChannelInboundHandlerAdapter {
    private UPackageKit packageKit = new UPackageKit();
    private ByteArrayHandler byteArrayHandler;

    public SessionHandler(ServiceManager serviceManager,BusFilter busFilter) {
        this.byteArrayHandler = new ByteArrayHandler(serviceManager,busFilter);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        byte[] response = this.byteArrayHandler.doHandle(ctx, (byte[]) msg);
        ctx.writeAndFlush(response);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
//        System.out.println("add one...");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        ctx.close();
        log.info("channel {}  from {} close ", ctx.channel(), ctx.channel().remoteAddress());
    }
}
