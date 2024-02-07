package com.itluo.mapper;

import com.github.pagehelper.Page;
import com.itluo.annotation.AutoFill;
import com.itluo.dto.NotificationsPageQueryDTO;
import com.itluo.entity.Notifications;
import com.itluo.enumeration.OperationType;
import com.itluo.vo.NotificationsSeVO;
import com.itluo.vo.NotificationsVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Administrator
 */
@Mapper
public interface NotificationsMapper {


    /**
     * 分页查询
     * @param notificationsPageQueryDTO
     * @return
     */
    Page<NotificationsVO> pageQuery(NotificationsPageQueryDTO notificationsPageQueryDTO);

    /**
     * 根据系统通知id查询内容
     * @param id
     * @return
     */
    @Select("select * from notifications where id = #{id};")
    Notifications findByNotificationsId(Long id);

    /**
     * 修改系统通知内容
     * @param not
     */
    @AutoFill(OperationType.UPDATE)
    void update(Notifications not);

    /**
     * 删除系统通知
     * @param id
     */
    @Delete("delete from notifications where id = #{id};")
    void delete(Long id);

    /**
     * 添加指定类型的系统通知
     * @param notifications
     */
    @Insert("insert into notifications(user_id,type,content,create_time,update_time,status) " +
            "values(#{userId},#{type},#{content},#{createTime},#{updateTime},#{status})")
    @AutoFill(OperationType.INSERT)
    void insertNotifications(Notifications notifications);

    /**
     * 添加系统通知
     * @param notifications
     */
    @Insert("insert into notifications(type,content,create_time,update_time,status) values(#{type},#{content},#{createTime},#{updateTime},#{status})")
    @AutoFill(OperationType.INSERT)
    void insertNotificationsSystem(Notifications notifications);

    /**
     * 获取系统通知
     * @param userId
     * @return
     */
    @Select("select content,create_time,status from notifications where user_id = #{id} or type = 2;")
    List<NotificationsSeVO> findByNotifications(Long userId);
}
