package com.itluo.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Administrator
 */
@Data
public class NotificationsSeVO implements Serializable {

    private String content;

    private LocalDateTime createTime;

    private Long status;

}
