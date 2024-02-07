package com.itluo.exception;

/**
 * 账号不存在异常
 * @author Administrator
 */
public class AccountNotFoundException extends BaseException {

    public AccountNotFoundException() {
    }

    public AccountNotFoundException(String msg) {
        super(msg);
    }

}
