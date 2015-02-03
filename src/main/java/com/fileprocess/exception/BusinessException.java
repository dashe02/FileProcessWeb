package com.fileprocess.exception;

/**
 * Created with IntelliJ IDEA.
 * User: hadoop
 * Date: 15-1-21
 * Time: 下午5:09
 * To change this template use File | Settings | File Templates.
 */
public class BusinessException extends RuntimeException{
    private static final long serialVersionUID = -6569214816469303340L;

    public BusinessException() {
        super();
    }

    public BusinessException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public BusinessException(Throwable throwable) {
        super(throwable);
    }
}
