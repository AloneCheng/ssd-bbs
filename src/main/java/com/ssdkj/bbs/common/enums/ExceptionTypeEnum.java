package com.ssdkj.bbs.common.enums;

import lombok.Getter;

/**
 * 异常类型 用于前端区分返回的异常信息是否需要提示用户、跳转页面
 * @author xiehai1
 */
public enum ExceptionTypeEnum {
    /**
     * 参数校验 接口调试用
     */
    PARAM_VERIFY_ERROR("1"),
    /**
     * 业务异常 关键信息出错(比如验证码验证不通过、用户名密码错误等)
     */
    BUSINESS_ERROR("2"),
    /**
     * token失效或者错误 需要跳转到登录页面
     */
    TOKEN_ERROR("3"),
    /**
     * 系统错误 需要提示用户系统错误
     */
    SYSTEM_ERROR("4");

    @Getter
    private String type;
    ExceptionTypeEnum(String type){
        this.type = type;
    }
}
