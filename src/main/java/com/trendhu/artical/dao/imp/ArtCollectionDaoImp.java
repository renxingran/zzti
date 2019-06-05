package com.trendhu.artical.dao.imp;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.trendhu.artical.bean.ArtCollection;
import com.trendhu.artical.bean.PointPrise;
import com.trendhu.artical.dao.ArtCollectionDao;
import com.trendhu.common.bean.mongo.Page;
import com.trendhu.common.dao.mongo.imp.BaseDaoImp;

@Repository(" artCollectionDao")
public class ArtCollectionDaoImp extends BaseDaoImp<ArtCollection> implements ArtCollectionDao{
	
    @Override
    public Boolean getCollectionSta(String artId,String userId) {
    	return this.getMongoTemplate().exists(
    			new Query(Criteria.where("userId").is(userId).
    					and("artId").is(artId) ), ArtCollection.class);
    }

	@Override
	public ArtCollection addCollection(ArtCollection object) {
		if(object != null){
				object.setArtCollectionDate(new Date());
				object.setCollectionSta(true);
				
				Boolean b = this.getMongoTemplate().exists(
		    			new Query(Criteria.where("userId").is(object.getUserId()).
		    					and("artId").is(object.getArtId()) ), ArtCollection.class);
				if(b) {
					Query query = new Query();
					query.addCriteria(Criteria.where("userId").is(object.getUserId()).and("artId").is(object.getArtId()));
					Update update = new Update();
					update.set("collectionSta",true );
					this.getMongoTemplate().updateFirst(query, update,"collection");
				}else {
					 this.getMongoTemplate().insert(object, "collection");
				}
				Long collectionCount = this.getMongoTemplate().count(
						new Query(Criteria.where("artId").is(object.getArtId()).and("collectionSta").is(true)), ArtCollection.class, "collection");
				object.setCollectionCount(collectionCount);
				return object;
		}else {
			ArtCollection artCollection = new ArtCollection();
			artCollection.setCollectionSta(false);
		   return artCollection;
		}
	}

	@Override
	public ArtCollection cancleCollection(ArtCollection object) {

			String userId = object.getUserId();
			String artId = object.getArtId();
			Query query = new Query();
			query.addCriteria(Criteria.where("userId").is(userId).and("artId").is(artId));
			Update update = new Update();
			update.set("collectionSta",false );
			this.getMongoTemplate().updateFirst(query, update,"collection");

	
			object.setCollectionSta(false);
			Long collectionCount = this.getMongoTemplate().count(
					new Query(Criteria.where("artId").is(artId).and("collectionSta").is(true)), ArtCollection.class, "collection");
			object.setCollectionCount(collectionCount);
			return object;
	
		
	}

	@Override
	public List<ArtCollection> queryMyCollectionList(Map<String, Object> params, Page<ArtCollection> page,
			String sortKey, Direction direction) {
		params.put("collectionSta", true);
		Query query = this.getGeneralQuery(params, page, sortKey, direction);
        List<ArtCollection> list = this.getMongoTemplate()
                           .find(query, ArtCollection.class,"collection");
		return list;
	}
	
	
}