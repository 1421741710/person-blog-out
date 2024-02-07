package com.itluo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author Administrator
 */
@Data
@ApiModel("用户修改实体类")
public class UserUpDTO implements Serializable {


    @NotNull(message = "id不能为空")
    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("邮箱")
    private String mailbox;

}
