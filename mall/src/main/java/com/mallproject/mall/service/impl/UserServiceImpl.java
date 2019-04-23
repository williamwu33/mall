package com.mallproject.mall.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mallproject.mall.dao.UserDOMapper;
import com.mallproject.mall.dao.UserPasswordDOMapper;
import com.mallproject.mall.dataobject.UserDO;
import com.mallproject.mall.dataobject.UserPasswordDO;
import com.mallproject.mall.service.UserService;
import com.mallproject.mall.service.model.UserModel;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserDOMapper userDOMapper;

	@Autowired
	private UserPasswordDOMapper userPasswordDOMapper;
	@Override
	public UserModel getUserById(Integer id) {
		// 调用userDOMapper获取用户dataobject
		UserDO userDO = userDOMapper.selectByPrimaryKey(id);
		if(userDO==null) {
			return null;
		}
		// 通过用户Id获取用户加密密码信息
		UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(userDO.getId());
		
		return convertFromDataObject(userDO, userPasswordDO);
	}
	
	private UserModel convertFromDataObject(UserDO userDO,UserPasswordDO userPasswordDO) {
		if(userDO==null) {
			return null;
		}
		UserModel userModel = new UserModel();
		BeanUtils.copyProperties(userDO,userModel);
		
		
		if(userPasswordDO!=null) {
			userModel.setEncrptPassword(userPasswordDO.getEncrptPassword());
		}
		
		return userModel;
		
	}

}
