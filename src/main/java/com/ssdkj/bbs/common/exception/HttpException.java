package com.ssdkj.bbs.common.exception;

import com.ssdkj.bbs.common.enums.ExceptionCodeEnum;
import lombok.Data;

import java.text.MessageFormat;

/**
 * member-http异常
 * @date 2017/01/04 17:20
 */
@Data
public class HttpException extends RuntimeException{
    protected String errorType;
    protected String errorCode;
    protected String errorMsg;
    
    public HttpException(){
        super();
    }

    public HttpException(Throwable throwable){
        super(throwable);
    }

    public HttpException(ExceptionCodeEnum exceptionCode){
        super(exceptionCode.getMsg());
        this.errorType = exceptionCode.getType();
        this.errorCode = exceptionCode.getCode();
        this.errorMsg = exceptionCode.getMsg();
    }

    public HttpException(ExceptionCodeEnum exceptionCode, Object ...objects){
        super(exceptionCode.getMsg());
        this.errorType = exceptionCode.getType();
        this.errorCode = exceptionCode.getCode();
        this.errorMsg = MessageFormat.format(exceptionCode.getMsg(), objects);
    }

    public HttpException(String type, String code, String message){
        super(message);
        this.errorType = type;
        this.errorCode = code;
        this.errorMsg = message;
    }
}
