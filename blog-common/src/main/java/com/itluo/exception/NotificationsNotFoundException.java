package com.itluo.exception;

/**
 * 系统通知不存在异常
 * @author Administrator
 */
public class NotificationsNotFoundException extends BaseException{

    public NotificationsNotFoundException(){}

    public NotificationsNotFoundException(String msg){super(msg);}
}
