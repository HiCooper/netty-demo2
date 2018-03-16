package com.rongyu.fep.server;

import com.rongyu.fep.config.GlobalProperties;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Netty-Server
 * <p>
 * netty服务器启动类
 *
 * @author Berry_Cooper
 */

@Service
public class NettyServer {

    @Autowired
    private GlobalProperties globalProperties;

    public void run() throws Exception {

        //服务器端口
        int port = this.globalProperties.getPort();
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    //设置新的Channel接收进来的连接方式-NIO
                    .channel(NioServerSocketChannel.class)
                    //配置具体的数据处理方式
                    .childHandler(new NettyServerInitializer())
                    //设置TCP缓冲区
                    .option(ChannelOption.SO_BACKLOG, 128)
                    //设置发送数据缓冲大小
                    .option(ChannelOption.SO_SNDBUF, 32 * 1024)
                    //设置接受数据缓冲大小
                    .option(ChannelOption.SO_RCVBUF, 32 * 1024)
                    //设置接受数据缓冲大小
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            System.out.println("server start on port：" + port);
            //bind server port and begin to collect come in connect
            ChannelFuture f = b.bind(port).sync();
            //wait for server socket close
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
            System.out.println("server closed");
        }
    }

}