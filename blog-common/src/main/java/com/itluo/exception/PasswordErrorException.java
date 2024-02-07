package com.itluo.exception;

/**
 * 密码错误异常
 * @author Administrator
 */
public class PasswordErrorException extends BaseException {

    public PasswordErrorException() {
    }

    public PasswordErrorException(String msg) {
        super(msg);
    }

}
