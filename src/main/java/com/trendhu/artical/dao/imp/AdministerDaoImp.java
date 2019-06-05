package com.trendhu.artical.dao.imp;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.trendhu.artical.bean.Administer;
import com.trendhu.artical.bean.User;
import com.trendhu.artical.dao.AdministerDao;
import com.trendhu.common.dao.mongo.imp.BaseDaoImp;

@Repository(" AdministerDao")
public class AdministerDaoImp extends BaseDaoImp<Administer> implements AdministerDao{
	
	@Override
	public String login(String admAccount,String admPwd) {
		String returnNum;
		
		Boolean b = this.getMongoTemplate().exists(
				new Query(Criteria.where("admAccount").is(admAccount).and("admPwd").is(admPwd)), Administer.class, "administer");
		
		if(b.equals(true)) {
			Query query = new Query();
			query.addCriteria(Criteria.where("admAccount").is(admAccount));
			Update update = new Update();
			update.set("admSta",1 );
			this.getMongoTemplate().updateFirst(query, update,"administer");
			returnNum = "A001";
		}else {
			returnNum = "A008";
		}
		return  returnNum;
	}
}