package com.itluo.service;

import com.itluo.vo.FavoritesSelectVO;

import java.util.List;

/**
 * @author Administrator
 */
public interface FavoritesService {

    /**
     * 获取用户收藏夹
     * @return
     */
    List<FavoritesSelectVO> findByFavorites();

    /**
     * 删除用户收藏夹内容
     * @param id
     */
    void delete(Long id);

    /**
     * 添加用户收藏夹
     * @param articleId
     */
    void insert(Long articleId);
}
