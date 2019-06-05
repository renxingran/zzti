package com.trendhu.artical.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trendhu.artical.bean.HisAct;
import com.trendhu.artical.dao.HisActDao;
import com.trendhu.artical.service.HisActService;
import com.trendhu.common.service.imp.BaseServiceImp;


@Service
public class HisActServiceImp extends BaseServiceImp<HisAct>implements HisActService{
	
	@Autowired
	HisActDao hisActDao;
	
	public HisActDao getDao() {
		return hisActDao;
	}
}