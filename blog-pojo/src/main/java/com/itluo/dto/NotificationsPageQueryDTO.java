package com.itluo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Administrator
 */
@Data
public class NotificationsPageQueryDTO implements Serializable {


    private Integer page;

    private Integer pageSize;

    private String content;

}
