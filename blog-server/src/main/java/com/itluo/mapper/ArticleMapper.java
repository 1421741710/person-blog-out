package com.itluo.mapper;

import com.github.pagehelper.Page;
import com.itluo.annotation.AutoFill;
import com.itluo.dto.ArticlePageQueryDTO;
import com.itluo.entity.Article;
import com.itluo.enumeration.OperationType;
import com.itluo.vo.ArticleLoginVO;
import com.itluo.vo.ArticleVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Administrator
 */
@Mapper
public interface ArticleMapper{

    /**
     * 分页查询
     * @param articlePageQueryDTO
     * @return
     */
    Page<Article> pageQuery(ArticlePageQueryDTO articlePageQueryDTO);

    /**
     * 根据id查询信息
     * @param id
     * @return
     */
    @Select("select * from article where id = #{id}")
    Article findByArticleId(Long id);

    /**
     * 修改文章
     * @param articles
     */
    @AutoFill(OperationType.UPDATE)
    void update(Article articles);

    /**
     * 添加文章
     * @param article
     */
    @Insert("insert into article(title, secondary, content, img, create_time, update_time) " +
            "VALUES(#{title},#{secondary},#{content},#{img},#{createTime},#{updateTime})")
    @AutoFill(OperationType.INSERT)
    void insert(Article article);

    /**
     * 根据文章id删除文章
     * @param id
     */
    @Delete("delete from article where id = #{id};")
    void delete(Long id);

    /**
     * 获取文章
     * @return
     */
    @Select("select id,title,secondary,content,img,likes,favorite from article;")
    List<ArticleVO> findByArticleList();

    /**
     * 根据用户id查询文章和文章点赞
     * @param userId
     * @return
     */
    @Select("select a.id,a.title,a.secondary,a.content,a.img,a.likes,au.like_tremple as like_tremple from article a " +
            "LEFT JOIN article_user au ON au.article_id = a.id and au.user_id = #{userId};")
    List<ArticleLoginVO> findByArticleUserId(Long userId);

    /**
     *根据用户id和文章id查询文章
     * @param id
     * @param userId
     * @return
     */
    @Select("select a.id,a.title,a.secondary,a.content,a.img,a.likes,a.favorite,a.create_time,au.like_tremple as like_tremple," +
            "IF((SELECT COUNT(*) FROM favorites WHERE article_id = a.id AND user_id = #{userId}) > 0, 1, 0) AS favorites " +
            "from article a " +
            "left join article_user au on au.article_id = a.id and au.user_id = #{userId} where a.id = #{id};")
    ArticleLoginVO findByArticleIdSelect(Long id,Long userId);

    /**
     * 统计文章
     * @return
     */
    @Select("select count(*) from article;")
    Long findByArticleCount();
}
