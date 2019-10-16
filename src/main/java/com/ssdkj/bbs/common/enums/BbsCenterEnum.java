package com.ssdkj.bbs.common.enums;

public enum BbsCenterEnum {

	successes("4000", "操作成功!"), 
	exception_error("4001", "系统忙,请稍后再试!"), 
	request_params_null("4002", "非空参数不能传递空值或者不正确的值！"),
	request_optionValues_null("4003", "可选值不能为空"),
	insert_memberAttr_error("4004", "操作用户失败"),
    delete_memberAttrOptionValue_error("4005", "删除会员属性可选值失败");
	
	private String code;
	private String message;

	private BbsCenterEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
