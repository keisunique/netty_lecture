package com.ke.netty.first;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class TestServer {


    public static void main(String[] args) throws Exception{
        // 事件循环组
        EventLoopGroup bossGroup = new NioEventLoopGroup();// 接受链接，发送给worker
        EventLoopGroup workerGroup = new NioEventLoopGroup();// 完成链接处理

        try {
            // 用于启动服务端的类，对启动做了基本的封装
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new TestServerInitializer());// 子处理器

            // 绑定端口
            ChannelFuture channelFuture = serverBootstrap.bind(8080).sync();

            // 关闭监听
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
