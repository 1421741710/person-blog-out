package com.itluo.service;

import com.itluo.dto.ArticleUserUpDTO;

/**
 * @author Administrator
 */
public interface ArticleUserService {

    /**
     * 修改文章点赞/踩/无
     * @param articleUserUpDTO
     * @return
     */
    Long likeTrempl(ArticleUserUpDTO articleUserUpDTO);
}
