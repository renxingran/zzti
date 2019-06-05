package com.trendhu.artical.dao;


import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Sort.Direction;

import com.mongodb.client.result.UpdateResult;
import com.trendhu.artical.bean.ArtF;
import com.trendhu.artical.bean.ArtRecom;
import com.trendhu.artical.bean.PointPrise;
import com.trendhu.common.bean.mongo.Page;
import com.trendhu.common.dao.mongo.BaseDao;


public interface ArtFDao  extends BaseDao<ArtF>{
	 public Page<ArtF> query(Map<String, Object> params,
             Page<ArtF> page,
             String sortKey) ;
	
//	 public Page<ArtRecom> queryArtLists(Map<String, Object> params,
//             Page<ArtRecom> page,
//             String sortKey,
//             Direction direction);
	
	 public Long getArtReleaseCount(String sourceName);
	 
	 public String examineArtPass(String id);
	 
	 public Page<ArtF> queryArtInfoLists(Map<String, Object> params,
             Page<ArtF> page,
             String sortKey,
             Direction direction); 
	 
		public List<ArtF> queryKeyWordsLists(String keyWords);
		
		public List<ArtF> searchNewsTitle(ArtF object);

}