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
@ApiModel("审核实体类")
public class Process {

  @ApiModelProperty("主键id")
  private Long id;
  @ApiModelProperty("用户id")
  private Long userId;
  @ApiModelProperty("管理员id")
  private Long adminId;
  @ApiModelProperty("审核类型")
  private Long type;
  @ApiModelProperty("审核内容")
  private String content;
  @ApiModelProperty("审核状态")
  private Long status;
  @ApiModelProperty("审核结果")
  private Long result;
  @ApiModelProperty("审核备注")
  private String remark;
  @ApiModelProperty("创建时间")
  private LocalDateTime createTime;
  @ApiModelProperty("修改时间")
  private LocalDateTime updateTime;

}
