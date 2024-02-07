package com.itluo.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itluo.dto.LogPageQueryDTO;
import com.itluo.entity.Log;
import com.itluo.mapper.LogMapper;
import com.itluo.result.PageResult;
import com.itluo.service.LogService;
import com.itluo.utils.LocalDateTimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 */
@Service
@Slf4j
public class LogServiceImpl implements LogService {

    @Autowired
    private LogMapper logMapper;

    /**
     * 分页查询
     * @param logPageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(LogPageQueryDTO logPageQueryDTO) {
        PageHelper.startPage(logPageQueryDTO.getPage(),logPageQueryDTO.getPageSize());
        Page<Log> page = logMapper.pageQuery(logPageQueryDTO);
        return new PageResult(page.getTotal(),page.getResult());
    }
}
