package com.trendhu.artical.dao.imp;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.trendhu.artical.bean.ArtCollection;
import com.trendhu.artical.bean.FM;
import com.trendhu.artical.dao.FMDao;
import com.trendhu.common.bean.mongo.Page;
import com.trendhu.common.dao.mongo.imp.BaseDaoImp;

@Repository(" fmDao")//文章初表DAO
public class FMDaoImp extends BaseDaoImp<FM> implements FMDao{

	@Override
	public List<FM> queryFM(Map<String, Object> params, Page<FM> page, String sortKey,
			Direction direction) {
		Query query = this.getGeneralQuery(params, page, sortKey, direction);
        List<FM> list = this.getMongoTemplate()
                           .find(query, FM.class,"fm");
		return list;
	}

	
}