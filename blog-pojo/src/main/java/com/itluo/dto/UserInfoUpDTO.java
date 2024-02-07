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
@ApiModel("用户信息修改实体")
public class UserInfoUpDTO implements Serializable {

    @NotNull(message = "id不能为空")
    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("头像")
    private String img;

    @ApiModelProperty("电话")
    private String phone;

    @ApiModelProperty("地址")
    private String address;
}
