package com.itluo.controller.admin;

import com.itluo.annotation.CustomPermission;
import com.itluo.annotation.LogAnnotation;
import com.itluo.constant.MessageConstant;
import com.itluo.dto.UserPageQueryDTO;
import com.itluo.dto.UserUpDTO;
import com.itluo.enumeration.RoleType;
import com.itluo.result.PageResult;
import com.itluo.result.Result;
import com.itluo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Administrator
 */
@RestController("AdminUserController")
@RequestMapping("/admin/user")
@Api(tags = "用户相关接口")
@Slf4j
public class UserController {


    @Autowired
    private UserService userService;

    /**
     * 分页查询
     * @param userPageQueryDTO
     * @return
     */
    @GetMapping("/findByUser")
    @ApiOperation("分页查询")
    @CustomPermission(RoleType.SUPER_ADMINISTRATOR)
    public Result<PageResult> findByUser(UserPageQueryDTO userPageQueryDTO){
        PageResult pageResult = userService.pageQuery(userPageQueryDTO);
        return Result.success(MessageConstant.OPERATE,pageResult);
    }

    /**
     * 修改信息
     * @param userUpDTO
     * @return
     */
    @PatchMapping("/update")
    @ApiOperation("修改信息")
    @LogAnnotation(module = "用户",operator = "修改信息")
    @CustomPermission(RoleType.SUPER_ADMINISTRATOR)
    public Result<String> update(@Valid @RequestBody UserUpDTO userUpDTO){
        userService.update(userUpDTO);
        return Result.success(MessageConstant.OPERATE);
    }

    /**
     * 修改状态
     * @param id
     * @return
     */
    @PutMapping("/update/{id}")
    @ApiOperation("修改状态")
    @LogAnnotation(module = "用户",operator = "修改状态")
    @CustomPermission(RoleType.SUPER_ADMINISTRATOR)
    public Result<String> updateStatus(@PathVariable Long id){
        userService.updateStatus(id);
        return Result.success(MessageConstant.OPERATE);
    }

}
