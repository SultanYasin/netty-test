package com.example.demotest.scal;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.channel.Channel;

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

    // Other methods...






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
}


/*

public class LoadBalancer {

    public static void main(String[] args) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.option(ChannelOption.SO_BACKLOG, 1024);
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler( new LoadBalancerInitializer(serverAddress));

            Channel ch = b.bind(8080).sync().channel();
            System.out.println("Load balancer started at port 8080.");
            ch.closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
*/
