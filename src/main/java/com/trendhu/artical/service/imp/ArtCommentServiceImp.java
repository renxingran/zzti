package com.trendhu.artical.service.imp;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.trendhu.artical.bean.ArtComment;
import com.trendhu.artical.dao.ArtCommentDao;
import com.trendhu.artical.service.ArtCommentService;
import com.trendhu.common.bean.mongo.Page;
import com.trendhu.common.service.imp.BaseServiceImp;


@Service
public class ArtCommentServiceImp extends BaseServiceImp<ArtComment>implements ArtCommentService{
	
	@Autowired
	ArtCommentDao artCommentDao;
	
	public ArtCommentDao getDao() {
		return artCommentDao;
	}

	@Override
	public ArtComment addComment(ArtComment object) {
		return artCommentDao.addComment(object);
	}

	@Override
	public Page<ArtComment> queryCommentInfoList(Map<String, Object> params, Page<ArtComment> page, String sortKey,
			Direction direction) {
		return artCommentDao.queryCommentInfoList(params, page, sortKey,direction);
	}

	@Override
	public List<ArtComment> queryMyComments(Map<String, Object> params, Page<ArtComment> page, String sortKey,
			Direction direction) {
		return artCommentDao.queryMyComments(params, page, sortKey,direction);
	}
}