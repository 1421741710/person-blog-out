package com.itluo.service;

import com.itluo.dto.MessageInDTO;
import com.itluo.vo.MessageVO;
import com.itluo.vo.UserInfoMessageVO;

import java.util.List;

/**
 * @author Administrator
 */
public interface MessageService {

    /**
     * 获取私信
     * @return
     */
    List<UserInfoMessageVO> findByMessage();

    /**
     * 根据用户ID获取私信
     * @param id
     * @return
     */
    List<MessageVO> findByMessageId(Long id);

    /**
     * 添加聊天记录
     * @param message
     */
    void insertMessage(MessageInDTO message);
}
