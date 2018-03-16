package com.rongyu.fep.server;


import com.rongyu.fep.filter.NettyEncoder;
import com.rongyu.fep.filter.NettyLengthFieldDecoder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;

/**
 * Server ChannelInitializer
 *
 * @author Berry_Cooper
 */
public class NettyServerInitializer extends ChannelInitializer<SocketChannel> {

    private static final int LENGTH_FIELD_LENGTH = 0;
    private static final int LENGTH_FIELD_OFFSET = 0;
    private static final int LENGTH_ADJUSTMENT = 0;
    private static final int INITIAL_BYTES_TO_STRIP = 0;

    @Override
    public void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("decoder", new NettyLengthFieldDecoder(Integer.MAX_VALUE, LENGTH_FIELD_LENGTH, LENGTH_FIELD_OFFSET, LENGTH_ADJUSTMENT, INITIAL_BYTES_TO_STRIP, false));
        pipeline.addLast("encoder", new NettyEncoder());
        pipeline.addLast("handler", new NettyServerHandler());

        System.out.println(ch.remoteAddress() + "--connected");
    }

}
