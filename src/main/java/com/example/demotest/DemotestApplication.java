package com.example.demotest;

import com.example.demotest.scal.EcommerceServer;
import com.example.demotest.scal.LoadBalancer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class DemotestApplication implements CommandLineRunner {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(DemotestApplication.class, args);
        // Create a list of server addresses
        List<InetSocketAddress> serverAddresses = Arrays.asList(
                new InetSocketAddress("localhost", 8080),
                new InetSocketAddress("localhost", 8081),
                new InetSocketAddress("localhost", 8082)
        );


// Create an instance of LoadBalancer with the server addresses
        LoadBalancer loadBalancer = new LoadBalancer(serverAddresses);
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

// Start the LoadBalancer
        loadBalancer.start();

// Create an instance of EcommerceServer with the LoadBalancer and the AnnotationConfigApplicationContext
        EcommerceServer ecommerceServer = new EcommerceServer(loadBalancer, applicationContext);

// Start the EcommerceServer
        ecommerceServer.start();




    }

    @Override
    public void run(String... args) throws Exception {

    }
}




























/*
    @Override
    public void run(String... args) throws Exception {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(EcommerceConfig.class);
        applicationContext.refresh();

        int port = 8080;
        EcommerceServer server = new EcommerceServer(port, applicationContext);
        server.start();
    }
}*/

