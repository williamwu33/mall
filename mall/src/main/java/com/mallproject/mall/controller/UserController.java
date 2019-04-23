package com.mallproject.mall.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mallproject.mall.controller.viewobject.UserVO;
import com.mallproject.mall.service.UserService;
import com.mallproject.mall.service.model.UserModel;

/**
 * 
 * @author Administrator
 *
 */
@Controller("user")
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/get")
	@ResponseBody
	public UserVO getUser(@RequestParam(name ="id") Integer id) {
		//调用service服务获取对应ID的用户对象并返回给前端
		
		UserModel userModel = userService.getUserById(id);
		// 将核心领域模型用户对象转化为供UI使用的viewObject
		return convertFromModel(userModel);
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
