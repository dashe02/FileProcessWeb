package com.fileprocess.exception;

/**
 * Created with IntelliJ IDEA.
 * User: hadoop
 * Date: 15-1-19
 * Time: 下午3:05
 * To change this template use File | Settings | File Templates.
 */
public class MultipartRequestException extends RuntimeException{
    private static final long serialVersionUID = 7557240487549582620L;

    public MultipartRequestException(String msg) {
        super(msg);
    }

}
