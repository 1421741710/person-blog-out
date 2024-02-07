package com.itluo.vo;

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
public class UserInfoNameVO implements Serializable {

    @ApiModelProperty("主键id")
    private Long id;
    @ApiModelProperty("用户账号")
    private String username;
    @ApiModelProperty("用户名称")
    private String name;
    @ApiModelProperty("用户头像")
    private String img;
    @ApiModelProperty("用户电话")
    private String phone;
    @ApiModelProperty("用户地址")
    private String address;
    @ApiModelProperty("状态")
    private Long status;
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;
    @ApiModelProperty("修改时间")
    private LocalDateTime updateTime;

}
