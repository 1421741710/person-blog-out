package com.itluo.exception;

/**
 * 文章id不存在异常
 * @author Administrator
 */
public class ArticleIDNotFoundException extends BaseException{

    public ArticleIDNotFoundException(){}

    public ArticleIDNotFoundException(String msg){super(msg);}
}
