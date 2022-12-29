package com.example.demotest.scal;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

import java.net.InetSocketAddress;
import java.util.List;

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

/*
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