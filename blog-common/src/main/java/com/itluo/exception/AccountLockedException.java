package com.itluo.exception;

/**
 * 账号被锁定异常
 * @author Administrator
 */
public class AccountLockedException extends BaseException {

    public AccountLockedException() {
    }

    public AccountLockedException(String msg) {
        super(msg);
    }

}
