package com.mallproject.mall.error;
// 包装业务异常实现类
public class BusinessException extends Exception implements CommonError{

	private CommonError commonError;
	
	public BusinessException(CommonError commonError) {
		super();
		this.commonError = commonError;
	}
	// 接收自定义的errmsg的方式构造业务异常
	public BusinessException(CommonError commonError,String errMsg) {
		super();
		this.commonError = commonError;
		this.setErrMsg(errMsg);
	}
	@Override
	public int getErrCode() {
		return this.commonError.getErrCode();
	}

	@Override
	public String getErrMsg() {
		return this.commonError.getErrMsg();
	}

	@Override
	public CommonError setErrMsg(String errMsg) {
		this.commonError.setErrMsg(errMsg);
		return this;
	}

}
