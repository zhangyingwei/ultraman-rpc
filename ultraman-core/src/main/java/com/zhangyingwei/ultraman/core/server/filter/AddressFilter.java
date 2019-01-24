package com.zhangyingwei.ultraman.core.server.filter;

import io.netty.channel.ChannelHandlerContext;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangyw
 * @date: 2019/1/18
 * @desc:
 */
public class AddressFilter implements IUFilter {
    private List<String> ipList = new ArrayList<String>();

    public AddressFilter authorize(String ip) {
        this.ipList.add(ip);
        return this;
    }

    @Override
    public boolean accept(ChannelHandlerContext ctx, Object msg) {
        InetSocketAddress remoteAddress = (InetSocketAddress) ctx.channel().remoteAddress();
        String ip = remoteAddress.getHostName();
        return ipList.contains(ip);
    }

}
