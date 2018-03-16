package com.rongyu.fep.server;

import com.rongyu.common.utils.ConvertTools;
import com.rongyu.common.utils.JsonUtils;
import com.rongyu.fep.models.Message;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * SERVER channel handler
 *
 * @author Berry_Cooper
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<Message> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * A thread-safe Set  Using ChannelGroup, you can categorize Channels into a meaningful group.
     * A closed Channel is automatically removed from the collection,
     */
    private static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * Channel added
     *
     * @param ctx
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        Channel incoming = ctx.channel();
        System.out.println("客户端加入信息：" + ctx);
        incoming.writeAndFlush("connect server success!");
        channels.add(ctx.channel());
    }

    /**
     * Channel removed
     *
     * @param ctx
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        Channel incoming = ctx.channel();
        this.logger.info(incoming.remoteAddress() + " disconnect from server success!");
    }

    /**
     * receive message from client then deal with that
     *
     * @param ctx
     * @param message
     */
    @Override
    protected void messageReceived(ChannelHandlerContext ctx, Message message) throws Exception {
        this.logger.info("messageInfo \r\nServerHandler:" + this + " \r\nChannel:" + ctx + " \r\nmessage:" + message);
        //处理解码后的报文，并应答客户端
        Channel incoming = ctx.channel();
        String result = JsonUtils.toJson(message);
        System.out.println(result);
        incoming.writeAndFlush(ConvertTools.bytesToHexString(result != null ? result.getBytes() : new byte[0]));
    }

    /**
     * Channel active
     *
     * @param ctx
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        Channel incoming = ctx.channel();
        System.out.println("客户端在线信息：" + ctx);
        this.logger.info(incoming.remoteAddress() + "--online");
    }

    /**
     * Channel inactive
     *
     * @param ctx
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        Channel incoming = ctx.channel();
        this.logger.info(incoming.remoteAddress() + "--offline");
    }

    /**
     * connect exception
     *
     * @param ctx
     * @param cause
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        Channel incoming = ctx.channel();
        this.logger.info(incoming.remoteAddress() + "--abnormal");
        // close connect when exception happen
        cause.printStackTrace();
        ctx.close();
    }
}