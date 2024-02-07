package com.itluo.controller.user;

import com.itluo.constant.MailBoxConstant;
import com.itluo.constant.MessageConstant;
import com.itluo.dto.EmailDTO;
import com.itluo.dto.UserRegisterDTO;
import com.itluo.entity.EmailVerification;
import com.itluo.exception.BaseException;
import com.itluo.exception.EmailAlreadyExistsException;
import com.itluo.result.Result;
import com.itluo.service.EmailService;
import com.itluo.service.EmailVerificationService;
import com.itluo.utils.HtmlUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/emil")
@Api(tags = "邮件相关接口")
@Slf4j
public class EmailController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private EmailVerificationService emailVerificationService;


    /***
     * 邮件发送验证码
     * @param emailDTO
     * @return
     */
    @PostMapping("/findByEmil")
    @ApiOperation("发送邮件验证码")
    @Transactional(rollbackFor = {Exception.class})
    public Result<String> findByEmil(@RequestBody EmailDTO emailDTO){
        String mailbox = emailDTO.getMailbox();
        Long type = emailDTO.getType();
        boolean falg = emailVerificationService.findByEmail(mailbox);
        if (falg){
            throw new EmailAlreadyExistsException(MessageConstant.CODE_FREQUENTLY);
        }
        String code = (String) redisTemplate.opsForValue().get(mailbox);
        if (code != null){
            throw new BaseException(MessageConstant.ALREADY_EXISTS);
        }
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        int index = 5;
        for (int i = 0; i < index; i++) {
            stringBuilder.append(random.nextInt(10));
        }
        redisTemplate.opsForValue().set(mailbox,stringBuilder.toString());
        redisTemplate.expire(mailbox,60, TimeUnit.SECONDS);
        // 读取 HTML 模板内容
        String finalHtml = HtmlUtils.loadAndModifyVerificationTemplate("/templates/verification.html",stringBuilder.toString(),type);
        // 替换占位符为实际邮箱和验证码
        emailService.sendHtmlMail(mailbox, MailBoxConstant.TITLE,finalHtml);
        return Result.success(MessageConstant.OPERATE);
    }



}
