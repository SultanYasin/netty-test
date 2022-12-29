package com.example.demotest.scal;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class SpringHttpServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private final AnnotationConfigApplicationContext applicationContext;
    private final DispatcherServlet dispatcherServlet;

    public SpringHttpServerHandler(AnnotationConfigApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        this.dispatcherServlet = new DispatcherServlet((WebApplicationContext) applicationContext);
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        // Create a Spring MVC DispatcherServlet and forward the request to it
        HttpServletRequest servletRequest = new NettyHttpServletRequest(request, ctx.channel().eventLoop());
        HttpServletResponse servletResponse = new NettyHttpServletResponse((FullHttpResponse) request, ctx.channel().eventLoop());
        dispatcherServlet.service(servletRequest, servletResponse);
    }
}
