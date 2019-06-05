package com.trendhu.artical.dao;


import java.util.Map;

import org.springframework.data.domain.Sort.Direction;

import com.trendhu.artical.bean.SourceW;
import com.trendhu.common.bean.mongo.Page;
import com.trendhu.common.dao.mongo.BaseDao;


public interface SourceWDao  extends BaseDao<SourceW>{
    public Page<SourceW> query(Map<String, Object> params,
            Page<SourceW> page,
            String sortKey);

	public String  findSourceId(String sourceName);
	
	public String getSourceNameByArtId(String artId);
	
	public String getSourceImgByArtId(String artId) ;
	
	public Page<SourceW> querySourceWInfoLists(Map<String, Object> params, Page<SourceW> page, String sortKey,
			Direction direction) ;
}