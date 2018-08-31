package com.berry.fep.filter;

import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.List;

/**
 * @author Berry_Cooper
 * @date 2018/1/23.
 */
@ChannelHandler.Sharable
public class NettyEncoder extends MessageToMessageEncoder<String> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    protected void encode(ChannelHandlerContext ctx, String msg, List<Object> out) throws Exception {
        logger.info("********************* 封包 *********************");
        if (msg.length() == 0) {
            return;
        }
        out.add(ByteBufUtil.encodeString(ctx.alloc(), CharBuffer.wrap(msg), Charset.defaultCharset()));
    }

}
