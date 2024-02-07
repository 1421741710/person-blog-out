package com.itluo.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author Administrator
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("文章实体类")
public class Article {

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
  @ApiModelProperty("创建时间")
  private LocalDateTime createTime;
  @ApiModelProperty("修改时间")
  private LocalDateTime updateTime;

}
