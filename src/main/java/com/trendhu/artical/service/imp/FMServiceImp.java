package com.trendhu.artical.service.imp;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.trendhu.artical.bean.FM;
import com.trendhu.artical.dao.FMDao;
import com.trendhu.artical.service.FMService;
import com.trendhu.common.bean.mongo.Page;
import com.trendhu.common.service.imp.BaseServiceImp;


@Service
public class FMServiceImp extends BaseServiceImp<FM>implements FMService{
	
	@Autowired
	FMDao fmDao;
	
	public FMDao getDao() {
		return fmDao;
	}

	@Override
	public List<FM> queryFM(Map<String, Object> params, Page<FM> page, String sortKey,
			Direction direction) {
		return fmDao.queryFM(params, page, sortKey, direction);
	}
}