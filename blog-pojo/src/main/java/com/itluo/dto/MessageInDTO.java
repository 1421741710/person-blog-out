package com.itluo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Administrator
 */
@Data
public class MessageInDTO implements Serializable {

    private Long senderUserId;

    private Long recipientUserId;

    private String content;

}
