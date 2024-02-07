package com.itluo.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itluo.constant.MessageConstant;
import com.itluo.constant.StatusConstant;
import com.itluo.context.BaseContext;
import com.itluo.dto.NotificationsInDTO;
import com.itluo.dto.NotificationsPageQueryDTO;
import com.itluo.dto.NotificationsUpDTO;
import com.itluo.entity.Notifications;
import com.itluo.entity.User;
import com.itluo.enumeration.MessageType;
import com.itluo.exception.AccountNotFoundException;
import com.itluo.exception.NotificationsNotFoundException;
import com.itluo.json.JacksonObjectMapper;
import com.itluo.mapper.NotificationsMapper;
import com.itluo.mapper.UserMapper;
import com.itluo.result.PageResult;
import com.itluo.result.WebSocketMessage;
import com.itluo.service.NotificationsService;
import com.itluo.vo.NotificationsSeVO;
import com.itluo.vo.NotificationsVO;
import com.itluo.websocket.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.websocket.Session;
import java.util.List;

/**
 * @author Administrator
 */
@Service
@Slf4j
public class NotificationsServiceImpl implements NotificationsService {

    @Autowired
    private NotificationsMapper notificationsMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private WebSocketServer webSocketServer;

    /**
     * 分页查询
     * @param notificationsPageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(NotificationsPageQueryDTO notificationsPageQueryDTO) {
        PageHelper.startPage(notificationsPageQueryDTO.getPage(),notificationsPageQueryDTO.getPageSize());
        Page<NotificationsVO> page = notificationsMapper.pageQuery(notificationsPageQueryDTO);
        return new PageResult(page.getTotal(),page.getResult());
    }

    /**
     * 修改系统通知内容
     * @param notificationsUpDTO
     */
    @Override
    public void update(NotificationsUpDTO notificationsUpDTO) {
        Long id = notificationsUpDTO.getId();
        validate(id);
        Notifications not = new Notifications();
        BeanUtils.copyProperties(notificationsUpDTO,not);
        notificationsMapper.update(not);
    }

    /**
     * 验证
     * @param id
     */
    private void validate(Long id){
        Notifications notifications = notificationsMapper.findByNotificationsId(id);
        if (notifications == null){
            throw new NotificationsNotFoundException(MessageConstant.STUDENT);
        }
    }

    /**
     * 删除系统通知
     * @param id
     */
    @Override
    public void delete(Long id) {
        validate(id);
        notificationsMapper.delete(id);
    }

    /**
     * 发送系统通知
     * @param notificationsInDTO
     */
    @Override
    public void insert(NotificationsInDTO notificationsInDTO) {
        Notifications notifications = Notifications.builder()
                .status(StatusConstant.DISABLE)
                .build();
        BeanUtils.copyProperties(notificationsInDTO,notifications);
        JacksonObjectMapper jacksonObjectMapper = new JacksonObjectMapper();
        WebSocketMessage webSocketMessage = new WebSocketMessage();
        if (notifications.getType().equals(StatusConstant.DISABLE)){
            notificationsMapper.insertNotificationsSystem(notifications);
            webSocketMessage.setType(MessageType.SYSTEM_NOTICE);
            try {
                String str = jacksonObjectMapper.writeValueAsString(notifications);
                webSocketMessage.setMessage(str);
                String s = jacksonObjectMapper.writeValueAsString(webSocketMessage);
                webSocketServer.sendToAllClient(s);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取系统通知
     * @return
     */
    @Override
    public List<NotificationsSeVO> findByNotifications() {
        Long userId = BaseContext.getCurrentId();
        List<NotificationsSeVO> notificationsSe = notificationsMapper.findByNotifications(userId);
        return notificationsSe;
    }
}
