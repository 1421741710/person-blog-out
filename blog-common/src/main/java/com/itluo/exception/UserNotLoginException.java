package com.itluo.exception;

/**
 * 用户未登录异常
 * @author Administrator
 */
public class UserNotLoginException extends BaseException {

    public UserNotLoginException() {
    }

    public UserNotLoginException(String msg) {
        super(msg);
    }

}
