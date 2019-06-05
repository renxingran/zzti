package com.trendhu.artical.service.imp;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import com.trendhu.artical.bean.ArtF;
import com.trendhu.artical.bean.ArtRecom;
import com.trendhu.artical.dao.ArtFDao;
import com.trendhu.artical.service.ArtFService;
import com.trendhu.common.bean.mongo.Page;
import com.trendhu.common.service.imp.BaseServiceImp;


@Service
public class ArtFServiceImp extends BaseServiceImp< ArtF>implements ArtFService{
	
	@Autowired
	ArtFDao artFDao;
	
	public ArtFDao getDao() {
		return artFDao;
	}
	
//    @Override
//    public Page<ArtRecom> queryArtLists(Map<String, Object> params,
//                         Page<ArtRecom> page,
//                         String sortKey,
//                         Direction direction) {
//    	return this.getDao().queryArtLists(params, page, sortKey, direction);
//    }
   
    public Long getArtReleaseCount(String sourceName) {
		return this.getDao().getArtReleaseCount(sourceName);
    }


	@Override
	public String examineArtPass(String id) {
		
		return this.getDao().examineArtPass(id);
		
	}

	@Override
	public Page<ArtF> queryArtInfoLists(Map<String, Object> params, Page<ArtF> page, String sortKey,
			Direction direction) {
		return this.getDao().queryArtInfoLists(params, page, sortKey,direction);
	}

	@Override
	public List<ArtF> queryKeyWordsLists(String keyWords) {
		return this.getDao().queryKeyWordsLists(keyWords);
	}

	@Override
	public List<ArtF> searchNewsTitle(ArtF object) {
		return this.getDao().searchNewsTitle(object);
	}

}