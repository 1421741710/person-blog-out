package com.itluo.controller.user;

import com.itluo.constant.MessageConstant;
import com.itluo.dto.ArticleUserUpDTO;
import com.itluo.result.Result;
import com.itluo.service.ArticleUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

/**
 * 点赞/踩/无
 * @author Administrator
 */
@RestController
@RequestMapping("/user/articleUser")
@Api(tags = "文章点赞相关接口")
@Slf4j
public class ArticleUserController {


    @Autowired
    private ArticleUserService articleUserService;

    /**
     * 修改文章点赞/踩/无
     * @param articleUserUpDTO
     * @return
     */
    @PatchMapping("/LikeTrempl")
    @ApiOperation("修改文章点赞/踩/无")
    public Result<Long> likeTrempl(@RequestBody ArticleUserUpDTO articleUserUpDTO){
        Long likes = articleUserService.likeTrempl(articleUserUpDTO);
        return Result.success(MessageConstant.OPERATE,likes);
    }

}
