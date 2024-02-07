package com.itluo.exception;

/**
 * 业务异常
 * @author Administrator
 */
public class BaseException extends RuntimeException {

    public BaseException() {
    }

    public BaseException(String msg) {
        super(msg);
    }

}
