package com.itluo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Administrator
 */
@Data
@ApiModel("发送系统通知")
public class NotificationsInDTO implements Serializable {

    @ApiModelProperty("类型")
    @NotNull(message = "类型不能为空")
    private Long type;

    @ApiModelProperty("通知内容")
    @NotBlank(message = "通知内容不能为空")
    private String content;

}
