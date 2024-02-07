package com.itluo.mapper;

import com.itluo.annotation.AutoFill;
import com.itluo.entity.Message;
import com.itluo.enumeration.OperationType;
import com.itluo.vo.MessageVO;
import com.itluo.vo.UserInfoMessageVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Administrator
 */
@Mapper
public interface MessageMapper {


    /**
     * 获取和当前用户聊天的所有用户
     * @param userId
     * @return
     */
    @Select("SELECT DISTINCT CASE WHEN sender_user_id = #{userId} THEN recipient_user_id ELSE sender_user_id END AS sender_user_id FROM message WHERE #{userId} IN (sender_user_id, recipient_user_id);")
    List<Long> findByChatObjectId(Long userId);

    /**
     * 获取当前用户和聊天对象的最新消息并且获取聊天对象的信息
     * @param currentId
     * @param counterpartId
     * @return
     */
    @Select("select m.content,m.state,m.create_time,u.name,u.img from message m left join user_info u on u.user_id = #{counterpartId} " +
            "where (m.sender_user_id = #{currentId} and m.recipient_user_id = #{counterpartId}) or (m.sender_user_id = #{counterpartId} and m.recipient_user_id = #{currentId}) " +
            "order by m.create_time desc limit 1")
    UserInfoMessageVO findByLatestNews(Long currentId,Long counterpartId);

    /**
     * 查询当前用户和聊天对象的记录
     * @param message
     * @return
     */
    @Select("select * from message where (sender_user_id = #{senderUserId} and recipient_user_id = #{recipientUserId}) " +
            "or (sender_user_id = #{recipientUserId} and recipient_user_id = #{senderUserId})" +
            "order by create_time asc")
    List<MessageVO> findByMessageId(Message message);

    /**
     * 添加聊天记录
     * @param messages
     */
    @Insert("insert into message(sender_user_id, recipient_user_id, content, state, create_time, update_time) " +
            "VALUES(#{senderUserId},#{recipientUserId},#{content},#{state},#{createTime},#{updateTime})")
    @AutoFill(OperationType.INSERT)
    void insertMessage(Message messages);
}
