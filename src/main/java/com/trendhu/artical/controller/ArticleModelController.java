package com.trendhu.artical.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.trendhu.artical.bean.Administer;
import com.trendhu.artical.service.AdministerService;
import com.trendhu.common.bean.http.ResponseBean;
import com.trendhu.common.conf.StatusCode;
import com.trendhu.common.controller.imp.RestfullBaseController;
import com.trendhu.common.service.BaseService;

@RestController
@RequestMapping(value = { "/articleModel" })
public class ArticleModelController extends RestfullBaseController<Administer> {

	@Override
	protected BaseService<Administer> getService() {
		// TODO Auto-generated method stub
		return null;
	}

}