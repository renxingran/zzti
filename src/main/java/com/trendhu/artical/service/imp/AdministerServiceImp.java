package com.trendhu.artical.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trendhu.artical.bean.Administer;
import com.trendhu.artical.dao.AdministerDao;
import com.trendhu.artical.service.AdministerService;
import com.trendhu.common.service.imp.BaseServiceImp;


@Service
public class AdministerServiceImp extends BaseServiceImp<Administer>implements AdministerService{
	
	@Autowired
	AdministerDao administerDao;
	
	public AdministerDao getDao() {
		return administerDao;
	}
	
	@Override
	public String login(String admAccount,String admPwd) {
		return administerDao.login(admAccount,admPwd);
	}
}