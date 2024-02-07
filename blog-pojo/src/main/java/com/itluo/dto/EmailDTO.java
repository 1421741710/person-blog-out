package com.itluo.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author Administrator
 */
@Data
public class EmailDTO implements Serializable {

    /**
     * 邮箱
     */
    @NotBlank(message = "邮箱不能为空")
    private String mailbox;

    /**
     * 类型:注册/修改邮箱/修改密码
     */
    @NotBlank(message = "类型不能为空")
    private Long type;
}
