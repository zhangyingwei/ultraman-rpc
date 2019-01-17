package com.zhangyingwei.ultraman.core.server;

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

/**
 * @author zhangyw
 * @date: 2019/1/17
 * @desc:
 */
public class UltramanRpcServer {
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childHandler(new ChildChannelHandler());

        ChannelFuture future = bootstrap.bind(8000).sync();
        System.out.println("start at 8000...");
        future.channel().closeFuture().sync();

        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
        System.out.println("stop...");
    }

    private static class ChildChannelHandler extends ChannelInitializer<SocketChannel> {
        protected void initChannel(SocketChannel socketChannel) throws Exception {
            socketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));
            socketChannel.pipeline().addLast("decoder",new StringDecoder());
            socketChannel.pipeline().addLast("encoder",new StringEncoder());
            socketChannel.pipeline().addLast(new SessionHandler());
        }

        private class SessionHandler extends ChannelInboundHandlerAdapter {
            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                String body = (String) msg;
                System.out.println(msg);
                ctx.writeAndFlush("hello i am server" + System.getProperty("line.separator") + "\\n");
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
    }
}
