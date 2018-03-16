package com.rongyu.fep.filter;

import com.rongyu.common.utils.ConvertTools;
import com.rongyu.fep.commons.parse.MessageParse;
import com.rongyu.fep.models.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.List;

/**
 * @author Berry_Cooper
 * @date 2018/1/23.
 */
@ChannelHandler.Sharable
public class NettyDecoder extends MessageToMessageDecoder<ByteBuf> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        logger.info("********************* 拆包 *********************");

        //1.将ByteBuf类型msg转为可阅读的ASCII字符
        String message = msg.toString(Charset.defaultCharset());
        String asciiString = ConvertTools.convertHexToASCIIString(message);
        //2.解析报文
        Channel channel = ctx.channel();
        Message messageHeader = MessageParse.parseMessage(channel, asciiString);
        out.add(messageHeader);
    }

}
