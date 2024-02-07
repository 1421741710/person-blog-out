package com.itluo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Administrator
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoMessageVO implements Serializable {

    private String name;

    private String img;

    private Long senderUserId;

    private Long recipientUserId;

    private String content;

    private Long state;

    private LocalDateTime createTime;


}
