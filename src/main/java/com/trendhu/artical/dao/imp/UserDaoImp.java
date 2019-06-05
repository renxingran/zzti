package com.trendhu.artical.dao.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.trendhu.artical.bean.ArtF;
import com.trendhu.artical.bean.User;
import com.trendhu.artical.dao.UserDao;
import com.trendhu.artical.service.UserService;
import com.trendhu.common.bean.mongo.Page;
import com.trendhu.common.dao.mongo.imp.BaseDaoImp;

@Repository(" userDao")
public class UserDaoImp extends BaseDaoImp<User> implements UserDao{

	@Override
	public String getExitByPhone(String phone,String email) {
		Query query1 = new Query();
		query1.addCriteria(Criteria.where("phone").is(phone));
		Boolean b1 = this.getMongoTemplate().exists(query1, "user");
		
		Query query2 = new Query();
		query2.addCriteria(Criteria.where("email").is(email));
		Boolean b2 = this.getMongoTemplate().exists(query2, "user");
		
		if(b1.equals(true)) {
			return "E002";
		}else if(b2.equals(true)) {
			return "E003";
		}else {
			return "A001";
		}
	}

	@Override
	public String login(String phone, String pwd) {
		String returnNum;
		Boolean b = this.getMongoTemplate().exists(
				new Query(Criteria.where("phone").is(phone).and("pwd").is(pwd)), User.class, "user");
		
		Boolean c = this.getMongoTemplate().exists(
				new Query(Criteria.where("phone").is(phone)), User.class, "user");
		
		if(b.equals(true)) {
			returnNum = "A001";
			
		}else if(c.equals(false)) {
			returnNum = "A010";//用户未注册
		}else {
			returnNum =  "A008";
		}
		return  returnNum;
	}
	
	@Autowired 
	private UserService userService;
	public String getUserIdByPhone(String phone) {
				
		User user =this.getMongoTemplate()
						.findOne(new Query(Criteria.where("phone").is(phone)),User.class,"user");
		
		String id  = user.getId();
		Query query = new Query();
		query.addCriteria(Criteria.where("phone").is(phone));
		Update update = new Update();
		update.set("loginStatus",1 );
		this.getMongoTemplate().updateFirst(query, update,"user");
		
		return  id;
	}
//用户忘记密码
	@Override
	public String forgetPwd(String phone, String email, String pwd) {
		
		String forgetPwdNum;
		
		Boolean b = this.getMongoTemplate().exists(
				new Query(Criteria.where("phone").is(phone).and("email").is(email)), User.class, "user");
		
		if(b) {
				Query query = new Query();
				query.addCriteria(Criteria.where("phone").is(phone).and("email").is(email));
				Update update = new Update();
				update.set("pwd",pwd);
				this.getMongoTemplate().updateFirst(query, update,"user");
				forgetPwdNum = "A001";//修改密码成功
				return forgetPwdNum;
		}else {
			forgetPwdNum = "A008";//邮箱或手机号输入错误
			return forgetPwdNum;
		}
	}

	@Override
	public Page<User> queryUserLists(Map<String, Object> params, Page<User> page, String sortKey, Direction direction) {
		        Query query = this.getGeneralQuery(params, page, sortKey, direction);
		        List<User> list = this.getMongoTemplate()
		                           .find(query, User.class,"user");
		        List<User> artFLists = new ArrayList<User>();
		        
		        for(User userList:list) {
		        		artFLists.add(userList);
		        }
		        page.setList(artFLists);
		        return page;
	}

	@Override
	public List<User> searchUserName(User object) {
			Query  query = new Query();
			String userName = object.getUserName();
			String phone = object.getPhone();
			query.addCriteria(Criteria.where("userName").regex(".*?\\" +userName+ ".*"));
			return this.getMongoTemplate().find(query, User.class);
	}

}