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
@ApiModel("私信实体类")
public class Message {

  @ApiModelProperty("主键id")
  private Long id;
  @ApiModelProperty("发送人用户id")
  private Long senderUserId;
  @ApiModelProperty("接收人用户id")
  private Long recipientUserId;
  @ApiModelProperty("内容")
  private String content;
  @ApiModelProperty("状态")
  private Long state;
  @ApiModelProperty("创建时间")
  private LocalDateTime createTime;
  @ApiModelProperty("修改时间")
  private LocalDateTime updateTime;

}
