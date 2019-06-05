package com.trendhu.artical.dao.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.trendhu.artical.bean.ArtF;
import com.trendhu.artical.bean.SourceW;
import com.trendhu.artical.dao.SourceWDao;
import com.trendhu.common.bean.mongo.Page;
import com.trendhu.common.dao.mongo.imp.BaseDaoImp;

@Repository(" SourceWDao")//文章初表DAO
public class SourceWDaoImp extends BaseDaoImp<SourceW> implements SourceWDao{
    @Override
    public Page<SourceW> query(Map<String, Object> params,
                         Page<SourceW> page,
                         String sortKey) {
        return this.query(params, page, sortKey, Direction.DESC);
    }

	@Override
	public String findSourceId(String sourceName) {
		SourceW sourceW = this.getMongoTemplate()
                .findOne(new Query(Criteria.where("sourceName").is(sourceName)),
                		SourceW.class,
                        "sourceW");
		return  sourceW.getId();
		
	}

	@Override
	public String getSourceNameByArtId(String artId) {
		ArtF artF = this.getMongoTemplate()
				.findOne(new Query(Criteria.where("id").is(artId)), ArtF.class, "artF");
		return artF.getSource();
	}

	@Override
	public String getSourceImgByArtId(String artId) {
		ArtF artF = this.getMongoTemplate()
				.findOne(new Query(Criteria.where("id").is(artId)), ArtF.class, "artF");
		String sourceName = artF.getSource();
		SourceW sourceW = this.getMongoTemplate()
				.findOne(new Query(Criteria.where("sourceName").is(sourceName)), SourceW.class, "sourceW");
		return sourceW.getImgSouce();
	}

	@Override
	public Page<SourceW> querySourceWInfoLists(Map<String, Object> params, Page<SourceW> page, String sortKey,
			Direction direction) {
			Query query = this.getGeneralQuery(params, page, sortKey, direction);
	        List<SourceW> list = this.getMongoTemplate()
	                           .find(query, SourceW.class,"sourceW");
	        List<SourceW> sourceWLists = new ArrayList<SourceW>();
	        
	        for(SourceW sourceWList:list) {
	        		sourceWLists.add(sourceWList);
	        }
	        page.setList(sourceWLists);
	        return page;
		
	}
}