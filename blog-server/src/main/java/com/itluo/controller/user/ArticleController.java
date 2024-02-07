package com.itluo.controller.user;

import com.itluo.constant.MessageConstant;
import com.itluo.result.Result;
import com.itluo.service.ArticleService;
import com.itluo.vo.ArticleLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 登录的用户
 * @author Administrator
 */
@RestController
@RequestMapping("/user/article")
@Api(tags = "文章相关接口")
@Slf4j
public class ArticleController {


    @Autowired
    private ArticleService articleService;

    /**
     * 获取文章
     * @return
     */
    @GetMapping("/findByArticle")
    @ApiOperation("获取文章")
    public Result<List<ArticleLoginVO>> findByArticleUserId(){
        List<ArticleLoginVO> articleLogin = articleService.findByArticleUserId();
        return Result.success(MessageConstant.OPERATE,articleLogin);
    }

    /**
     * 根据id获取文章
     * @param id
     * @return
     */
    @GetMapping("/findByArticleId")
    @ApiOperation("根据id获取文章")
    public Result<ArticleLoginVO> findByArticleId(Long id){
        long beginTime = System.currentTimeMillis();
        ArticleLoginVO articleLoginVO = articleService.findByArticleId(id);
        //执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        log.info("执行时长:{}",time);
        return Result.success(MessageConstant.OPERATE,articleLoginVO);
    }

}
