package com.trendhu.artical.service.imp;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import com.trendhu.artical.bean.User;
import com.trendhu.artical.dao.UserDao;
import com.trendhu.artical.service.UserService;
import com.trendhu.common.bean.mongo.Page;
import com.trendhu.common.service.imp.BaseServiceImp;


@Service
public class UserServiceImp extends BaseServiceImp<User>implements UserService{
	
	@Autowired
	UserDao userDao;
	
	public UserDao getDao() {
		return userDao;
	}
	@Override
	public String getExitByPhone(String phone,String email) {
		
		return userDao.getExitByPhone(phone,email);
	}
	@Override
	public String login(String phone, String pwd) {
		return userDao.login(phone,pwd);
	}
	@Override
	public String getUserIdByPhone(String phone) {
		return userDao.getUserIdByPhone(phone);
	}
	@Override
	public String forgetPwd(String phone, String email, String pwd) {
		return userDao.forgetPwd( phone,  email,  pwd);
	}
	@Override
	public Page<User> queryUserLists(Map<String, Object> params, Page<User> page, String sortKey, Direction direction) {
		
		return userDao.queryUserLists(params, page, sortKey,direction);
	}
	@Override
	public List<User> searchUserName(User object) {
		return userDao.searchUserName(object);
	}
	
}