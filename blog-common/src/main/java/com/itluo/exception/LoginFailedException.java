package com.itluo.exception;

/**
 * 登录失败
 * @author Administrator
 */
public class LoginFailedException extends BaseException{
    public LoginFailedException(String msg){
        super(msg);
    }
}
