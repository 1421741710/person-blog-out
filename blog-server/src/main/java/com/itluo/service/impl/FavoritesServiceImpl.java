package com.itluo.service.impl;

import com.itluo.constant.MessageConstant;
import com.itluo.context.BaseContext;
import com.itluo.entity.Article;
import com.itluo.entity.Favorites;
import com.itluo.exception.ArticleIDNotFoundException;
import com.itluo.exception.BaseException;
import com.itluo.exception.DeletionNotAllowedException;
import com.itluo.mapper.ArticleMapper;
import com.itluo.mapper.FavoritesMapper;
import com.itluo.service.FavoritesService;
import com.itluo.vo.FavoritesSelectVO;
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
public class FavoritesServiceImpl implements FavoritesService {

    @Autowired
    private FavoritesMapper favoritesMapper;

    @Autowired
    private ArticleMapper articleMapper;

    /**
     * 获取用户收藏夹
     * @return
     */
    @Override
    public List<FavoritesSelectVO> findByFavorites() {
        Long id = BaseContext.getCurrentId();
        List<FavoritesSelectVO> favoritesSelect = favoritesMapper.findByFavorites(id);
        return favoritesSelect;
    }

    /**
     * 删除用户收藏夹内容
     * @param id
     */
    @Override
    @Transactional(rollbackFor = {ArticleIDNotFoundException.class})
    public void delete(Long id) {
        verify(id);
        favoritesMapper.deleteFavorites(id);
    }

    /**
     * 验证
     * @param id
     */
    private void verify(Long id){
        Favorites favorites = favoritesMapper.findByFavoritesId(id);
        if (favorites == null){
            throw new DeletionNotAllowedException(MessageConstant.STUDENT);
        }
    }

    /**
     * 添加用户收藏夹
     * @param articleId
     */
    @Override
    public void insert(Long articleId) {
        Article byArticleId = articleMapper.findByArticleId(articleId);
        if (byArticleId == null){
            throw new ArticleIDNotFoundException(MessageConstant.STUDENT);
        }
        Long id = BaseContext.getCurrentId();
        Favorites favorite = favoritesMapper.findByFavoritesUserId(articleId,id);
        Article article = new Article();
        article.setId(articleId);
        if (favorite != null){
            favoritesMapper.deleteFavorites(favorite.getId());
            Long favoriteNumber = byArticleId.getFavorite() == 0 ? 0 : byArticleId.getFavorite() - 1;
            article.setFavorite(favoriteNumber);
            articleMapper.update(article);
        }else{
            Favorites favorites = Favorites.builder()
                    .userId(id)
                    .articleId(articleId)
                    .build();
            favoritesMapper.insert(favorites);
            article.setFavorite(byArticleId.getFavorite() + 1);
        }
        articleMapper.update(article);

    }
}
