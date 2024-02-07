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
@ApiModel("评论实体类")
public class Comment {

  @ApiModelProperty("主键id")
  private Long id;
  @ApiModelProperty("用户id")
  private Long userId;
  @ApiModelProperty("文章id")
  private Long articleId;
  @ApiModelProperty("评论内容")
  private String comment;
  @ApiModelProperty("创建时间")
  private LocalDateTime createTime;
  @ApiModelProperty("修改时间")
  private LocalDateTime updateTime;


}
