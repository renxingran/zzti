package com.trendhu.artical.dao.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import com.trendhu.artical.bean.ArtComment;
import com.trendhu.artical.bean.PointPrise;
import com.trendhu.artical.bean.User;
import com.trendhu.artical.dao.ArtCommentDao;
import com.trendhu.artical.service.UserService;
import com.trendhu.common.bean.mongo.Page;
import com.trendhu.common.dao.mongo.imp.BaseDaoImp;

@Repository(" artCommentDao")
public class ArtCommentDaoImp extends BaseDaoImp<ArtComment> implements ArtCommentDao{

	@Override
	public ArtComment addComment(ArtComment object) {
		if(object != null){
			object.setComTime(new Date());
			object.setComSta(1);
			this.getMongoTemplate().insert(object, "artComment");
			Long commentCount = this.getMongoTemplate().count(
					new Query(Criteria.where("artId").is(object.getArtId()).and("comSta").is(1)), ArtComment.class, "artComment");
			object.setCommentCount(commentCount);
			User user = this.getMongoTemplate()
					.findOne(new Query(Criteria.where("id").is(object.getUserId())), User.class, "user");
			object.setUserName(user.getUserName());
			object.setUseImg(user.getUseImg());
			return object;
		}else {
			ArtComment artComment = new ArtComment();
			artComment.setComSta(0);
		   return artComment;
			}
	}

	@Autowired
	private UserService userService;
	@Override
	public Page<ArtComment> queryCommentInfoList(Map<String, Object> params, Page<ArtComment> page, String sortKey, Direction direction) {
	
		Query query = this.getGeneralQuery(params, page, sortKey, direction);
		query.addCriteria(Criteria.where("comSta").is(1));
        List<ArtComment> list = this.getMongoTemplate()
                           .find(query, ArtComment.class,"artComment");
        List<ArtComment> artCommentLists = new ArrayList<ArtComment>();
        
        for(ArtComment artComment:list) {
//        	userService.get
        	User user = this.getMongoTemplate()
					.findOne(new Query(Criteria.where("id").is(artComment.getUserId())), User.class, "user");
        	artComment.setUserName(user.getUserName());
        	artCommentLists.add(artComment);
        }
        page.setList(artCommentLists);
        return page;
	}
	@Override
	public List<ArtComment> queryMyComments(Map<String, Object> params, Page<ArtComment> page, String sortKey,
			Direction direction) {
		params.put("comSta", 1);
		Query query = this.getGeneralQuery(params, page, sortKey, direction);
        List<ArtComment> list = this.getMongoTemplate()
                           .find(query, ArtComment.class,"artComment");
		return list;
	}
}