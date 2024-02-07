package com.itluo.exception;

/**
 * 不允许删除异常
 * @author Administrator
 */
public class DeletionNotAllowedException extends BaseException {

    public DeletionNotAllowedException(String msg) {
        super(msg);
    }

}
