package com.ssdkj.bbs.common.enums;

import lombok.Getter;
import org.springframework.util.StringUtils;

/**
 * 异常类型 用于前端区分返回的异常信息是否需要提示用户、跳转页面
 * @date 2017/01/04 16:29
 */
@Getter
public enum ExceptionCodeEnum {
    PARAM_REQUIRED(ExceptionTypeEnum.PARAM_VERIFY_ERROR, "param.required", "参数{0}不能为空"),
    PARAM_LEN_RANGE_ERROR(ExceptionTypeEnum.PARAM_VERIFY_ERROR, "param.range.error", "参数{0}长度在[{1}, {2}]之间"),
    PARAM_LEN_MAX_ERROR(ExceptionTypeEnum.PARAM_VERIFY_ERROR, "param.max.error", "参数{0}最长限制为{1}"),
    PARAM_LEN_MIN_ERROR(ExceptionTypeEnum.PARAM_VERIFY_ERROR, "param.min.error", "参数{0}最短限制为{1}"),
    PARAM_FORMAT_ERROR(ExceptionTypeEnum.PARAM_VERIFY_ERROR, "param.format.error", "参数{0}格式错误, 格式应为{1}"),
    PARAM_EXPECTED_ERROR(ExceptionTypeEnum.PARAM_VERIFY_ERROR, "param.expected.error", "参数{0}与预期值不符"),
    PARAM_ERROR(ExceptionTypeEnum.PARAM_VERIFY_ERROR, "param.error", "参数错误"),

    TOKEN_INVALID(ExceptionTypeEnum.TOKEN_ERROR, "token.invalid", "TOKEN无效"),
    PARAM_TOKEN_INVALID(ExceptionTypeEnum.BUSINESS_ERROR, "param.token.invalid", "TOKEN无效"),
    TOKEN_TIME_OUT(ExceptionTypeEnum.TOKEN_ERROR, "token.time.out", "长时间未操作,请重新登录"),
    VERIFY_CODE_ERROR(ExceptionTypeEnum.BUSINESS_ERROR,"verify.code.error","验证码错误"),
    MODILE_IDNO_ERROR(ExceptionTypeEnum.BUSINESS_ERROR,"no.match.error","手机号和身份证号码不匹配!"),
    SYSTEM_ERROR(ExceptionTypeEnum.SYSTEM_ERROR, "system.error", "系统错误");
    /**
     * 异常类型
     */
    private String type;
    /**
     * 异常代码
     */
    private String code;
    /**
     * 异常信息
     */
    private String msg;

    ExceptionCodeEnum(ExceptionTypeEnum type, String code, String msg) {
        this.type = type.getType();
        this.code = code;
        this.msg = msg;
    }

    public static ExceptionCodeEnum getEnumByCode(String code){
        if(!StringUtils.isEmpty(code)){
            for(ExceptionCodeEnum codeEnum : ExceptionCodeEnum.values()){
                if(codeEnum.getCode().equals(code.trim())){
                    return codeEnum;
                }
            }
        }
        return null;
    }
}
