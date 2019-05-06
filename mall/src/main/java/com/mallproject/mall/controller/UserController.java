package com.mallproject.mall.controller;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.mallproject.mall.controller.viewobject.UserVO;
import com.mallproject.mall.error.BusinessException;
import com.mallproject.mall.error.EmBusinessError;
import com.mallproject.mall.response.CommonReturnType;
import com.mallproject.mall.service.UserService;
import com.mallproject.mall.service.model.UserModel;
import com.mysql.jdbc.util.Base64Decoder;

/**
 * 
 * @author Administrator
 *
 */
@Controller("user")
@RequestMapping("/user")
@CrossOrigin(allowCredentials="true",allowedHeaders="*")
public class UserController extends BaseController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private HttpServletRequest httpServletRequest;
	
	
	// 用户注册接口
	@RequestMapping(value="/register",method= {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
	@ResponseBody
	public CommonReturnType register(@RequestParam(name="telphone") String telphone
			,@RequestParam(name="otpCode") String otpCode
			,@RequestParam(name="name") String name
			,@RequestParam(name="gender") Integer gender
			,@RequestParam(name="age") Integer age
			,@RequestParam(name="password") String password
			) throws BusinessException, NoSuchAlgorithmException, UnsupportedEncodingException {
		
		// 验证手机号和对应的otpCode相符合
		String inSessionOtpCode = (String) this.httpServletRequest.getSession().getAttribute(telphone);
		if(!StringUtils.equals(otpCode, inSessionOtpCode)) {
			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "短信验证码错误");
		}
		// 用户的注册流程
		UserModel userModel = new UserModel();
		userModel.setName(name);
		userModel.setAge(age);
		userModel.setGender(new Byte(String.valueOf(gender.intValue())));
		userModel.setTelphone(telphone);
		userModel.setRegisterMode("byPhone");
		userModel.setEncrptPassword(EncodeByMd5(password));
		
		userService.register(userModel);
		
		return CommonReturnType.create(null);
	}
	
	public String EncodeByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		// 确定计算方法
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		md5.digest(str.getBytes("utf-8")).toString();
		
		return md5.digest(str.getBytes("utf-8")).toString();
	}
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
