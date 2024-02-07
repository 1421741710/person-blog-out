package com.itluo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Administrator
 */
@Data
public class UserInfoPageQueryDTO implements Serializable {

    private Integer page;

    private Integer pageSize;

    private String name;

    private String phone;

    private String address;

}
