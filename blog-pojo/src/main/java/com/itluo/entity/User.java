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
@ApiModel("用户实体类")
public class User {


  /**
   * 用户id
   */
  @ApiModelProperty("用户id")
  private Long id;

  /**
   * 用户账号
   */
  @ApiModelProperty("用户账号")
  private String username;

  /**
   * 用户密码
   */
  @ApiModelProperty("用户密码")
  private String password;

  /**
   * 邮箱
   */
  @ApiModelProperty("用户邮箱")
  private String mailbox;

  /**
   * 用户权限
   */
  @ApiModelProperty("用户权限")
  private Long role;

  /**
   * 用户状态 0可用 1锁定
   */
  @ApiModelProperty("用户状态")
  private Long status;

  /**
   * 用户最近登录时间
   */
  @ApiModelProperty("用户最近登录时间")
  private LocalDateTime loginTime;

  /**
   * 创建时间
   */
  @ApiModelProperty("创建时间")
  private LocalDateTime createTime;

  /**
   * 修改时间
   */
  @ApiModelProperty("修改时间")
  private LocalDateTime updateTime;



}
