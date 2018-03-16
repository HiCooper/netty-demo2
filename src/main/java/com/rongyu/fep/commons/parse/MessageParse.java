package com.rongyu.fep.commons.parse;

import com.rongyu.fep.models.Message;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Berry_Cooper
 * @date 2018/1/22.
 */
public class MessageParse {

    private static final Logger logger = LoggerFactory.getLogger(MessageParse.class);


    /**
     * 解析报文，返回可操作的消息对象
     *
     * @param ctx
     * @param message 待解析报文（ASCII格式）
     * @return MessageNetty 消息对象
     * @throws Exception 异常
     */
    public static Message parseMessage(Channel ctx, String message) throws Exception {
        //1.解析报文头
        Message messageHeader = parseMessageHeader(message);
        logger.info("serverHeader:" + messageHeader);

        //2.组装已解析消息对象,消息内容，序列化后获取字节数组
        messageHeader.setLength(message.length());
        return messageHeader;
    }

    /**
     * 解析报文头
     *
     * @param message 待解析报文
     * @return 报文头信息对象 messageHeader
     */
    private static Message parseMessageHeader(String message) throws Exception {
        Message message1 = new Message();
        //F01 182 0 000000 1002 20170324135657 105152683980000 12345675 000013 000406 0800000000V70old.wav
        //原报文体
        message1.setBody(message.substring(4, message.length()));

        return message1;
    }

}
