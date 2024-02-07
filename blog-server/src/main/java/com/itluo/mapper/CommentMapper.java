package com.itluo.mapper;

import com.github.pagehelper.Page;
import com.itluo.annotation.AutoFill;
import com.itluo.dto.CommentPageQueryDTO;
import com.itluo.entity.Comment;
import com.itluo.enumeration.OperationType;
import com.itluo.vo.CommentListVO;
import com.itluo.vo.CommentVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Administrator
 */
@Mapper
public interface CommentMapper {

    /**
     * 根据文章id删除评论
     * @param id
     */
    @Delete("delete from comment where article_id = #{id};")
    void delete(Long id);

    /**
     * 分页查询
     * @param commentPageQueryDTO
     * @return
     */
    Page<CommentVO> pageQuery(CommentPageQueryDTO commentPageQueryDTO);

    /**
     * 查询评论
     * @param id
     * @return
     */
    List<Long> batchSelectByIds(List<Long> id);

    /**
     * 删除评论
     * @param id
     */
    void batchDeleteByIds(List<Long> id);


    /**
     * 获取评论
     * @param id
     * @return
     */
    @Select("select u.user_id,u.name,u.img,c.comment,c.create_time from comment c left join user_info u on u.user_id = c.user_id where c.article_id = #{id};")
    List<CommentListVO> findByCommentArticleId(Long id);


    /**
     * 根据评论文章id获取评论数
     * @param id
     * @return
     */
    @Select("select count(*) from comment where article_id = #{id}")
    Long findByCommentArticleIdNum(Long id);


    /**
     * 添加评论
     * @param comment
     */
    @Insert("insert into comment(user_id, article_id, comment, create_time, update_time) " +
            "values(#{userId},#{articleId},#{comment},#{createTime},#{updateTime});")
    @AutoFill(OperationType.INSERT)
    void insert(Comment comment);
}
