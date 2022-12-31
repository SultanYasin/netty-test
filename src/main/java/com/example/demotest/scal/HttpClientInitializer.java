package com.example.demotest.scal;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;

import java.net.InetSocketAddress;
import java.util.List;

public class HttpClientInitializer extends ChannelInitializer<SocketChannel> {

    private final LoadBalancer loadBalancer;

    public HttpClientInitializer(LoadBalancer loadBalancer) {
        this.loadBalancer = loadBalancer;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline p = ch.pipeline();
        p.addLast(new HttpClientCodec());
        p.addLast(new HttpObjectAggregator(65536));
        p.addLast(new LoadBalancerHandler(loadBalancer));
    }
}


/*
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;

import java.net.InetSocketAddress;
import java.util.List;

public class HttpClientInitializer extends ChannelInitializer<SocketChannel> {

    private final List<InetSocketAddress> servers;

    public HttpClientInitializer(List<InetSocketAddress> servers) {
        this.servers = servers;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline p = ch.pipeline();
        p.addLast(new HttpClientCodec());
        p.addLast(new HttpObjectAggregator(65536));
        p.addLast(new LoadBalancerHandler(servers));
    }
}*/
