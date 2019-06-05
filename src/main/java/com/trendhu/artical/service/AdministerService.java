package com.trendhu.artical.service;

import com.trendhu.artical.bean.Administer;
import com.trendhu.common.service.BaseService;

public interface AdministerService extends BaseService<Administer>{
	
	public String login(String admAccount,String admPwd);
	
}