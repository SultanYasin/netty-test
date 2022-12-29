package com.example.demotest.scal;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;

public class HttpClientHandler extends SimpleChannelInboundHandler<FullHttpResponse> {

    private final FullHttpRequest request;

    public HttpClientHandler(FullHttpRequest request) {
        this.request = request;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        // Send the request to the server
        ctx.writeAndFlush(request);
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, FullHttpResponse response) throws Exception {
        // Forward the response back to the client
        ctx.channel().writeAndFlush(response);
        ctx.channel().close();
    }
}
