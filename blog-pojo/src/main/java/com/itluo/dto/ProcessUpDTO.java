package com.itluo.dto;

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
@ApiModel("修改审核")
public class ProcessUpDTO implements Serializable {

  @ApiModelProperty("主键id")
  private Long id;

  @ApiModelProperty("审核结果")
  private Long result;

  @ApiModelProperty("审核备注")
  private String remark;

}
