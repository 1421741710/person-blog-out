package com.itluo.controller.user;

import com.itluo.constant.MessageConstant;
import com.itluo.result.Result;
import com.itluo.service.FavoritesService;
import com.itluo.vo.FavoritesSelectVO;
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
@RequestMapping("/user/favorites")
@Api(tags = "文章收藏相关接口")
@Slf4j
public class FavoritesController {


    @Autowired
    private FavoritesService favoritesService;

    /**
     * 获取用户收藏夹
     * @return
     */
    @GetMapping("/findByFavorites")
    @ApiOperation("获取用户收藏夹")
    public Result<List<FavoritesSelectVO>> findByFavorites(){
        List<FavoritesSelectVO> favorites = favoritesService.findByFavorites();
        return Result.success(MessageConstant.OPERATE,favorites);
    }

    /**
     * 添加用户收藏夹
     * @param articleId
     * @return
     */
    @PutMapping("/insert/{articleId}")
    @ApiOperation("添加用户收藏夹")
    public Result<String> insert(@PathVariable Long articleId){
        favoritesService.insert(articleId);
        return Result.success(MessageConstant.OPERATE);
    }

    /**
     * 删除用户收藏夹内容
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @ApiOperation("删除用户收藏夹内容")
    public Result<String> delete(@PathVariable Long id){
        favoritesService.delete(id);
        return Result.success(MessageConstant.OPERATE);
    }

}
