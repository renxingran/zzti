package com.trendhu.artical.dao.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.trendhu.artical.bean.ArtCollection;
import com.trendhu.artical.bean.ArtRecom;
import com.trendhu.artical.bean.FM;
import com.trendhu.artical.dao.ArtRecomDao;
import com.trendhu.artical.dao.FMDao;
import com.trendhu.artical.service.HisActService;
import com.trendhu.common.bean.mongo.Page;
import com.trendhu.common.dao.mongo.imp.BaseDaoImp;

@Repository(" artRecomDao")//文章初表DAO
public class ArtRecomDaoImp extends BaseDaoImp<ArtRecom> implements ArtRecomDao{

	public ArtRecom queryArtLists(ArtRecom object){
	    	
	    	ArtRecom  artRecom = this.getMongoTemplate().
	    			findOne(new Query(Criteria.where("user_id").is(object.getUser_id())), ArtRecom.class,"art_recom");

	        return artRecom;
	    }

	
}