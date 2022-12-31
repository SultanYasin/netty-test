package com.example.demotest.scal;


import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.List;

public class EcommerceServerInitializer extends ChannelInitializer<SocketChannel> {

    private final LoadBalancer loadBalancer;
    private final AnnotationConfigApplicationContext applicationContext;

    public EcommerceServerInitializer(LoadBalancer loadBalancer, AnnotationConfigApplicationContext applicationContext) {
        this.loadBalancer = loadBalancer;
        this.applicationContext = applicationContext;
    }

    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new HttpRequestDecoder());
        pipeline.addLast(new HttpResponseEncoder());
        pipeline.addLast(new HttpObjectAggregator(65536));
        pipeline.addLast(new SpringHttpServerHandler(applicationContext));
    }
}

/*
public class EcommerceServerInitializer extends ChannelInitializer<SocketChannel> {

    private final List<InetSocketAddress> servers;

    public EcommerceServerInitializer(List<InetSocketAddress> servers) {
        this.servers = servers;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline p = ch.pipeline();
        p.addLast(new HttpServerCodec());
        p.addLast(new HttpObjectAggregator(65536));
        p.addLast(new LoadBalancerHandler(servers));
    }
}


public class EcommerceServerInitializer extends ChannelInitializer<SocketChannel> {

    private final AnnotationConfigApplicationContext applicationContext;

    public EcommerceServerInitializer(AnnotationConfigApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void initChannel(SocketChannel ch) {
        ChannelPipeline p = ch.pipeline();
        p.addLast(new HttpServerCodec());
        p.addLast(new HttpObjectAggregator(65536));
        p.addLast(new SpringHttpServerHandler(applicationContext));
    }
}*/