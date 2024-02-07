package com.itluo.mapper;

import com.itluo.entity.ArticleUser;
import org.apache.ibatis.annotations.*;

/**
 * @author Administrator
 */
@Mapper
public interface ArticleUserMapper {

    /**
     * 根据文章id删除信息
     * @param id
     */
    @Delete("delete from article_user where article_id = #{id};")
    void delete(Long id);

    /**
     * 根据用户id查询文章点赞情况
     * @param userId
     * @param articleId
     * @return
     */
    @Select("select * from article_user where user_id = #{userId} and article_id = #{articleId};")
    ArticleUser findByUserId(Long userId,Long articleId);

    /**
     * 添加文章点赞状态
     * @param articleUsers
     */
    @Insert("insert into article_user(user_id, article_id, like_tremple) VALUES(#{userId},#{articleId},#{likeTremple})")
    void insert(ArticleUser articleUsers);

    /**
     * 根据用户id和文章id修改文章点赞/踩/无
     * @param articleUser
     */
    @Update("update article_user set like_tremple = #{likeTremple} where article_id = #{articleId} and user_id = #{userId};")
    void update(ArticleUser articleUser);
}
