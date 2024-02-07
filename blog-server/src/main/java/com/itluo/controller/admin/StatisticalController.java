package com.itluo.controller.admin;

import com.itluo.annotation.CustomPermission;
import com.itluo.constant.MessageConstant;
import com.itluo.enumeration.RoleType;
import com.itluo.result.Result;
import com.itluo.service.StatisticalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/admin/statistical ")
@Api(tags = "统计分析")
@Slf4j
public class StatisticalController {


    @Autowired
    private StatisticalService statisticalService;


    /**
     * 统计分析
     * @return
     */
    @GetMapping("/findByStatistical")
    @ApiOperation("统计分析")
    public Result findBystatistical(){
        Map<String,Object> map = statisticalService.findByStatistical();
        return Result.success(MessageConstant.OPERATE,map);
    }




}
