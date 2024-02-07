package com.itluo.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author Administrator
 */
@Data
public class UserMailboxDTO implements Serializable {

    @NotBlank(message = "邮箱不能为空")
    private String mailbox;

    @NotBlank(message = "验证码不能为空")
    private String captcha;

}
