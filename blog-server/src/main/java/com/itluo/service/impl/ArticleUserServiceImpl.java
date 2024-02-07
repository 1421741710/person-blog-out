package com.itluo.service.impl;

import com.itluo.constant.MessageConstant;
import com.itluo.constant.StatusConstant;
import com.itluo.context.BaseContext;
import com.itluo.dto.ArticleUserUpDTO;
import com.itluo.entity.Article;
import com.itluo.entity.ArticleUser;
import com.itluo.exception.ArticleIDNotFoundException;
import com.itluo.mapper.ArticleMapper;
import com.itluo.mapper.ArticleUserMapper;
import com.itluo.service.ArticleUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 */
@Service
@Slf4j
public class ArticleUserServiceImpl implements ArticleUserService {

    @Autowired
    private ArticleUserMapper articleUserMapper;

    @Autowired
    private ArticleMapper articleMapper;

    /**
     * 修改文章点赞/踩/无
     * @param articleUserUpDTO
     */
    @Override
    public Long likeTrempl(ArticleUserUpDTO articleUserUpDTO) {
        Long userId = BaseContext.getCurrentId();
        Long articleId = articleUserUpDTO.getArticleId();
        Article byArticleId = articleMapper.findByArticleId(articleId);
        if (byArticleId == null){
            throw new ArticleIDNotFoundException(MessageConstant.OPERATE);
        }
        ArticleUser articleUser = articleUserMapper.findByUserId(userId,articleId);
        ArticleUser articleUsers = ArticleUser.builder()
                .userId(userId)
                .articleId(articleUserUpDTO.getArticleId())
                .likeTremple(articleUserUpDTO.getLikeTremple())
                .build();
        if (articleUser == null){
            Long insert = insert(articleUsers, byArticleId.getLikes());
            return insert;
        }else{
            Long update = update(articleUsers, articleUser.getLikeTremple(), byArticleId.getLikes());
            return update;
        }
    }

    /**
     * 修改文章点赞状态
     * @param articleUser
     */
    private Long update(ArticleUser articleUser,Long likeTremple,Long likes){
        Long likeTremples = articleUser.getLikeTremple();
        Long aLong = likes;
        boolean flag = likeTremples.equals(StatusConstant.ENABLE) ? true : false;
        if (likeTremples.equals(likeTremple)){
            if (flag){
                aLong = updateArticle(false, likes, articleUser.getArticleId());
            }
            articleUser.setLikeTremple(StatusConstant.ZERO);
            articleUserMapper.update(articleUser);
            return aLong;
        }else{
            if (flag){
                aLong = updateArticle(true, likes, articleUser.getArticleId());
            }else {
                if (likeTremple.equals(StatusConstant.ENABLE)){
                    aLong = updateArticle(false, likes, articleUser.getArticleId());
                }
            }
            articleUserMapper.update(articleUser);
            return aLong;
        }
    }

    /**
     * 决定是+还是-
     * @param flag
     * @param likes
     * @param articleId
     */
    private Long updateArticle(boolean flag,Long likes,Long articleId){
        Long num = flag ? likes + 1 : likes - 1;
        Article article = Article.builder()
                .id(articleId)
                .likes(num)
                .build();
        articleMapper.update(article);
        return num;
    }

    /**
     * 添加文章点赞状态
     * @param articleUsers
     * @param likes
     */
    private Long insert(ArticleUser articleUsers,Long likes){
        Long num = likes;
        if (articleUsers.getLikeTremple().equals(StatusConstant.ENABLE)){
            num += 1;
        }
        Article article = Article.builder()
                .id(articleUsers.getArticleId())
                .likes(num)
                .build();
        articleMapper.update(article);
        articleUserMapper.insert(articleUsers);
        return num;
    }
}
