package com.itluo.controller.admin;

import com.itluo.annotation.LogAnnotation;
import com.itluo.constant.MessageConstant;
import com.itluo.dto.ProcessPageQueryDTO;
import com.itluo.dto.ProcessUpDTO;
import com.itluo.result.PageResult;
import com.itluo.result.Result;
import com.itluo.service.ProcessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/admin/process")
@Api(tags = "审核相关接口")
@Slf4j
public class ProcessController {

    @Autowired
    private ProcessService processService;

    /**
     * 分页查询
     * @param processPageQueryDTO
     * @return
     */
    @GetMapping("/findByProcess")
    @ApiOperation("分页查询")
    public Result<PageResult> pageQuery(ProcessPageQueryDTO processPageQueryDTO){
        PageResult pageResult = processService.pageQuery(processPageQueryDTO);
        return Result.success(MessageConstant.OPERATE,pageResult);
    }

    /**
     * 修改审核状态
     * @param id
     * @return
     */
    @PutMapping("/updateStatus/{id}")
    @ApiOperation("修改审核状态")
    @LogAnnotation(module = "审核",operator = "修改审核状态")
    public Result<String> updateStatus(@PathVariable Long id){
        processService.updateStatus(id);
        return Result.success(MessageConstant.OPERATE);
    }

    /**
     * 提交审核
     * @param processUpDTO
     * @return
     */
    @PatchMapping("/update")
    @ApiOperation("提交审核")
    @LogAnnotation(module = "审核",operator = "提交审核")
    public Result<String> update(@RequestBody ProcessUpDTO processUpDTO){
        long beginTime = System.currentTimeMillis();
        processService.update(processUpDTO);
        //执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        log.info("执行时长:{}",time);
        return Result.success(MessageConstant.OPERATE);
    }


}
