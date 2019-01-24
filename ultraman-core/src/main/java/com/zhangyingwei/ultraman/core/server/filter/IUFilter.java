package com.zhangyingwei.ultraman.core.server.filter;

import io.netty.channel.ChannelHandlerContext;

/**
 * @author zhangyw
 * @date: 2019/1/18
 * @desc:
 */
public interface IUFilter {
    boolean accept(ChannelHandlerContext ctx, Object msg);
}
