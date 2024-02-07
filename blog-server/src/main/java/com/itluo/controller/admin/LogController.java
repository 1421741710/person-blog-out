package com.itluo.controller.admin;

import com.itluo.annotation.CustomPermission;
import com.itluo.constant.MessageConstant;
import com.itluo.dto.LogPageQueryDTO;
import com.itluo.enumeration.RoleType;
import com.itluo.result.PageResult;
import com.itluo.result.Result;
import com.itluo.service.LogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/admin/log")
@Api(tags = "日志相关接口")
@Slf4j
public class LogController {


    @Autowired
    private LogService logService;


    /**
     * 分页查询
     * @param logPageQueryDTO
     * @return
     */
    @GetMapping("/findByLogPage")
    @ApiOperation("分页查询")
    @CustomPermission(RoleType.SUPER_ADMINISTRATOR)
    public Result<PageResult> findByLogPage(LogPageQueryDTO logPageQueryDTO){
        PageResult pageResult = logService.pageQuery(logPageQueryDTO);
        return Result.success(MessageConstant.OPERATE,pageResult);
    }

}
