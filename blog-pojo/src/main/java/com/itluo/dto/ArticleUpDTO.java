package com.itluo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Administrator
 */
@Data
@ApiModel("文章修改实体")
public class ArticleUpDTO implements Serializable {

    @NotNull(message = "id不能为空")
    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("副标题")
    private String secondary;

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("图片")
    private String img;
}
