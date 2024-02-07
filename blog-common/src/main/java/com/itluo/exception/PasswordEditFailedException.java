package com.itluo.exception;

/**
 * 密码修改失败异常
 * @author Administrator
 */
public class PasswordEditFailedException extends BaseException{

    public PasswordEditFailedException(String msg){
        super(msg);
    }

}
