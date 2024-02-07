package com.itluo.exception;

/**
 * 审核单id不存在异常
 * @author Administrator
 */
public class ProcessIDNotFoundException extends BaseException{

    public ProcessIDNotFoundException(){}

    public ProcessIDNotFoundException(String msg){super(msg);}
}
