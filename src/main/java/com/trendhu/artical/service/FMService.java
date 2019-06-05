package com.trendhu.artical.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Sort.Direction;

import com.trendhu.artical.bean.FM;
import com.trendhu.common.bean.mongo.Page;
import com.trendhu.common.service.BaseService;

public interface FMService extends BaseService<FM>{
	
	public List<FM> queryFM(Map<String, Object> params, Page<FM> page, String sortKey,
			Direction direction) ;
}