package com.trendhu.artical.dao;


import java.util.Map;
import com.trendhu.artical.bean.Focus;
import com.trendhu.common.bean.mongo.Page;
import com.trendhu.common.dao.mongo.BaseDao;


public interface FocusDao  extends BaseDao<Focus>{
	
	 public Boolean getFocusSta(String sourceId,String userId) ;
	 
	    public Page<Focus> query(Map<String, Object> params, Page<Focus> page);
	    
	    public Long getFocusCount(String id);
	    
	    public Focus addFocusSource(Focus object);
	    
	    public Focus cancleFocusSource(Focus object);
}