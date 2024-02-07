package com.itluo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Administrator
 */
@Data
public class AdminPageQueryDTO implements Serializable {

    /**
     * 页数
     */
    private Integer page;

    /**
     * 每页记录数
     */
    private Integer pageSize;

    private String username;

    private Long role;
}
