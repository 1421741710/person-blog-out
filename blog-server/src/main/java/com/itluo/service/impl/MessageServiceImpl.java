package com.itluo.service.impl;

import com.itluo.constant.MessageConstant;
import com.itluo.constant.StatusConstant;
import com.itluo.context.BaseContext;
import com.itluo.dto.MessageInDTO;
import com.itluo.entity.Message;
import com.itluo.exception.BaseException;
import com.itluo.mapper.MessageMapper;
import com.itluo.mapper.UserInfoMapper;
import com.itluo.service.MessageService;
import com.itluo.vo.MessageVO;
import com.itluo.vo.UserInfoMessageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author Administrator
 */
@Service
@Slf4j
public class MessageServiceImpl implements MessageService {


    @Autowired
    private MessageMapper messageMapper;


    /**
     * 获取私信
     * @return
     */
    @Override
    public List<UserInfoMessageVO> findByMessage() {
        Long userId = BaseContext.getCurrentId();
        List<UserInfoMessageVO> userInfoMessage = new ArrayList<>();
        List<Long> byChatObjectId = messageMapper.findByChatObjectId(userId);
        for (Long l : byChatObjectId){
            UserInfoMessageVO byLatestNews = messageMapper.findByLatestNews(userId, l);
            byLatestNews.setSenderUserId(l);
            byLatestNews.setRecipientUserId(userId);
            userInfoMessage.add(byLatestNews);
        }
        userInfoMessage.sort(Comparator.comparing(UserInfoMessageVO::getCreateTime).reversed());
        return userInfoMessage;
    }

    /**
     * 根据用户ID获取私信
     * @param id
     * @return
     */
    @Override
    public List<MessageVO> findByMessageId(Long id) {
        Long userId = BaseContext.getCurrentId();
        Message message = Message.builder()
                .senderUserId(id)
                .recipientUserId(userId)
                .build();
        List<MessageVO> messages = messageMapper.findByMessageId(message);
        return messages;
    }


    /**
     * 聊天添加
     * @param message
     */
    @Override
    public void insertMessage(MessageInDTO message) {
        Message messages = new Message();
        BeanUtils.copyProperties(message,messages);
        messages.setState(StatusConstant.DISABLE);
        messageMapper.insertMessage(messages);
    }

}
