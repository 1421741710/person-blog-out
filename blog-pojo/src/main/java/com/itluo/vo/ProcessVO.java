package com.itluo.vo;

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
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProcessVO implements Serializable {

  private Long id;

  private String userName;

  private String adminName;

  private Long type;

  private String content;

  private Long status;

  private Long result;

  private String remark;

  private LocalDateTime createTime;

  private LocalDateTime updateTime;

}
