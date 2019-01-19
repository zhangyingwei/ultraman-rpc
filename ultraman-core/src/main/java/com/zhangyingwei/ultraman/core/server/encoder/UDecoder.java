package com.zhangyingwei.ultraman.core.server.encoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


@Slf4j
public class UDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf inByte, List<Object> list) throws Exception {
        if (inByte.readableBytes() < 4) {
            return;
        }
        inByte.markReaderIndex();
        int length = inByte.readInt();
        if (length < 0) {
            channelHandlerContext.channel().close();
        } else if (length == 0) {

        } else if (inByte.readableBytes() < length) {
            inByte.resetReaderIndex();
        } else {
            byte[] bytes = new byte[length];
            inByte.readBytes(bytes);
            list.add(bytes);
            log.info("message decode finish. length: {}", length);
        }

    }
}
