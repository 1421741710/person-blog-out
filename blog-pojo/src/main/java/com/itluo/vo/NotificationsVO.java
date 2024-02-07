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
public class NotificationsVO implements Serializable {

    private Long id;

    private String username;

    private Long type;

    private String content;

    private Long status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
