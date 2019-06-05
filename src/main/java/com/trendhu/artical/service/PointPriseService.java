package com.trendhu.artical.service;

import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Sort.Direction;
import com.trendhu.artical.bean.PointPrise;
import com.trendhu.common.bean.mongo.Page;
import com.trendhu.common.service.BaseService;

public interface PointPriseService extends BaseService<PointPrise>{
	
	public Boolean getPointPriseSta(String artId,String userId) ;
	
	public PointPrise addPointPrise(PointPrise object);
	
	public PointPrise canclePointPrise(PointPrise object);
	
	  public List<PointPrise> queryMyPointPriseList(Map<String, Object> params,
	            Page<PointPrise> page,
	            String sortKey,
	            Direction direction);

}