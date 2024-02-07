package com.itluo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author Administrator
 */
@Data
@ApiModel("用户注册")
public class UserRegisterDTO implements Serializable {

    @ApiModelProperty("账号")
    @NotBlank(message = "账号不能为空")
    @Size(min = 5,max = 20,message = "账号长度5-20个字符")
    private String username;

    @ApiModelProperty("密码")
    @NotBlank(message = "密码不能为空")
    @Size(min = 5,max = 20,message = "密码长度5-20个字符")
    private String password;

    @ApiModelProperty("邮箱")
    @Email(message = "请输入有效的电子邮箱")
    private String mailbox;

    @ApiModelProperty("验证码")
    @NotBlank(message = "验证码不能为空")
    @Size(min = 5,max = 5,message = "验证码长度必须为5")
    private String verifyCode;

}
