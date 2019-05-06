package com.mallproject.mall.service;

import com.mallproject.mall.error.BusinessException;
import com.mallproject.mall.service.model.UserModel;

public interface UserService {

	UserModel getUserById(Integer id);
	
	void register(UserModel userModel) throws BusinessException;
}
