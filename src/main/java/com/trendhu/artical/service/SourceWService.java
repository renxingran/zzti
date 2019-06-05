package com.trendhu.artical.service;

import java.util.Map;

import org.springframework.data.domain.Sort.Direction;

import com.trendhu.artical.bean.ArtF;
import com.trendhu.artical.bean.SourceW;
import com.trendhu.common.bean.mongo.Page;
import com.trendhu.common.service.BaseService;

public interface SourceWService extends BaseService<SourceW>{

	    public Page<SourceW> query(Map<String, Object> params,
                         Page<SourceW> page,
                         String sortKey,
                         Direction direction);
	    
	    public Page<SourceW> query(Map<String, Object> params,
                Page<SourceW> page) ;

		public String findSourceId(String sourceName);
		
		public String getSourceNameByArtId(String artId);
		
		public String getSourceImgByArtId(String artId);
		
	    public Page<SourceW> querySourceWInfoLists(Map<String, Object> params,
	            Page<SourceW> page,
	            String sortKey,
	            Direction direction);		
	
}