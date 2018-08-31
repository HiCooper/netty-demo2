package com.berry.fep.models;

import io.netty.channel.Channel;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Berry_Cooper
 * @date 2018/2/1.
 */
@Getter
@Setter
public class Session {

    /**
     * session-id，值为channel的id
     */
    private String id;

    /**
     * 来源渠道-每次请求唯一id信息
     */
    private Channel channel;
    /**
     * 消息正文
     */
    private String body;
    /**
     * 是否第一次读取 0-第一次，1-不是第一次
     */
    private int tag = 0;

    /**
     * 正文长度
     */
    private int length = 0;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
