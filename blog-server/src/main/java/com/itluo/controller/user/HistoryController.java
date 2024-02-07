package com.itluo.controller.user;

import com.itluo.constant.MessageConstant;
import com.itluo.result.Result;
import com.itluo.service.HistoryService;
import com.itluo.vo.HistoryVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/user/history")
@Api(tags = "搜索历史相关接口")
@Slf4j
public class HistoryController {


    @Autowired
    private HistoryService historyService;


    /**
     * 获取搜索历史
     * @return
     */
    @GetMapping("/findByHistory")
    @ApiOperation("获取搜索历史")
    public Result<List<HistoryVO>> findByHistory(){
        List<HistoryVO> history = historyService.findByHistory();
        return Result.success(MessageConstant.OPERATE,history);
    }

    /**
     * 添加搜索历史
     * @param articleId
     * @return
     */
    @PutMapping("/insert/{articleId}")
    @ApiOperation("添加搜索历史")
    public Result<String> insert(@PathVariable Long articleId){
        historyService.insert(articleId);
        return Result.success(MessageConstant.OPERATE);
    }

    /**
     * 删除搜索历史
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @ApiOperation("删除搜索历史")
    public Result<String> delete(@PathVariable Long id){
        historyService.delete(id);
        return Result.success(MessageConstant.OPERATE);
    }


}
