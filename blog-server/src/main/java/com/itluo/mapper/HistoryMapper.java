package com.itluo.mapper;

import com.itluo.annotation.AutoFill;
import com.itluo.entity.History;
import com.itluo.enumeration.OperationType;
import com.itluo.vo.HistoryVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Administrator
 */
@Mapper
public interface HistoryMapper {

    /**
     * 根据文章id删除搜索历史
     * @param id
     */
    @Delete("delete from history where article_id = #{id};")
    void delete(Long id);

    /**
     * 根据用户id查询信息
     * @param userId
     * @return
     */
    @Select("select h.id,h.article_id,a.title from history h left join article a on a.id = h.article_id where h.user_id = #{userId}")
    List<HistoryVO> findByHistoryUserId(Long userId);

    /**
     * 添加搜索历史
     * @param history
     */
    @Insert("insert into history(user_id, article_id, create_time, update_time) " +
            "values(#{userId},#{articleId},#{createTime},#{updateTime});")
    @AutoFill(OperationType.INSERT)
    void insert(History history);

    /**
     * 根据id删除搜索历史
     * @param id
     */
    @Delete("delete from history where id = #{id};")
    void deleteHistoryId(Long id);

    /**
     * 根据id查询搜索历史
     * @param id
     * @return
     */
    @Select("select * from history where id = #{id};")
    History findByHistoryId(Long id);

    /**
     * 根据文章id和用户id查询搜索历史
     * @param articleId
     * @param userId
     * @return
     */
    @Select("select * from history where article_id = #{articleId} and user_id = #{userId}")
    History findByHistoryArticleIdAndUserId(Long articleId, Long userId);
}
