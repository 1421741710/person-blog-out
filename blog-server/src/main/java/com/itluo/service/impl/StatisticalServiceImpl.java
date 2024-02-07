package com.itluo.service.impl;

import com.itluo.entity.User;
import com.itluo.mapper.ArticleMapper;
import com.itluo.mapper.UserMapper;
import com.itluo.service.StatisticalService;
import com.itluo.vo.StatisticalCountVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@Service
@Slf4j
public class StatisticalServiceImpl implements StatisticalService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ArticleMapper articleMapper;

    /**
     * 统计分析
     * @return
     */
    @Override
    public Map<String, Object> findByStatistical() {
        Map<String,Object> map = new HashMap<>(3);
        Long userCount = userMapper.findByUserCount();
        Long articleCount = articleMapper.findByArticleCount();
        List<StatisticalCountVO> statisticalCount = userMapper.findByUserGrowth();
        map.put("userCount",userCount);
        map.put("articleCount",articleCount);
        map.put("userGrowth",statisticalCount);
        return map;
    }
}
