package com.ke.netty.first;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;


/**
 * 自定义处理去
 */
public class TestHttpServerHandle extends SimpleChannelInboundHandler<HttpObject> {

    /**
     * 该方法读取客服端的请求并返回响应
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception{
        if(msg instanceof HttpRequest){
            HttpRequest httpRequest = (HttpRequest)msg;
            System.out.println("请求方法名：" + httpRequest.method().name() + " 路径：" + httpRequest.uri());


            ByteBuf content = Unpooled.copiedBuffer("Hello world", CharsetUtil.UTF_8);
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
            // 响应头
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());
            ctx.writeAndFlush(response);
        }
    }

}
