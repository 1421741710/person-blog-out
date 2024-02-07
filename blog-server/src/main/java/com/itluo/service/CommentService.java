package com.itluo.service;

import com.itluo.dto.CommentInDTO;
import com.itluo.dto.CommentPageQueryDTO;
import com.itluo.result.PageResult;
import com.itluo.vo.CommentListVO;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
public interface CommentService {

    /**
     * 分页查询
     * @param commentPageQueryDTO
     * @return
     */
    PageResult pageQuery(CommentPageQueryDTO commentPageQueryDTO);

    /**
     * 删除评论
     * @param id
     */
    void delete(List<Long> id);

    /**
     * 获取评论
     * @param id
     * @return
     */
    Map<String,Object> findByComment(Long id);

    /**
     * 添加评论
     * @param commentInDTO
     */
    void insert(CommentInDTO commentInDTO);
}
