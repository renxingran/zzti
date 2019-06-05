package com.trendhu.artical.dao.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.trendhu.artical.bean.ArtF;
import com.trendhu.artical.bean.PointPrise;
import com.trendhu.artical.dao.PointPriseDao;
import com.trendhu.common.bean.mongo.Page;
import com.trendhu.common.dao.mongo.imp.BaseDaoImp;

@Repository(" pointPriseDao")
public class PointPriseDaoImp extends BaseDaoImp<PointPrise> implements PointPriseDao{

	@Override
    public Boolean getPointPriseSta(String artId,String userId) {
    	return this.getMongoTemplate().exists(
    			new Query(Criteria.where("userId").is(userId).
    					and("artId").is(artId).and("pointPriseSta").is(true) ), PointPrise.class,"pointPrise");
    }

	@Override
	public PointPrise addPointPrise(PointPrise object) {
		if(object != null){
				object.setPointPriseDate(new Date());
				object.setPointPriseSta(true);
				
				Boolean b = this.getMongoTemplate().exists(
		    			new Query(Criteria.where("userId").is(object.getUserId()).
		    					and("artId").is(object.getArtId()) ), PointPrise.class,"pointPrise");
				if(b) {
					Query query = new Query();
					query.addCriteria(Criteria.where("userId").is(object.getUserId()).and("artId").is(object.getArtId()));
					Update update = new Update();
					update.set("pointPriseSta",true );
					this.getMongoTemplate().updateFirst(query, update,"pointPrise");
				}else {
					 this.getMongoTemplate().insert(object, "pointPrise");
				}
				Long pointPriseCount = this.getMongoTemplate().count(
						new Query(Criteria.where("artId").is(object.getArtId()).and("pointPriseSta").is(true)), PointPrise.class, "pointPrise");
				object.setPointCount(pointPriseCount);
				System.out.println("66666666"+object);
				return object;
		}else {
			PointPrise pointPrise = new PointPrise();
			pointPrise.setPointPriseSta(false);
		   return pointPrise;
		}
}

	@Override
	public PointPrise canclePointPrise(PointPrise object) {

			String userId = object.getUserId();
			String artId = object.getArtId();
			Query query = new Query();
			query.addCriteria(Criteria.where("userId").is(userId).and("artId").is(artId));
			Update update = new Update();
			update.set("pointPriseSta",false );
			this.getMongoTemplate().updateFirst(query, update,"pointPrise");
			object.setPointPriseSta(false);
			
			Long pointPriseCount = this.getMongoTemplate().count(
					new Query(Criteria.where("artId").is(object.getArtId()).and("pointPriseSta").is(true)), PointPrise.class, "pointPrise");
			object.setPointCount((pointPriseCount));
			return object;
	}

	@Override
	public List<PointPrise> queryMyPointPriseList(Map<String, Object> params, Page<PointPrise> page, String sortKey,
			Direction direction) {
		params.put("pointPriseSta", true);
		Query query = this.getGeneralQuery(params, page, sortKey, direction);
        List<PointPrise> list = this.getMongoTemplate()
                           .find(query, PointPrise.class,"pointPrise");
		return list;
	}
	
}