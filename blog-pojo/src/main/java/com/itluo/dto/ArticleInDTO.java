package com.itluo.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author Administrator
 */
@Data
public class ArticleInDTO implements Serializable {

    @NotBlank(message = "标题不能为空")
    private String title;

    @NotBlank(message = "副标题不能为空")
    private String secondary;

    @NotBlank(message = "内容不能为空")
    private String content;

    @NotBlank(message = "图片地址不能为空")
    private String img;

}
