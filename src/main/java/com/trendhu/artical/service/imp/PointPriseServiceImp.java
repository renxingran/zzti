package com.trendhu.artical.service.imp;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.trendhu.artical.bean.ArtF;
import com.trendhu.artical.bean.PointPrise;
import com.trendhu.artical.dao.PointPriseDao;
import com.trendhu.artical.service.PointPriseService;
import com.trendhu.common.bean.mongo.Page;
import com.trendhu.common.service.imp.BaseServiceImp;


@Service
public class PointPriseServiceImp extends BaseServiceImp<PointPrise>implements PointPriseService{
	
	@Autowired
	PointPriseDao pointPriseDao;
	
	public PointPriseDao getDao() {
		return pointPriseDao;
	}
	
	public Boolean getPointPriseSta(String artId,String userId) {
    	return this.getDao().getPointPriseSta(artId,userId);
    }

	@Override
	public PointPrise addPointPrise(PointPrise object) {
		return this.getDao().addPointPrise(object);
	}

	@Override
	public PointPrise canclePointPrise(PointPrise object) {
		return this.getDao().canclePointPrise(object);
	}

	@Override
	public List<PointPrise> queryMyPointPriseList(Map<String, Object> params, Page<PointPrise> page, String sortKey,
			Direction direction) {
		return this.getDao().queryMyPointPriseList(params, page, sortKey,direction);
	}
	
	
}