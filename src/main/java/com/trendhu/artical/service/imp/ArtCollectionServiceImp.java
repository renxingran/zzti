package com.trendhu.artical.service.imp;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import com.trendhu.artical.bean.ArtCollection;
import com.trendhu.artical.bean.PointPrise;
import com.trendhu.artical.dao.ArtCollectionDao;
import com.trendhu.artical.service.ArtCollectionService;
import com.trendhu.common.bean.mongo.Page;
import com.trendhu.common.service.imp.BaseServiceImp;


@Service
public class ArtCollectionServiceImp extends BaseServiceImp<ArtCollection>implements ArtCollectionService{
	
	@Autowired
	ArtCollectionDao artCollectionDao;
	
	public ArtCollectionDao getDao() {
		return artCollectionDao;
	}
	
    public Boolean getCollectionSta(String artId,String userId) {
    	return this.getDao().getCollectionSta(artId,userId);
    }

	@Override
	public ArtCollection addCollection(ArtCollection object) {
		
		return this.getDao().addCollection(object);
	}

	@Override
	public ArtCollection cancleCollection(ArtCollection object) {
		return this.getDao().cancleCollection(object);
	}

	
	@Override
	public List<ArtCollection> queryMyCollectionList(Map<String, Object> params, Page<ArtCollection> page, String sortKey,
			Direction direction) {
		return this.getDao().queryMyCollectionList(params, page, sortKey,direction);
	}
	
	
    
    

}