package com.zhangyingwei.ultraman.core.server.encoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class UEncoder extends MessageToByteEncoder {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object msg, ByteBuf outByte) throws Exception {
        byte[] bytes = (byte[]) msg;
        int length = bytes.length;
        outByte.writeInt(length);
        outByte.writeBytes(bytes);
        log.info("message encode finish. length: {}", length);
    }
}
