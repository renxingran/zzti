package com.trendhu.artical.service.imp;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.trendhu.artical.bean.ArticleModel;
import com.trendhu.artical.dao.ArticleModelDao;
import com.trendhu.artical.service.ArticleModelService;
import com.trendhu.common.service.imp.BaseServiceImp;


@Service
public class ArticleModelServiceImp extends BaseServiceImp<ArticleModel>implements ArticleModelService{
	
	@Autowired
	ArticleModelDao articleModelDao;
	
	@Override
	public ArticleModelDao getDao() {
		return articleModelDao;
	}

	@Override
	public ArrayList<String> getFeatureByArtId(String artId) {
		return this.getDao().getFeatureByArtId(artId);
	}
}