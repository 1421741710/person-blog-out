package com.itluo.exception;

/**
 * 验证码错误异常
 * @author Administrator
 */
public class CaptchaErrorException extends BaseException {

    public CaptchaErrorException() {
    }

    public CaptchaErrorException(String msg) {
        super(msg);
    }

}
