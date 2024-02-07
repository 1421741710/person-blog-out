package com.itluo.service;

import com.itluo.dto.ProcessPageQueryDTO;
import com.itluo.dto.ProcessUpDTO;
import com.itluo.entity.Process;
import com.itluo.result.PageResult;

import java.util.List;

/**
 * @author Administrator
 */
public interface ProcessService {

    /**
     * 分页查询
     * @param processPageQueryDTO
     * @return
     */
    PageResult pageQuery(ProcessPageQueryDTO processPageQueryDTO);

    /**
     * 修改审核状态
     * @param id
     */
    void updateStatus(Long id);

    /**
     * 提交审核
     * @param processUpDTO
     */
    void update(ProcessUpDTO processUpDTO);

}
