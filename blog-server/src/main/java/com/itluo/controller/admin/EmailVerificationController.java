package com.itluo.controller.admin;

import com.itluo.annotation.CustomPermission;
import com.itluo.constant.MessageConstant;
import com.itluo.dto.EmailVerificationPageQueryDTO;
import com.itluo.enumeration.RoleType;
import com.itluo.result.PageResult;
import com.itluo.result.Result;
import com.itluo.service.EmailVerificationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/admin/emailVerification")
@Api(tags = "邮箱验证码相关接口")
@Slf4j
public class EmailVerificationController {


    @Autowired
    private EmailVerificationService emailVerificationService;

    /**
     * 分页查询
     * @param emailVerificationPageQueryDTO
     * @return
     */
    @GetMapping("/findByEmailVerification")
    @ApiOperation("分页查询")
    @CustomPermission(RoleType.SUPER_ADMINISTRATOR)
    public Result<PageResult> pageQuery(EmailVerificationPageQueryDTO emailVerificationPageQueryDTO){
        PageResult pageResult = emailVerificationService.pageQuery(emailVerificationPageQueryDTO);
        return Result.success(MessageConstant.OPERATE,pageResult);
    }


}
