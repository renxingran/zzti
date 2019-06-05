package com.trendhu.artical.dao;


import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Sort.Direction;
import com.trendhu.artical.bean.ArtRecom;
import com.trendhu.common.bean.mongo.Page;
import com.trendhu.common.dao.mongo.BaseDao;


public interface ArtRecomDao  extends BaseDao<ArtRecom>{
	
	 public ArtRecom queryArtLists(ArtRecom object);
	
}