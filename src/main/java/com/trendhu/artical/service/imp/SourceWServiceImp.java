package com.trendhu.artical.service.imp;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import com.trendhu.artical.bean.SourceW;
import com.trendhu.artical.dao.SourceWDao;
import com.trendhu.artical.service.SourceWService;
import com.trendhu.common.bean.mongo.Page;
import com.trendhu.common.service.imp.BaseServiceImp;


@Service
public class SourceWServiceImp extends BaseServiceImp<SourceW>implements SourceWService{
	
	@Autowired
	SourceWDao sourceWDao;
	
	public SourceWDao getDao() {
		return sourceWDao;
	}
	
    @Override
    public Page<SourceW> query(Map<String, Object> params,
                         Page<SourceW> page,
                         String sortKey,
                         Direction direction) {
        return this.getDao().query(params, page, sortKey);
    }
    
    @Override
    public Page<SourceW> query(Map<String, Object> params,
                         Page<SourceW> page) {
        return this.getDao().query(params, page);
    }
    
	public String findSourceId(String sourceName) {
		return sourceWDao.findSourceId(sourceName);
		
	}

	@Override
	public String getSourceNameByArtId(String artId) {
		return sourceWDao.getSourceNameByArtId(artId);
	}

	@Override
	public String getSourceImgByArtId(String artId) {
		return sourceWDao.getSourceImgByArtId(artId);
	}

	@Override
	public Page<SourceW> querySourceWInfoLists(Map<String, Object> params, Page<SourceW> page, String sortKey,
			Direction direction) {
		
		return sourceWDao.querySourceWInfoLists(params, page, sortKey,direction);
		
	}

}