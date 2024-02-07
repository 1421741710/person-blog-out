package com.itluo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Administrator
 */
@Data
public class UserPageQueryDTO implements Serializable {

    private Integer page;

    private Integer pageSize;

    private String username;

    private String mailbox;

    private Long status;

}
