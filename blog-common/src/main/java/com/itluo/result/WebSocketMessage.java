package com.itluo.result;

import com.itluo.enumeration.MessageType;
import lombok.Data;

/**
 * WebSocket发送消息格式
 * @author Administrator
 */
@Data
public class WebSocketMessage {
    /**
     * 有助于序列化兼容性
     */
    private static final long serialVersionUID = 1L;


    private MessageType type;

    /**
     * 作用于发送私信给这个id  如果是私信的情况下
     */
    private Long id;

    /**
     * 对于心跳包，此字段可简化为空字符串或者其他简单标记
     */
    private String message;

    /**
     * 对于心跳包而言，可以考虑增加如下字段：
     * 时间戳，用于判断心跳是否及时响应
     */
    private Long timestamp;

    /**
     * 序列号，用于确认心跳请求和响应的一致性
     */
    private Long sequence;
}
