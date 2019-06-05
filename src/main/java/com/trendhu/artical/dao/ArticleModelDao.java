package com.trendhu.artical.dao;


import java.util.ArrayList;

import com.trendhu.artical.bean.ArticleModel;
import com.trendhu.common.dao.mongo.BaseDao;


public interface ArticleModelDao  extends BaseDao<ArticleModel>{
	
	public ArrayList<String>  getFeatureByArtId(String artId);
}