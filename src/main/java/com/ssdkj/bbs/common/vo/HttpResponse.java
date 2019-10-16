package com.ssdkj.bbs.common.vo;

//import com.gome.o2m.member.commom.dubbo.Response;


import com.ssdkj.bbs.common.dto.Response;
import com.ssdkj.bbs.common.enums.ExceptionCodeEnum;
import com.ssdkj.bbs.common.enums.ExceptionTypeEnum;
import com.ssdkj.bbs.common.exception.HttpException;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.text.MessageFormat;

/**
 * http 应答信息实体
 */
@Builder
@ToString
@Getter
@Setter
public class HttpResponse<T> extends BaseVo{
    private T result;
    private boolean isSuccess;
    private String errorType;
    private String errorCode;
    private String errorMsg; 
    private String token;

    public static HttpResponse ok() {
        return HttpResponse.builder()
                .isSuccess(true)
                .build();
    }

    public static <T> HttpResponse ok(T result) {
        return HttpResponse.builder()
                .result(result)
                .isSuccess(true)
                .build();
    }
    public static <T> HttpResponse ok(T result, String token) {
        return HttpResponse.builder()
                .result(result)
                .isSuccess(true)
                .token(token)
                .build();
    }

    public static HttpResponse fail(String errorType, String errorCode, String errorMsg) {
        return HttpResponse.builder()
                .errorType(errorType)
                .errorCode(errorCode)
                .errorMsg(errorMsg)
                .isSuccess(false)
                .build();
    }

    public static <T> HttpResponse fail(T result, String errorType, String errorCode, String errorMsg) {
        return HttpResponse.builder()
                .result(result)
                .errorType(errorType)
                .errorCode(errorCode)
                .errorMsg(errorMsg)
                .isSuccess(false)
                .build();
    }

    public static HttpResponse fail(HttpException e){
        return HttpResponse.builder()
                .errorType(e.getErrorType())
                .errorCode(e.getErrorCode())
                .errorMsg(e.getErrorMsg())
                .isSuccess(false)
                .build();
    }

    public static HttpResponse fail(HttpException code, Object ...objects){
        return HttpResponse.builder()
                .errorType(code.getErrorType())
                .errorCode(code.getErrorCode())
                .errorMsg(MessageFormat.format(code.getErrorMsg(), objects))
                .isSuccess(false)
                .build();
    }

    public static HttpResponse fail(ExceptionCodeEnum exceptionCodeEnum, Object ...objects){
        return HttpResponse.builder()
                .errorType(exceptionCodeEnum.getType())
                .errorCode(exceptionCodeEnum.getCode())
                .errorCode(MessageFormat.format(exceptionCodeEnum.getMsg(), objects))
                .isSuccess(false)
                .build();
    }

    public static HttpResponse convert(Response response){
        return response.isOk()
                ? ok(response.getResult())
                : fail(ExceptionTypeEnum.BUSINESS_ERROR.getType(), response.getError(), response.getErrorMessage());
    }
}
