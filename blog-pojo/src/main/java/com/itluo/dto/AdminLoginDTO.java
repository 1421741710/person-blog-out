package com.itluo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author Administrator
 */
@Data
@ApiModel("管理员登录")
public class AdminLoginDTO implements Serializable {

    @ApiModelProperty("管理员账号")
    @NotBlank(message = "账号不能为空")
    @Size(min = 5,max = 20,message = "账号长度5-20个")
    private String username;

    @ApiModelProperty("密码")
    @NotBlank(message = "密码不能为空")
    @Size(min = 5,max = 20,message = "密码长度5-20个")
    private String password;
}
