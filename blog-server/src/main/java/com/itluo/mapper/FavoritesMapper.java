package com.itluo.mapper;

import com.itluo.annotation.AutoFill;
import com.itluo.entity.Favorites;
import com.itluo.enumeration.OperationType;
import com.itluo.vo.FavoritesSelectVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Administrator
 */
@Mapper
public interface FavoritesMapper {

    /**
     * 根据文章id删除收藏夹
     * @param id
     */
    @Delete("delete from favorites where article_id = #{id};")
    void delete(Long id);

    /**
     * 获取用户收藏夹
     * @param id
     * @return
     */
    @Select("select f.id,f.article_id,a.title from favorites f left join article a on a.id = f.article_id where f.user_id = #{id};")
    List<FavoritesSelectVO> findByFavorites(Long id);

    /**
     * 根据收藏夹id查询收藏夹信息
     * @param id
     * @return
     */
    @Select("select * from favorites where id = #{id}")
    Favorites findByFavoritesId(Long id);

    /**
     * 删除用户收藏夹内容
     * @param id
     */
    @Delete("delete from favorites where id = #{id};")
    void deleteFavorites(Long id);

    /**
     * 添加用户收藏夹
     * @param favorites
     */
    @Insert("insert into favorites(user_id, article_id, create_time, update_time) " +
            "values(#{userId},#{articleId},#{createTime},#{updateTime});")
    @AutoFill(OperationType.INSERT)
    void insert(Favorites favorites);

    /**
     * 根据文章id和用户id查询收藏夹信息
     * @param articleId
     * @param id
     * @return
     */
    @Select("select * from favorites where article_id = #{articleId} and user_id = #{id};")
    Favorites findByFavoritesUserId(Long articleId, Long id);
}
