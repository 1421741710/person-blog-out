package com.itluo.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Administrator
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("管理员登录")
public class AdminLoginVO implements Serializable {

    @ApiModelProperty("管理员id")
    private Long id;

    @ApiModelProperty("管理员账号")
    private String username;

    @ApiModelProperty("token令牌")
    private String token;

    @ApiModelProperty("管理员权限")
    private Long role;
}
