package com.itluo.controller.user;

import com.itluo.constant.MessageConstant;
import com.itluo.dto.UserInfoUpDTO;
import com.itluo.result.Result;
import com.itluo.service.UserInfoService;
import com.itluo.vo.UserInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/user/userinfo")
@Api(tags = "用户信息相关接口")
@Slf4j
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 获取用户信息
     * @return
     */
    @GetMapping("/findByUserInfo")
    @ApiOperation("获取用户信息")
    public Result<UserInfoVO> findByUserInfo(){
        UserInfoVO userInfo = userInfoService.findByUserInfo();
        return Result.success(MessageConstant.OPERATE,userInfo);
    }

    /**
     * 用户修改用户信息
     * @param userInfoUpDTO
     * @return
     */
    @PatchMapping("/update")
    @ApiOperation("修改用户信息")
    public Result<String> update(@RequestBody UserInfoUpDTO userInfoUpDTO){
        userInfoService.updateUserInfo(userInfoUpDTO);
        return Result.success(MessageConstant.OPERATE);
    }

}
