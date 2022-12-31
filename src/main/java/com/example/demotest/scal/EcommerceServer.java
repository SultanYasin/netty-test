package com.example.demotest.scal;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.List;

/*

public class EcommerceServer {

    private final List<InetSocketAddress> serverAddresses;

    public EcommerceServer(List<InetSocketAddress> serverAddresses) {
        this.serverAddresses = serverAddresses;
    }

    public void start() throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new EcommerceServerInitializer(serverAddresses));

            b.bind(8080).sync().channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
*/
import java.net.InetSocketAddress;
import java.util.List;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


import java.net.InetSocketAddress;
import java.util.List;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
public class EcommerceServer {

    private final LoadBalancer loadBalancer;
    private final AnnotationConfigApplicationContext applicationContext;

    public EcommerceServer(LoadBalancer loadBalancer, AnnotationConfigApplicationContext applicationContext) {
        this.loadBalancer = loadBalancer;
        this.applicationContext = applicationContext;
    }

    public void start() throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new EcommerceServerInitializer(loadBalancer, applicationContext));

            b.bind(8080).sync().channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}



/*
public class EcommerceServer {

    private final List<InetSocketAddress> serverAddresses;
    private final AnnotationConfigApplicationContext applicationContext;


    public EcommerceServer(List<InetSocketAddress> serverAddresses, AnnotationConfigApplicationContext applicationContext) {
        this.serverAddresses = serverAddresses;
        this.applicationContext = applicationContext;
    }

    public void start() throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            LoadBalancer loadBalancer;
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new EcommerceServerInitializer(loadBalancer, applicationContext));

            b.bind(8080).sync().channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}




public class EcommerceServer {

    private final List<InetSocketAddress> serverAddresses;
    private final AnnotationConfigApplicationContext applicationContext;

    public EcommerceServer(List<InetSocketAddress> serverAddresses, AnnotationConfigApplicationContext applicationContext) {
        this.serverAddresses = serverAddresses;
        this.applicationContext = applicationContext;
    }

    public void start() throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new EcommerceServerInitializer(serverAddresses, applicationContext));

            b.bind(8080).sync().channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
*/
