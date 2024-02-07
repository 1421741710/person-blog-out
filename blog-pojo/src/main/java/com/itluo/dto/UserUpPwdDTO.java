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
@ApiModel("用户修改密码")
public class UserUpPwdDTO implements Serializable {

    @ApiModelProperty("邮箱")
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式如:admin@qq.com")
    private String mailbox;

    @ApiModelProperty("密码")
    @NotBlank(message = "密码不能为空")
    @Size(min = 5,max = 20,message = "密码长度5-20个")
    private String password;

    @ApiModelProperty("验证码")
    @NotBlank(message = "验证码不能为空")
    @Size(min = 5,max = 5,message = "验证码长度5个")
    private String captcha;

}
