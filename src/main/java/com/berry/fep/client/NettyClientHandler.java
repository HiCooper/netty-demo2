package com.berry.fep.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Berry_Cooper.
 * @date 2018-12-06 13:17
 * fileName：SimpleChatClientHandler
 * Use：
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<String> {


    @Override
    protected void messageReceived(ChannelHandlerContext ctx, String msg) {
        System.out.println(msg);
    }
}