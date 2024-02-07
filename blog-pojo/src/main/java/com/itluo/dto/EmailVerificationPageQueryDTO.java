package com.itluo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Administrator
 */
@Data
public class EmailVerificationPageQueryDTO implements Serializable {

    private Integer page;

    private Integer pageSize;

    private String email;

}
