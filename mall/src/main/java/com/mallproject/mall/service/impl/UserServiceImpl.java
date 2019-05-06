package com.mallproject.mall.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mallproject.mall.dao.UserDOMapper;
import com.mallproject.mall.dao.UserPasswordDOMapper;
import com.mallproject.mall.dataobject.UserDO;
import com.mallproject.mall.dataobject.UserPasswordDO;
import com.mallproject.mall.error.BusinessException;
import com.mallproject.mall.error.EmBusinessError;
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

	@Override
	@Transactional
	public void register(UserModel userModel) throws BusinessException {
		if(userModel==null) {
			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
		}
		if(StringUtils.isEmpty(userModel.getName())
				|| userModel.getGender() ==null
				|| userModel.getAge() == null
				|| StringUtils.isEmpty(userModel.getTelphone())
				) {
			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
		}
		
		// 实现 UserModel -> UserDO方法
		UserDO userDO = convertFromModel(userModel);
		userDOMapper.insertSelective(userDO);
		
		userModel.setId(userDO.getId());
		UserPasswordDO userPasswordDO = convertPasswordFromModel(userModel);
		
		userPasswordDOMapper.insertSelective(userPasswordDO);
		
		return;
	}

	private UserPasswordDO convertPasswordFromModel(UserModel userModel) {
		if(userModel==null) {
			return null;
		}
		UserPasswordDO userPasswordDO = new UserPasswordDO();
		userPasswordDO.setUserId(userModel.getId());
		userPasswordDO.setEncrptPassword(userModel.getEncrptPassword());
		return userPasswordDO;
	}
	private UserDO convertFromModel(UserModel userModel) {
		if(userModel==null) {
			return null;
		}
		UserDO userDO = new UserDO();
		BeanUtils.copyProperties(userModel, userDO);
		return userDO;
	}
}
