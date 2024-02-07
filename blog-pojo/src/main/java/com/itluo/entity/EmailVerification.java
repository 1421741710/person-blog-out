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
public class EmailVerification {

  @ApiModelProperty("主键id")
  private Long id;

  @ApiModelProperty("邮箱")
  private String email;

  @ApiModelProperty("请求次数")
  private Long requestCount;

  @ApiModelProperty("请求时间")
  private LocalDateTime lastRequestTime;

  @ApiModelProperty("状态")
  private Long status;



}
