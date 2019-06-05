package com.trendhu.artical.dao;


import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Sort.Direction;

import com.trendhu.artical.bean.ArtCollection;
import com.trendhu.artical.bean.PointPrise;
import com.trendhu.common.bean.mongo.Page;
import com.trendhu.common.dao.mongo.BaseDao;


public interface ArtCollectionDao  extends BaseDao<ArtCollection>{

    public Boolean getCollectionSta(String artId,String userId) ;
    
    public ArtCollection addCollection(ArtCollection object);
    
    public ArtCollection cancleCollection(ArtCollection object);
    
	public List<ArtCollection> queryMyCollectionList(Map<String, Object> params, Page<ArtCollection> page, String sortKey,
			Direction direction) ;

}