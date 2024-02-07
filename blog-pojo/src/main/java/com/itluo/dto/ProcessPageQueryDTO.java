package com.itluo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Administrator
 */
@Data
public class ProcessPageQueryDTO implements Serializable {

    private Integer page;

    private Integer pageSize;

    private Long type;

    private Long status;

    private Long result;
}
