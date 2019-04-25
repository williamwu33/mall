package com.mallproject.mall.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.mallproject.mall.error.BusinessException;
import com.mallproject.mall.error.EmBusinessError;
import com.mallproject.mall.response.CommonReturnType;

public class BaseController {
	// 定义一个handlerExccption解决未被cotroller层吸收的exception
		@ExceptionHandler(Exception.class)
		@ResponseStatus(HttpStatus.OK)
		@ResponseBody
		public Object handlerException(HttpServletRequest request,Exception ex) {
			Map<String,Object> responseData = new HashMap<String,Object>();
			if(ex instanceof BusinessException) {
				BusinessException businessException = (BusinessException)ex;
				responseData.put("errCode", businessException.getErrCode());
				responseData.put("errMsg", businessException.getErrMsg());
			}else {
				responseData.put("errCode",EmBusinessError.UNKNOWN_ERROR.getErrCode());
				responseData.put("errMsg", EmBusinessError.UNKNOWN_ERROR.getErrMsg());
			}
			
			CommonReturnType type = CommonReturnType.create(responseData, "fail");
			return type;
			
		}
}
