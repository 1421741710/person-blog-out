package com.itluo.exception;


/**
 * 账户也存在
 * @author Administrator
 */
public class AccountExistsException extends BaseException{

    public AccountExistsException(){}

    public AccountExistsException(String msg){super(msg);}
}
