package com.zhangyingwei.ultraman.core.server.handler;

import com.zhangyingwei.ultraman.common.exceptions.NullResponseExceotion;
import com.zhangyingwei.ultraman.common.exceptions.USessionNotSupportException;
import com.zhangyingwei.ultraman.core.executor.MsgExecutor;
import com.zhangyingwei.ultraman.core.server.filter.BusFilter;
import com.zhangyingwei.ultraman.core.service.ServiceManager;
import com.zhangyingwei.ultraman.session.UPackageKit;
import com.zhangyingwei.ultraman.session.URequest;
import com.zhangyingwei.ultraman.session.UResponse;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import java.net.InetSocketAddress;

@Slf4j
public class ByteArrayHandler {

    private MsgExecutor executor;
    private final BusFilter busFilter;
    private UPackageKit packageKit = new UPackageKit();

    public ByteArrayHandler(ServiceManager serviceManager, BusFilter busFilter) {
        this.busFilter = busFilter;
        this.executor = new MsgExecutor(serviceManager);
    }

    public byte[] doHandle(ChannelHandlerContext ctx, byte[] msg) throws USessionNotSupportException {
        InetSocketAddress remoteAddress = (InetSocketAddress) ctx.channel().remoteAddress();
        String ip = remoteAddress.getHostName();
        URequest request = null;
        UResponse response = null;
        if (busFilter.accept(ctx,msg)) {
            try {
                request = (URequest) packageKit.unPack(msg, URequest.class);
                log.info("request from: {}\tcall {}->{}", ip, request.getServiceClassName(),request.getMethodName());
                response = this.executor.execure(request);
                if (response == null) {
                    throw new NullResponseExceotion("response is null");
                }
            } catch (Exception e) {
                response = new UResponse(e.getLocalizedMessage(), UResponse.State.ERROR);
                log.error(e.getLocalizedMessage(), e);
            }
        } else {
            response = new UResponse("ChouBuYaoLianDe", UResponse.State.PERMISSION_DENIED);
        }
        return packageKit.pack(response);
    }
}
