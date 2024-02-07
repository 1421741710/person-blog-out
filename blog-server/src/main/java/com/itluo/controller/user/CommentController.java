package com.itluo.controller.user;

import com.itluo.constant.MessageConstant;
import com.itluo.dto.CommentInDTO;
import com.itluo.result.Result;
import com.itluo.service.CommentService;
import com.itluo.vo.CommentListVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/comment")
@Api(tags = "文章评论相关接口")
@Slf4j
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 获取评论
     * @param id
     * @return
     */
    @GetMapping("/findByComment")
    @ApiOperation("获取评论")
    public Result<Map<String,Object>> findByCommentArticleId(Long id){
        Map<String,Object> map = commentService.findByComment(id);
        return Result.success(MessageConstant.OPERATE,map);
    }

    /**
     * 添加评论
     * @param commentInDTO
     * @return
     */
    @PostMapping("/insert")
    @ApiOperation("添加评论")
    public Result<String> insert(@Valid @RequestBody CommentInDTO commentInDTO){
        commentService.insert(commentInDTO);
        return Result.success(MessageConstant.OPERATE);
    }

}
