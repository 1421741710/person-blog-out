package com.itluo.exception;

/**
 * @author Administrator
 */
public class InsufficientPermissionsException extends BaseException{

    public InsufficientPermissionsException(){}

    public InsufficientPermissionsException(String msg){
        super(msg);
    }
}
