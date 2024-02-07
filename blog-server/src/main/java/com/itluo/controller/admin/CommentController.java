package com.itluo.controller.admin;

import com.itluo.annotation.LogAnnotation;
import com.itluo.constant.MessageConstant;
import com.itluo.dto.CommentPageQueryDTO;
import com.itluo.result.PageResult;
import com.itluo.result.Result;
import com.itluo.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Administrator
 */
@RestController("AmdinCommentController")
@RequestMapping("/admin/comment")
@Api(tags = "评论相关接口")
@Slf4j
public class CommentController {

    @Autowired
    private CommentService commentService;


    /**
     * 分页查询
     * @param commentPageQueryDTO
     * @return
     */
    @GetMapping("/findByComment")
    @ApiOperation("分页查询")
    public Result<PageResult> findByComment(CommentPageQueryDTO commentPageQueryDTO){
        PageResult pageResult = commentService.pageQuery(commentPageQueryDTO);
        return Result.success(MessageConstant.OPERATE,pageResult);
    }

    /**
     * 删除评论
     * @param id
     * @return
     */
    @GetMapping("/delete/{id}")
    @ApiOperation("删除评论")
    @LogAnnotation(module = "评论",operator = "删除评论")
    public Result<String> delete(@PathVariable List<Long> id){
        commentService.delete(id);
        return Result.success(MessageConstant.OPERATE);
    }

}
