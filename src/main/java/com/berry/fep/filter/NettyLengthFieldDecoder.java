package com.berry.fep.filter;

import com.berry.fep.commons.utils.ConvertTools;
import com.berry.fep.models.Message;
import com.berry.fep.models.Session;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;

/**
 * @author berry
 */
public class NettyLengthFieldDecoder extends LengthFieldBasedFrameDecoder {

    private static HashMap<String, Session> hashMap = new HashMap<>();

    private static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 判断传送客户端传送过来的数据是否按照协议传输，头部信息的大小应该是  2
     */
    private static final int HEADER_SIZE = 2;

    /**
     * @param maxFrameLength      解码时，处理每个帧数据的最大长度 Integer.MAX_VALUE
     * @param lengthFieldOffset   该帧数据中，存放该帧数据的长度的数据的起始位置 0
     * @param lengthFieldLength   记录该帧数据长度的字段本身的长度 2
     * @param lengthAdjustment    修改帧数据长度字段中定义的值，可以为负数
     * @param initialBytesToStrip 解析的时候需要跳过的字节数
     * @param failFast            为true，当frame长度超过maxFrameLength时立即报TooLongFrameException异常，为false，读取完整个帧再报异常
     */
    public NettyLengthFieldDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength,
                                   int lengthAdjustment, int initialBytesToStrip, boolean failFast) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength,
                lengthAdjustment, initialBytesToStrip, failFast);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {

        // 截取头部 ，buffer将会被修改（减少了读取的头部信息）
        ByteBuf head = msg.readBytes(HEADER_SIZE);
        // 设置读取头部的字节长度
        byte[] bytes = new byte[head.readableBytes()];
        // 将 head 赋值 给 bytes
        head.readBytes(bytes);
        System.out.println(Arrays.toString(bytes));
        //计算报文正文字节长度
        int length = ((((bytes[0]) & 0xff) << 8) + (bytes[1] & 0xff)) / 2;
        System.out.println("length:"+length);


//        Session session = hashMap.get(String.valueOf(ctx.channel().id()));
//        if (session == null) {
//            session = new Session();
//            session.setId(String.valueOf(ctx.channel().id()));
//            session.setChannel(ctx.channel());
//        }
//
//        int length = session.getLength();
//        if (session.getTag() == 0) {
//            //第一次读取数据
//            if (msg.readableBytes() < HEADER_SIZE) {
//                return null;
//            }
//            // 截取头部 ，buffer将会被修改（减少了读取的头部信息）
//            ByteBuf head = msg.readBytes(HEADER_SIZE);
//            // 设置读取头部的字节长度
//            byte[] bytes = new byte[head.readableBytes()];
//            // 将 head 赋值 给 bytes
//            head.readBytes(bytes);
//            //计算报文正文字节长度
//            length = ((((bytes[0]) & 0xff) << 8) + (bytes[1] & 0xff)) / 2;
//            System.out.println("length:"+length);
//            session.setLength(length);
//        }
//
//        if (session.getBody() == null || session.getBody().length() < length) {
//            //没读完
//            //本次可读取的字节长度
//            int thisBuf = msg.readableBytes();
//            ByteBuf buf = msg.readBytes(thisBuf);
//            byte[] b = new byte[buf.readableBytes()];
//            buf.readBytes(b);
//            String thisStr = new String(b, Consts.UTF_8);
//
//            session.setBody(StringUtils.trimToEmpty(session.getBody()) + thisStr);
//            hashMap.putIfAbsent(session.getId(), session);
//            channels.add(ctx.channel());
//
//            //标记为已读取过数据
//            session.setTag(1);
//
//            if (session.getBody().length() == length) {
//                // 结束decode
//                Message message = new Message();
//                message.setLength(length);
//                message.setBody(session.getBody());
//                return message;
//            }
//        }
        return new Message();
    }

}

