package com.itluo.controller.admin;

import com.itluo.annotation.LogAnnotation;
import com.itluo.constant.MessageConstant;
import com.itluo.dto.ArticleInDTO;
import com.itluo.dto.ArticlePageQueryDTO;
import com.itluo.dto.ArticleUpDTO;
import com.itluo.result.PageResult;
import com.itluo.result.Result;
import com.itluo.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Administrator
 */
@RestController("AdminArticleController")
@RequestMapping("/admin/article")
@Api(tags = "文章相关接口")
@Slf4j
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 分页查询
     * @param articlePageQueryDTO
     * @return
     */
    @GetMapping("/findByArticle")
    @ApiOperation("分页查询")
    public Result<PageResult> pageQuery(ArticlePageQueryDTO articlePageQueryDTO){
        PageResult pageResult = articleService.pageQuery(articlePageQueryDTO);
        return Result.success(MessageConstant.OPERATE,pageResult);
    }

    /**
     * 修改文章
     * @param articleUpDTO
     * @return
     */
    @PatchMapping("/update")
    @ApiOperation("修改文章")
    @LogAnnotation(module = "文章",operator = "修改文章")
    public Result<String> update(@RequestBody ArticleUpDTO articleUpDTO){
        articleService.update(articleUpDTO);
        return Result.success(MessageConstant.OPERATE);
    }

    /**
     * 添加文章
     * @param articleInDTO
     * @return
     */
    @PostMapping("/insert")
    @ApiOperation("添加文章")
    @LogAnnotation(module = "文章",operator = "添加文章")
    public Result<String> insert(@RequestBody ArticleInDTO articleInDTO){
        articleService.insert(articleInDTO);
        return Result.success(MessageConstant.OPERATE);
    }

    /**
     * 删除文章
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @ApiOperation("删除文章")
    @LogAnnotation(module = "文章",operator = "删除文章")
    public Result<String> delete(@PathVariable Long id){
        articleService.delete(id);
        return Result.success(MessageConstant.OPERATE);
    }


}
