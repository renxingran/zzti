package com.trendhu.artical.dao;


import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Sort.Direction;

import com.trendhu.artical.bean.ArtComment;
import com.trendhu.artical.bean.User;
import com.trendhu.common.bean.mongo.Page;
import com.trendhu.common.dao.mongo.BaseDao;


public interface ArtCommentDao  extends BaseDao<ArtComment>{
	
	public ArtComment addComment(ArtComment object);
	
	public Page<ArtComment> queryCommentInfoList(Map<String, Object> params, Page<ArtComment> page, String sortKey, Direction direction) ;

	public List<ArtComment> queryMyComments(Map<String, Object> params, Page<ArtComment> page, String sortKey,
			Direction direction) ;
}