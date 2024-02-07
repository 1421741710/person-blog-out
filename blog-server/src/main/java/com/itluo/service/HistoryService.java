package com.itluo.service;

import com.itluo.vo.HistoryVO;

import java.util.List;

/**
 * @author Administrator
 */
public interface HistoryService {


    /**
     * 获取搜索历史
     * @return
     */
    List<HistoryVO> findByHistory();

    /**
     * 添加搜索历史
     * @param articleId
     */
    void insert(Long articleId);

    /**
     * 删除搜索历史
     * @param id
     */
    void delete(Long id);
}
