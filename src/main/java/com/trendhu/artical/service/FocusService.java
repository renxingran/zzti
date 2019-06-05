package com.trendhu.artical.service;

import java.util.Map;

import com.trendhu.artical.bean.Focus;
import com.trendhu.common.bean.mongo.Page;
import com.trendhu.common.service.BaseService;
  
public interface FocusService extends BaseService<Focus>{
	
	public Boolean getFocusSta(String sourceId,String userId) ;
	
    public Page<Focus> query(Map<String, Object> params,Page<Focus> page);
    
    public Long getFocusCount(String id);//网站关注量
    
    public Focus addFocusSource(Focus object);
    
    public Focus cancleFocusSource(Focus object);
}