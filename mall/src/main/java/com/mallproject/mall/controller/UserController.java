package com.mallproject.mall.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.mallproject.mall.controller.viewobject.UserVO;
import com.mallproject.mall.error.BusinessException;
import com.mallproject.mall.error.EmBusinessError;
import com.mallproject.mall.response.CommonReturnType;
import com.mallproject.mall.service.UserService;
import com.mallproject.mall.service.model.UserModel;

/**
 * 
 * @author Administrator
 *
 */
@Controller("user")
@RequestMapping("/user")
@CrossOrigin
public class UserController extends BaseController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private HttpServletRequest httpServletRequest;
	
	
	// 用户获取otp短信接口
	@RequestMapping(value="/getotp",method= {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
	@ResponseBody
	public CommonReturnType getOtp(@RequestParam(name="telphone") String telphone) {
		// 需要按照一定的规则生成Otp验证码
		Random random = new Random();
		int randomInt = random.nextInt(99999);
		randomInt+=10000;
		String otpCode = String.valueOf(randomInt);
		
		// 将OTP验证码同用户手机号相关联,使用httpsession的方式绑定他的手机号和otpCode
		httpServletRequest.getSession().setAttribute(telphone, otpCode);
		
		// 将验证码通过手机短信通道发送给用户,省略
		System.out.println("telphone = "+telphone+"& otpCode = "+otpCode);
		
		
		return CommonReturnType.create(null);
		
	}
	
	
	@RequestMapping("/get")
	@ResponseBody
	public CommonReturnType getUser(@RequestParam(name ="id") Integer id) throws BusinessException {
		//调用service服务获取对应ID的用户对象并返回给前端
		
		UserModel userModel = userService.getUserById(id);
		
		if(userModel==null) {
			throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
		}
		
		// 将核心领域模型用户对象转化为供UI使用的viewObject
		UserVO userVO = convertFromModel(userModel);
		
		//返回通用对象
		return CommonReturnType.create(userVO);
	}

	private UserVO convertFromModel(UserModel userModel) {
		if(userModel==null) {
			return null;
			
		}
		UserVO userVO = new UserVO();
		BeanUtils.copyProperties(userModel, userVO);
		return userVO;
		
	}
	
}
