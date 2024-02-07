package com.itluo.controller.admin;

import com.itluo.annotation.CustomPermission;
import com.itluo.annotation.LogAnnotation;
import com.itluo.constant.MessageConstant;
import com.itluo.dto.UserInfoPageQueryDTO;
import com.itluo.dto.UserInfoUpDTO;
import com.itluo.enumeration.RoleType;
import com.itluo.result.PageResult;
import com.itluo.result.Result;
import com.itluo.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Administrator
 */
@RestController("AdminUserInfoController")
@RequestMapping("/admin/userinfo")
@Api(tags = "用户信息相关接口")
@Slf4j
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;


    /**
     * 分页查询
     * @param userInfoPageQueryDTO
     * @return
     */
    @GetMapping("/findByUserInfo")
    @ApiOperation("分页查询")
    @CustomPermission(RoleType.SUPER_ADMINISTRATOR)
    public Result<PageResult> pageQuery(UserInfoPageQueryDTO userInfoPageQueryDTO){
        PageResult pageResult = userInfoService.pageQuery(userInfoPageQueryDTO);
        return Result.success(MessageConstant.OPERATE,pageResult);
    }

    /**
     * 修改用户信息
     * @param userInfoUpDTO
     * @return
     */
    @PatchMapping("/update")
    @ApiOperation("修改用户信息")
    @LogAnnotation(module = "用户信息",operator = "修改用户信息")
    @CustomPermission(RoleType.SUPER_ADMINISTRATOR)
    public Result<String> udpate(@RequestBody UserInfoUpDTO userInfoUpDTO){
        userInfoService.update(userInfoUpDTO);
        return Result.success(MessageConstant.OPERATE);
    }

}
