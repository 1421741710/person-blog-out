package com.itluo.controller.admin;

import com.itluo.annotation.LogAnnotation;
import com.itluo.constant.MessageConstant;
import com.itluo.dto.NotificationsInDTO;
import com.itluo.dto.NotificationsPageQueryDTO;
import com.itluo.dto.NotificationsUpDTO;
import com.itluo.entity.Notifications;
import com.itluo.result.PageResult;
import com.itluo.result.Result;
import com.itluo.service.NotificationsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Administrator
 */
@RestController("AdminNotificationsController")
@RequestMapping("/admin/notifications")
@Api(tags = "系统通知相关接口")
@Slf4j
public class NotificationsController {

    @Autowired
    private NotificationsService notificationsService;


    /**
     * 分页查询
     * @param notificationsPageQueryDTO
     * @return
     */
    @GetMapping("/findBynotifications")
    @ApiOperation("分页查询")
    public Result<PageResult> findBynNotifications(NotificationsPageQueryDTO notificationsPageQueryDTO){
        PageResult pageResult = notificationsService.pageQuery(notificationsPageQueryDTO);
        return Result.success(MessageConstant.OPERATE,pageResult);
    }

    /**
     * 修改系统通知内容
     * @param notificationsUpDTO
     * @return
     */
    @PatchMapping("/update")
    @ApiOperation("修改系统通知内容")
    @LogAnnotation(module = "系统通知",operator = "修改系统通知内容")
    public Result<String> update(@RequestBody NotificationsUpDTO notificationsUpDTO){
        notificationsService.update(notificationsUpDTO);
        return Result.success(MessageConstant.OPERATE);
    }

    /**
     * 删除系统通知
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @ApiOperation("删除系统通知")
    @LogAnnotation(module = "系统通知",operator = "删除系统通知")
    public Result<String> delete(@PathVariable Long id){
        notificationsService.delete(id);
        return Result.success(MessageConstant.OPERATE);
    }

    /**
     * 发送系统通知
     * @param notificationsInDTO
     * @return
     */
    @PostMapping("/insert")
    @ApiOperation("发送系统通知")
    @LogAnnotation(module = "系统通知",operator = "发送系统通知")
    public Result<String> insert(@RequestBody NotificationsInDTO notificationsInDTO){
        notificationsService.insert(notificationsInDTO);
        return Result.success(MessageConstant.OPERATE);
    }

}
