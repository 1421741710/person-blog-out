package com.itluo.controller.user;

import com.itluo.constant.JwtClaimsConstant;
import com.itluo.constant.MessageConstant;
import com.itluo.dto.*;
import com.itluo.entity.User;
import com.itluo.properties.JwtProperties;
import com.itluo.result.Result;
import com.itluo.service.UserService;
import com.itluo.utils.JwtUtil;
import com.itluo.vo.UserLoginVO;
import com.itluo.vo.UserSelectVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.websocket.server.PathParam;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/user")
@Slf4j
@Api(tags = "用户相关接口")
public class UserController {


    @Autowired
    private UserService userService;

    @Autowired
    private JwtProperties jwtProperties;


    /**
     * 登录
     * @param userLoginDTO
     * @return
     */
    @PostMapping("/login")
    @ApiOperation("用户登录")
    public Result login(@Valid @RequestBody UserLoginDTO userLoginDTO, HttpServletRequest request){
        UserSelectVO user = userService.login(userLoginDTO,request);
        Map<String,Object> claim = new HashMap<>(1);
        claim.put(JwtClaimsConstant.USER_ID,user.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getUserSecretKey(),
                jwtProperties.getAdminTtl(),
                claim
        );
        UserLoginVO userLoginVO = UserLoginVO.builder()
                .id(user.getId())
                .name(user.getName())
                .img(user.getImg())
                .token(token)
                .build();
        return Result.success(MessageConstant.LOGIN,userLoginVO);
    }

    /**
     * 修改密码
     * @param userUpPwdDTO
     * @return
     */
    @PatchMapping("/updatePwd")
    @ApiOperation("修改密码")
    public Result<String> updatePwd(@Valid @RequestBody UserUpPwdDTO userUpPwdDTO){
        userService.updatePwd(userUpPwdDTO);
        return Result.success(MessageConstant.OPERATE);
    }

    /**
     * 修改邮箱
     * @param userMailboxDTO
     * @return
     */
    @PatchMapping("/updateMailbox")
    @ApiOperation("修改邮箱")
    public Result<String> updateMailbox(@Valid @RequestBody UserMailboxDTO userMailboxDTO){
        userService.updateMailbox(userMailboxDTO);
        return Result.success(MessageConstant.OPERATE);
    }

    /**
     * 注册
     * @param userRegisterDTO
     * @return
     */
    @PostMapping("/register")
    @ApiOperation("注册")
    public Result<String> register(@Valid @RequestBody UserRegisterDTO userRegisterDTO){
        log.info("获取到的数据:{}",userRegisterDTO.toString());
        userService.register(userRegisterDTO);
        return Result.success(MessageConstant.REGISTER_SUCCESS);
    }


}
