package com.trendhu.artical.dao.imp;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import com.trendhu.artical.bean.Focus;
import com.trendhu.artical.dao.FocusDao;
import com.trendhu.common.bean.mongo.Page;
import com.trendhu.common.dao.mongo.imp.BaseDaoImp;

@Repository(" focusDao")
public class FocusDaoImp extends BaseDaoImp<Focus> implements FocusDao{

	@Override
    public Boolean getFocusSta(String sourceId,String userId) {
    	return this.getMongoTemplate().exists(
    			new Query(Criteria.where("userId").is(userId).
    					and("sourceId").is(sourceId).and("focusSta").is(true) ), Focus.class);
    }
	
    @Override
    public Page<Focus> query(Map<String, Object> params,
                         Page<Focus> page) {
        // 以Id为排序值进行查询
        return this.query(params, page, "id");
    }
    

    @Override
    public Page<Focus> query(Map<String, Object> params,
                         Page<Focus> page,
                         String sortKey) {
        return this.query(params, page, sortKey, Direction.DESC);
    }
    
    public Page<Focus> query(Map<String, Object> params,
                         Page<Focus> page,
                         String sortKey,
                         Direction direction){
        Query query = this.getGeneralQuery(params, page, sortKey, direction);
        List<Focus> list = this.getMongoTemplate()
                           .find(query, Focus.class, "focus");
        page.setList(list);
        return page;
    }
    @Override
	public Long getFocusCount(String id) {
        return this.getMongoTemplate().count(
        		new Query(Criteria.where("sourceId").is(id)),
        		 Focus.class);
		
	}

	@Override
	public Focus addFocusSource(Focus object) {
		if(object!=null) {
			object.setFocusDate(new Date());
			object.setFocusSta(true);
			
			Boolean b =	this.getMongoTemplate().exists(
						new Query(Criteria.where("userId").is(object.getUserId()).and("sourceId").is(object.getSourceId())), 
						Focus.class, "focus");
				if(b) {
					Query query = new Query();
					query.addCriteria(Criteria.where("userId").is(object.getUserId()).and("sourceId").is(object.getSourceId()));
					Update update = new Update();
					update.set("focusSta",true );
					this.getMongoTemplate().updateFirst(query, update,"focus");
				}else {
					 this.getMongoTemplate().insert(object, "focus");
				}
			return object;
			}else {
				Focus focus = new Focus();
				focus.setFocusSta(false);
				return focus;
			}
	}

	@Override
	public Focus cancleFocusSource(Focus object) {
		String userId = object.getUserId();
		String artId = object.getArtId();
		Query query = new Query();
		query.addCriteria(Criteria.where("userId").is(userId).and("artId").is(artId));
		Update update = new Update();
		update.set("focusSta",false );
		this.getMongoTemplate().updateFirst(query, update,"focus");
		object.setFocusSta(false);
		return object;
	}
}