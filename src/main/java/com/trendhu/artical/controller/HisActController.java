package com.trendhu.artical.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.trendhu.artical.bean.HisAct;
import com.trendhu.artical.service.HisActService;
import com.trendhu.common.controller.imp.RestfullBaseController;
import com.trendhu.common.service.BaseService;

@RestController
@RequestMapping(value = { "/hisAct" })
public class HisActController extends RestfullBaseController<HisAct> {

	@Autowired
	private HisActService hisActService;
	
	@Override
	protected BaseService<HisAct> getService() {
		return hisActService;
	}
}