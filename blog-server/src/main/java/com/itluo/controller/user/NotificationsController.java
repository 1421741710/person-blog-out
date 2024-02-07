package com.itluo.controller.user;

import com.itluo.constant.MessageConstant;
import com.itluo.result.Result;
import com.itluo.service.NotificationsService;
import com.itluo.vo.NotificationsSeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/user/notifications")
@Api(tags = "系统通知相关接口")
@Slf4j
public class NotificationsController {


    @Autowired
    private NotificationsService notificationsService;

    /**
     * 获取系统通知
     * @return
     */
    @GetMapping("/findByNotifications")
    @ApiOperation("获取系统通知")
    public Result<List<NotificationsSeVO>> findByNotifications(){
        List<NotificationsSeVO> notificationsSe = notificationsService.findByNotifications();
        return Result.success(MessageConstant.OPERATE,notificationsSe);
    }


}
