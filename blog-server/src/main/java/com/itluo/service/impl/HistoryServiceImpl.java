package com.itluo.service.impl;

import com.itluo.constant.MessageConstant;
import com.itluo.context.BaseContext;
import com.itluo.entity.Article;
import com.itluo.entity.History;
import com.itluo.exception.ArticleIDNotFoundException;
import com.itluo.exception.BaseException;
import com.itluo.exception.DeletionNotAllowedException;
import com.itluo.mapper.ArticleMapper;
import com.itluo.mapper.HistoryMapper;
import com.itluo.service.HistoryService;
import com.itluo.vo.HistoryVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Administrator
 */
@Service
@Slf4j
public class HistoryServiceImpl implements HistoryService {


    @Autowired
    private HistoryMapper historyMapper;

    @Autowired
    private ArticleMapper articleMapper;

    /**
     * 获取搜索历史
     * @return
     */
    @Override
    public List<HistoryVO> findByHistory() {
        Long userId = BaseContext.getCurrentId();
        List<HistoryVO> history = historyMapper.findByHistoryUserId(userId);
        return history;
    }

    /**
     * 添加搜索历史
     * @param articleId
     */
    @Override
    public void insert(Long articleId) {
        Article byArticleId = articleMapper.findByArticleId(articleId);
        if (byArticleId == null){
            throw new ArticleIDNotFoundException(MessageConstant.STUDENT);
        }
        Long userId = BaseContext.getCurrentId();
        History byHistoryArticleIdAndUserId = historyMapper.findByHistoryArticleIdAndUserId(articleId, userId);
        if (byHistoryArticleIdAndUserId != null){
            return;
        }
        History history = History.builder()
                .userId(userId)
                .articleId(articleId)
                .build();
        historyMapper.insert(history);
    }

    /**
     * 删除搜索历史
     * @param id
     */
    @Override
    public void delete(Long id) {
        History history = historyMapper.findByHistoryId(id);
        if (history == null){
            throw new BaseException(MessageConstant.STUDENT);
        }
        historyMapper.deleteHistoryId(id);
    }
}
