package com.example.demotest.scal;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LoadBalancer {

    private final List<InetSocketAddress> serverAddresses;
    private final EventLoopGroup group;
    private final Bootstrap bootstrap;
    private final Map<Channel, InetSocketAddress> channels;

    public LoadBalancer(List<InetSocketAddress> serverAddresses) {
        this.serverAddresses = serverAddresses;
        this.group = new NioEventLoopGroup();
        this.bootstrap = new Bootstrap();
        this.channels = new ConcurrentHashMap<>();

        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new LoadBalancerInitializer(this));
    }

    public void start() throws InterruptedException {
        for (InetSocketAddress serverAddress : serverAddresses) {
            bootstrap.connect(serverAddress).sync();
        }
    }

    public void stop() {
        group.shutdownGracefully();
    }

    public void addChannel(Channel channel, InetSocketAddress serverAddress) {
        channels.put(channel, serverAddress);
    }

    public void removeChannel(Channel channel) {
        channels.remove(channel);
    }

    public InetSocketAddress getServer(Channel channel) {
        return channels.get(channel);
    }

    public List<InetSocketAddress> getServerAddresses() {
        return serverAddresses;
    }



}

/*
public class LoadBalancer {

    private final List<InetSocketAddress> serverAddresses;
    private final EventLoopGroup group;
    private final Bootstrap bootstrap;
    private final Map<Channel, InetSocketAddress> channels;

    public LoadBalancer(List<InetSocketAddress> serverAddresses) {
        this.serverAddresses = serverAddresses;
        this.group = new NioEventLoopGroup();
        this.bootstrap = new Bootstrap();
        this.channels = new ConcurrentHashMap<>();

        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new LoadBalancerInitializer(this));
    }

    public void start() throws InterruptedException {
        for (InetSocketAddress serverAddress : serverAddresses) {
            bootstrap.connect(serverAddress).sync();
        }
    }

    public void stop() {
        group.shutdownGracefully();
    }

    public void addChannel(Channel channel, InetSocketAddress serverAddress) {
        channels.put(channel, serverAddress);
    }

    public void removeChannel(Channel channel) {
        channels.remove(channel);
    }

    public InetSocketAddress getServer(Channel channel) {
        return channels.get(channel);
    }

    public List<InetSocketAddress> getServerAddresses() {
        return serverAddresses;
    }


}




public class LoadBalancer {

    private final List<InetSocketAddress> serverAddresses;
    private final EventLoopGroup group;
    private final Bootstrap bootstrap;
    private final Map<Channel, InetSocketAddress> channels;

    public LoadBalancer(List<InetSocketAddress> serverAddresses) {
        this.serverAddresses = serverAddresses;
        this.group = new NioEventLoopGroup();
        this.bootstrap = new Bootstrap();
        this.channels = new ConcurrentHashMap<>();

        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new LoadBalancerInitializer(this));
    }


    public void start() throws InterruptedException {
        for (InetSocketAddress serverAddress : serverAddresses) {
            bootstrap.connect(serverAddress).sync();
        }
    }


    public void stop() {
        group.shutdownGracefully();
    }

    public void addChannel(Channel channel, InetSocketAddress serverAddress) {
        channels.put(channel, serverAddress);
    }

    public void removeChannel(Channel channel) {
        channels.remove(channel);
    }

    public InetSocketAddress getServer(Channel channel) {
        return channels.get(channel);
    }


}*/

