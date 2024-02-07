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
@ApiModel("日志实体类")
public class Log {

  @ApiModelProperty("主键id")
  private Long id;
  @ApiModelProperty("管理员账号")
  private String adminName;
  @ApiModelProperty("模块")
  private String module;
  @ApiModelProperty("操作")
  private String operator;
  @ApiModelProperty("请求方法名")
  private String className;
  @ApiModelProperty("请求参数")
  private String params;
  @ApiModelProperty("请求ip")
  private String ip;
  @ApiModelProperty("消耗")
  private Long executionTime;
  @ApiModelProperty("创建时间")
  private LocalDateTime createTime;
  @ApiModelProperty("修改时间")
  private LocalDateTime updateTime;

}
