package com.rongyu.fep.models;

import lombok.Getter;
import lombok.Setter;

/**
 * 报文实体类头部
 *
 * @author Berry_Cooper
 */
@Getter
@Setter
public class Message {

    /**
     * 主体信息的长度
     */
    private int length;

    /**
     * 主体信息
     */
    private String body;

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

}
