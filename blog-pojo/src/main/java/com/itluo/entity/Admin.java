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
@ApiModel("管理员实体类")
public class Admin {

  @ApiModelProperty("主键id")
  private Long id;
  @ApiModelProperty("用户名")
  private String username;
  @ApiModelProperty("密码")
  private String password;
  @ApiModelProperty("最近登录地址")
  private String adminIp;
  @ApiModelProperty("权限")
  private Long role;
  @ApiModelProperty("状态")
  private Long status;
  @ApiModelProperty("最近登录时间")
  private LocalDateTime loginTime;
  @ApiModelProperty("创建时间")
  private LocalDateTime createTime;
  @ApiModelProperty("修改时间")
  private LocalDateTime updateTime;

}
