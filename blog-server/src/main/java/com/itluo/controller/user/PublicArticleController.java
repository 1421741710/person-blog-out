package com.itluo.controller.user;

import com.itluo.constant.MessageConstant;
import com.itluo.result.Result;
import com.itluo.service.ArticleService;
import com.itluo.vo.ArticleLoginVO;
import com.itluo.vo.ArticleVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 未登录的用户
 * @author Administrator
 */
@RestController
@RequestMapping("/article")
@Api(tags = "公共文章相关接口")
@Slf4j
public class PublicArticleController {


    @Autowired
    private ArticleService articleService;

    /**
     * 获取文章
     * @return
     */
    @GetMapping("/findByArticle")
    @ApiOperation("获取文章")
    public Result<List<ArticleVO>> findByArticle(){
        List<ArticleVO> article = articleService.findByPublicArticle();
        return Result.success(MessageConstant.OPERATE,article);
    }

    /**
     * 模糊查询文章标题
     * @param title
     * @return
     */
    @GetMapping("/searchArticle")
    @ApiOperation("模糊查询文章标题")
    public Result searchArticle(@Valid @NotBlank(message = "搜索内容不能为空") String title){
        List<ArticleVO> article = articleService.findByPublicSearchArticle(title);
        return Result.success(MessageConstant.OPERATE,article);
    }

    /**
     * 根据id获取文章
     * @param id
     * @return
     */
    @GetMapping("/findByArticleId")
    @ApiOperation("根据id获取文章")
    public Result<ArticleVO> findByArticleId(Long id){
        ArticleVO article = articleService.findByPublicArticleId(id);
        return Result.success(MessageConstant.OPERATE,article);
    }

}
