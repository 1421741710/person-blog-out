package com.itluo.controller.user;

import com.itluo.constant.MessageConstant;
import com.itluo.dto.MessageInDTO;
import com.itluo.result.Result;
import com.itluo.service.MessageService;
import com.itluo.vo.MessageVO;
import com.itluo.vo.UserInfoMessageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.util.List;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/user/message")
@Api(tags = "私信相关接口")
@Slf4j
public class MessageController {

    @Autowired
    private MessageService messageService;

    /**
     * 获取私信
     * @return
     */
    @GetMapping("/findByMessage")
    @ApiOperation("获取私信")
    public Result<List<UserInfoMessageVO>> findByMessage(){
        List<UserInfoMessageVO> message = messageService.findByMessage();
        return Result.success(MessageConstant.OPERATE,message);
    }

    /**
     * 根据用户ID获取私信
     * @return
     */
    @GetMapping("/findByMessageId")
    @ApiOperation("根据用户ID获取私信")
    public Result<List<MessageVO>> findByMessageId(Long userId){
        List<MessageVO> message = messageService.findByMessageId(userId);
        return Result.success(MessageConstant.OPERATE,message);
    }

    /**
     * 聊天添加
     * @return
     */
    @PatchMapping("/insertMessage")
    @ApiOperation("添加私信")
    public Result<Spring> insertMessage(@RequestBody MessageInDTO message){
        messageService.insertMessage(message);
        return Result.success(MessageConstant.OPERATE);
    }

}
