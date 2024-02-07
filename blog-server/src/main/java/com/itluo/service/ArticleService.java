package com.itluo.service;

import com.itluo.dto.ArticleInDTO;
import com.itluo.dto.ArticlePageQueryDTO;
import com.itluo.dto.ArticleUpDTO;
import com.itluo.result.PageResult;
import com.itluo.vo.ArticleLoginVO;
import com.itluo.vo.ArticleVO;

import java.util.List;

/**
 * @author Administrator
 */
public interface ArticleService {

    /**
     * 分页查询
     * @param articlePageQueryDTO
     * @return
     */
    PageResult pageQuery(ArticlePageQueryDTO articlePageQueryDTO);

    /**
     * 修改文章
     * @param articleUpDTO
     */
    void update(ArticleUpDTO articleUpDTO);

    /**
     * 添加文章
     * @param articleInDTO
     */
    void insert(ArticleInDTO articleInDTO);

    /**
     * 删除文章
     * @param id
     */
    void delete(Long id);

    /**
     * 获取文章
     * @return
     */
    List<ArticleVO> findByPublicArticle();


    /**
     * 登录获取文章
     * @return
     */
    List<ArticleLoginVO> findByArticleUserId();

    /**
     * 根据id获取文章
     * @param id
     * @return
     */
    ArticleLoginVO findByArticleId(Long id);

    /**
     * 公共文章根据id获取文章
     * @param id
     * @return
     */
    ArticleVO findByPublicArticleId(Long id);

    /**
     * 模糊查询文章标题
     * @param title
     * @return
     */
    List<ArticleVO> findByPublicSearchArticle(String title);
}
