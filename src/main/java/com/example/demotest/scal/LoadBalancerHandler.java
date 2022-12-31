package com.example.demotest.scal;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.DispatcherServlet;

import java.net.InetSocketAddress;
public class LoadBalancerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private final LoadBalancer loadBalancer;

    public LoadBalancerHandler(LoadBalancer loadBalancer) {
        this.loadBalancer = loadBalancer;
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        // Get the server to forward the request to
        InetSocketAddress serverAddress = loadBalancer.getServer(ctx.channel());

        // Create a new client channel and forward the request to the server
        Bootstrap clientBootstrap = new Bootstrap();
        clientBootstrap.group(ctx.channel().eventLoop())
                .channel(NioSocketChannel.class)
                .handler(new SimpleChannelInboundHandler<FullHttpResponse>() {
                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, FullHttpResponse response) throws Exception {
                        // Send the response back to the client
                        ctx.writeAndFlush(response);
                    }
                });

        ChannelFuture future = clientBootstrap.connect(serverAddress);
        future.addListener((ChannelFutureListener) channelFuture -> {
            if (channelFuture.isSuccess()) {
                // Forward the request to the server
                channelFuture.channel().writeAndFlush(request);
            } else {
                // If the connection to the server failed, close the client channel
                ctx.close();
            }
        });
    }
}


/*
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.FullHttpRequest;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;


public class LoadBalancerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private final List<InetSocketAddress> servers;
    private final EventLoopGroup group;
    private final Bootstrap bootstrap;
    private final Map<Channel, InetSocketAddress> channels;

    public LoadBalancerHandler(List<InetSocketAddress> servers) {
        this.servers = servers;
        this.group = new NioEventLoopGroup();
        this.bootstrap = new Bootstrap();
        this.channels = new ConcurrentHashMap<>();

        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new HttpClientInitializer(servers));
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        InetSocketAddress serverAddress = channels.get(ctx.channel());

        if (serverAddress == null) {
            serverAddress = getNextServer();
            channels.put(ctx.channel(), serverAddress);
        }

        bootstrap.connect(serverAddress).addListener((ChannelFutureListener) future -> {
            if (future.isSuccess()) {
                future.channel().writeAndFlush(request);
            } else {
                ctx.channel().close();
            }
        });
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        channels.remove(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.channel().close();
    }

    private InetSocketAddress getNextServer() {
        return servers.get(new Random().nextInt(servers.size()));
    }
}

*/
