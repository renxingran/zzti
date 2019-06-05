package com.trendhu.artical.service;

import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Sort.Direction;
import com.trendhu.artical.bean.User;
import com.trendhu.common.bean.mongo.Page;
import com.trendhu.common.service.BaseService;

public interface UserService extends BaseService<User>{
		
	public String getExitByPhone(String phone,String email) ;
	
	public String login(String phone,String pwd);
	
	public String getUserIdByPhone(String phone);
	
	public String forgetPwd(String phone,String email,String pwd);
	
    public Page<User> queryUserLists(Map<String, Object> params,
            Page<User> page,
            String sortKey,
            Direction direction);
    
    public List<User> searchUserName(User object);
	
}
