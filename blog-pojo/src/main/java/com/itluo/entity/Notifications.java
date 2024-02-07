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
@ApiModel("系统通知实体类")
public class Notifications {

  @ApiModelProperty("主键id")
  private Long id;
  @ApiModelProperty("用户id")
  private Long userId;
  @ApiModelProperty("通知类型")
  private Long type;
  @ApiModelProperty("内容")
  private String content;
  @ApiModelProperty("创建时间")
  private LocalDateTime createTime;
  @ApiModelProperty("修改时间")
  private LocalDateTime updateTime;
  @ApiModelProperty("状态")
  private Long status;

}
