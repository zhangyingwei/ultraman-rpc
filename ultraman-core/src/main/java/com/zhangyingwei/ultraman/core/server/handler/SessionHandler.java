package com.zhangyingwei.ultraman.core.server.handler;

import com.zhangyingwei.ultraman.common.exceptions.NullResponseExceotion;
import com.zhangyingwei.ultraman.core.executor.MsgExecutor;
import com.zhangyingwei.ultraman.core.server.filter.AddressFilter;
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
    private MsgExecutor executor;
    private UPackageKit packageKit = new UPackageKit();
    private AddressFilter addressFilter = new AddressFilter();

    public SessionHandler(ServiceManager serviceManager) {
        this.executor = new MsgExecutor(serviceManager);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        InetSocketAddress remoteAddress = (InetSocketAddress) ctx.channel().remoteAddress();
        String ip = remoteAddress.getHostName();
        log.info("request from: {}", ip);
        URequest request = null;
        UResponse response = null;
        if (addressFilter.accept(ip)) {
            try {
                request = (URequest) packageKit.unPack((byte[]) msg, URequest.class);
                response = this.executor.execure(request);
                if (response == null) {
                    throw new NullResponseExceotion("response is null");
                }
            } catch (Exception e) {
                response = new UResponse(e.getLocalizedMessage(), UResponse.State.ERROR);
                log.error(e.getLocalizedMessage());
            }
        } else {
            response = new UResponse("臭不要脸的", UResponse.State.PERMISSION_DENIED);
        }
        ctx.writeAndFlush(packageKit.pack(response));
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("add one...");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
