package com.trendhu.artical.dao.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.client.result.UpdateResult;
import com.trendhu.artical.bean.ArtF;
import com.trendhu.artical.bean.ArtRecom;
import com.trendhu.artical.bean.PointPrise;
import com.trendhu.artical.bean.User;
import com.trendhu.artical.dao.ArtFDao;
import com.trendhu.artical.service.HisActService;
import com.trendhu.common.bean.mongo.Page;
import com.trendhu.common.dao.mongo.imp.BaseDaoImp;

@Repository(" artFDao")//文章初表DAO
public class ArtFDaoImp extends BaseDaoImp<ArtF> implements ArtFDao{

    @Override
    public Page<ArtF> query(Map<String, Object> params,
                         Page<ArtF> page,
                         String sortKey) {
        return this.query(params, page, sortKey, Direction.DESC);
    }
  
//    @Autowired
//    private HisActService hisActService;
//    public Page<ArtRecom> queryArtLists(Map<String, Object> params,
//                         Page<ArtRecom> page,
//                         String sortKey,
//                         Direction direction){
////    	params.put("examineN", 1);
////    	params.put("releaseN", 1);
//    	
//        Query query = this.getGeneralQuery(params, page, sortKey, direction);
//        List<ArtRecom> list = this.getMongoTemplate()
//                           .find(query, ArtRecom.class,"art_recom");
//        List<ArtRecom> artFLists = new ArrayList<ArtRecom>();
//        
//        for(ArtRecom artFList:list) {
//        		artFLists.add(artFList);
//        }
//        page.setList(artFLists);
//        return page;
//    }

	@Override
	public Long getArtReleaseCount(String sourceName) {
		
		  return this.getMongoTemplate().count(
	        		new Query(Criteria.where("source").is(sourceName).and("releaseN").is(1)),
	        		 ArtF.class);
	}
	

	@Override
	public String examineArtPass(String id) {
		if(id!=null) {
			Query query = new Query();
			query.addCriteria(Criteria.where("id").is(id));
			Update update = new Update();
			update.set("examineN",1);
			this.getMongoTemplate().updateFirst(query, update,"artF");
			return "A001";//审核通过成功
		}else {
			return "A008";//审核通过失败
		}
	}
	
	
	public Page<ArtF> queryArtInfoLists(Map<String, Object> params, Page<ArtF> page, String sortKey,
			Direction direction) {
		   Query query = this.getGeneralQuery(params, page, sortKey, direction);
		   query.addCriteria(Criteria.where("releaseN").is(0).and("examineN").is(0));
	        List<ArtF> list = this.getMongoTemplate()
	                           .find(query, ArtF.class,"artF");
	        List<ArtF> artFLists = new ArrayList<ArtF>();
	        
	        for(ArtF artList:list) {
	        		artFLists.add(artList);
	        }
	        page.setList(artFLists);
	        return page;
	}

	@Override
	public List<ArtF> queryKeyWordsLists(String keyWords) {
		
			Query  query = new Query();
			if(keyWords != "") {
				query.addCriteria(Criteria.where("title").regex(".*?\\" +keyWords+ ".*").and("examineN").is(1).and("releaseN").is(1));
			}
		return this.getMongoTemplate().find(query, ArtF.class);
	}

	@Override
	public List<ArtF> searchNewsTitle(ArtF object) {
		Query query = new Query();
		
		String title = object.getTitle();
    	String source = object.getSource();
//    	ArrayList<String> features = object.getFeatures();
    	
    	if((title != null) && (source != null)) {
			query.addCriteria(Criteria.where("title").regex(".*?\\" +title+ ".*").and("source").regex(".*?\\" +source+ ".*").and("releaseN").is(0));
    	}else if((title != null) &&(source == null)) {
			query.addCriteria(Criteria.where("title").regex(".*?\\" +title+ ".*").and("releaseN").is(0));
    	}else {
			query.addCriteria(Criteria.where("source").regex(".*?\\" +source+ ".*").and("releaseN").is(0));
    	}
		return this.getMongoTemplate().find(query, ArtF.class);
	}
}