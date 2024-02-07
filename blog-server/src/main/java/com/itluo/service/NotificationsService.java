package com.itluo.service;

import com.itluo.dto.NotificationsInDTO;
import com.itluo.dto.NotificationsPageQueryDTO;
import com.itluo.dto.NotificationsUpDTO;
import com.itluo.result.PageResult;
import com.itluo.vo.NotificationsSeVO;

import java.util.List;

/**
 * @author Administrator
 */
public interface NotificationsService {


    /**
     * 分页查询
     * @param notificationsPageQueryDTO
     * @return
     */
    PageResult pageQuery(NotificationsPageQueryDTO notificationsPageQueryDTO);

    /**
     * 修改系统通知内容
     * @param notificationsUpDTO
     */
    void update(NotificationsUpDTO notificationsUpDTO);

    /**
     * 删除系统通知
     * @param id
     */
    void delete(Long id);

    /**
     * 发送系统通知
     * @param notificationsInDTO
     */
    void insert(NotificationsInDTO notificationsInDTO);

    /**
     * 获取系统通知
     * @return
     */
    List<NotificationsSeVO> findByNotifications();
}
