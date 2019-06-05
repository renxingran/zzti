package com.trendhu.artical.dao;


import com.trendhu.artical.bean.Administer;
import com.trendhu.common.dao.mongo.BaseDao;


public interface AdministerDao  extends BaseDao<Administer>{
	
	public String login(String admAccount,String admPwd) ;
}