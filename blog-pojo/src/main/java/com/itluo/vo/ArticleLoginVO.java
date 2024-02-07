package com.itluo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Administrator
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("文章")
public class ArticleLoginVO implements Serializable {

    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("文章标题")
    private String title;

    @ApiModelProperty("文章副标题")
    private String secondary;

    @ApiModelProperty("文章内容")
    private String content;

    @ApiModelProperty("文章图片")
    private String img;

    @ApiModelProperty("文章点赞")
    private Long likes;

    @ApiModelProperty("文章收藏")
    private Long favorite;

    @ApiModelProperty("文章点赞标识符")
    private Long likeTremple;

    @ApiModelProperty("文章收藏标识符")
    private Long favorites;

    @ApiModelProperty("文章创建时间")
    private LocalDateTime createTime;

}
