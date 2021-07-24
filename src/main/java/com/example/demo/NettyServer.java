package com.example.demo;

import com.example.demo.handler.WebSocketServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.util.concurrent.CompletableFuture;


public class NettyServer implements InitializingBean, DisposableBean {
    private final static Log LOG = LogFactory.getLog(NettyServer.class);
    private final Integer port;
    private Channel channel;

    public NettyServer(Integer port) {
        this.port = port;
    }

    @Override
    public void afterPropertiesSet() {

        CompletableFuture.runAsync(() -> {
            try {
                start();
            } catch (Exception e) {
                LOG.error("ERROR", e);
            }
        });
    }

    public void start() throws Exception {
        LOG.info("NETTY START PORT " + this.port);
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(1);
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new WebSocketServerInitializer());

            bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
            bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);

            channel = bootstrap.bind(port).sync().channel();
            channel.closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    @Override
    public void destroy() {
        channel.close();
    }
}
