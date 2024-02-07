package com.itluo.service;

import com.itluo.dto.LogPageQueryDTO;
import com.itluo.result.PageResult;

/**
 * @author Administrator
 */
public interface LogService {

    /**
     * 分页查询
     * @param logPageQueryDTO
     * @return
     */
    PageResult pageQuery(LogPageQueryDTO logPageQueryDTO);
}
