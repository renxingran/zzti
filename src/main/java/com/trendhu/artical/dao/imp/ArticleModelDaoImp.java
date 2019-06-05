package com.trendhu.artical.dao.imp;

import java.util.ArrayList;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.trendhu.artical.bean.ArticleModel;
import com.trendhu.artical.dao.ArticleModelDao;
import com.trendhu.common.dao.mongo.imp.BaseDaoImp;

@Repository(" ArticleModelDao")
public class ArticleModelDaoImp extends BaseDaoImp<ArticleModel> implements ArticleModelDao{

	@Override
	public ArrayList<String> getFeatureByArtId(String artId) {
		
		ArticleModel articleModel =	this.getMongoTemplate()
		.findOne(new Query(Criteria.where("_id").is(artId)), ArticleModel.class, "article_model");
		
		ArrayList<String> feature1 = articleModel.getFeatures();//"/n"
		
		ArrayList<String> feature2 = new ArrayList<>();
		String a;
		if(feature1 != null) {
			for(String f:feature1) {
				if(f.contains("/n")) {
					 a = f.split("/n")[0];
				}else {
					a = f.split("/v")[0];
				}
				feature2.add(a);
			}
		}
		
		return feature2;
	}
}