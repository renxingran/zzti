package com.trendhu.artical.service.imp;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.trendhu.artical.bean.ArtRecom;
import com.trendhu.artical.bean.FM;
import com.trendhu.artical.dao.ArtRecomDao;
import com.trendhu.artical.dao.FMDao;
import com.trendhu.artical.service.ArtRecomService;
import com.trendhu.artical.service.FMService;
import com.trendhu.common.bean.mongo.Page;
import com.trendhu.common.service.imp.BaseServiceImp;


@Service
public class ArtRecomServiceImp extends BaseServiceImp<ArtRecom>implements ArtRecomService{
	
	@Autowired
	ArtRecomDao artRecomDao;
	
	public ArtRecomDao getDao() {
		return artRecomDao;
	}

	@Override
	public ArtRecom queryArtLists(ArtRecom object) {
    	return this.getDao().queryArtLists(object);
	}

}