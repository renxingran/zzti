package com.trendhu.artical.service;

import java.util.ArrayList;

import com.trendhu.artical.bean.ArticleModel;
import com.trendhu.common.service.BaseService;

public interface ArticleModelService extends BaseService<ArticleModel>{

	public ArrayList<String> getFeatureByArtId(String artId);
}