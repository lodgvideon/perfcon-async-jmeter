package com.example.demo.config;

import com.example.demo.ws.NettyServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class NettyConfig {

    @Value("${netty.port}")
    private String netty_port;

    @Bean
    public NettyServer nettyServer() {
        return new NettyServer(Integer.valueOf(netty_port));
    }

    @Bean
    public ExecutorService commonThreadPoolExecutor(){
        return Executors.newFixedThreadPool(16);
    }
}

