package com.trendhu.common.service.imp;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import com.trendhu.artical.bean.ArtCollection;
import com.trendhu.artical.bean.User;
import com.trendhu.common.bean.mongo.Page;
import com.trendhu.common.dao.mongo.BaseDao;
import com.trendhu.common.service.BaseService;

/**
 * 所有Service层的根基接口实现
 */
public abstract class BaseServiceImp<T> implements BaseService<T> {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected MongoTemplate mongoTemplate;

    public abstract BaseDao<T> getDao();

    protected String postFixLog() {
        return "";
    }

    @PostConstruct
    private void makeLogs() {
        logger.info("初始化controller：" + this.getClass().getSimpleName() + "成功." + postFixLog());
    }

    @Override
    public T insert(T object) {
        return this.getDao().insert(object);
    }

    @Override
    public Collection<T> insertAll(Collection<T> object) {
        return this.getDao().insertAll(object);
    }

    @Override
    public boolean remove(String id) {
        return this.getDao().remove(id);
    }

    @Override
    public long removeAll(List<String> idList) {
        return this.getDao().removeAll(idList);
    }

    @Override
    public long remove(Map<String, Object> params) {
        return this.getDao().remove(params);
    }

    @Override
    public boolean update(Map<String, Object> params) {
        return this.getDao().update(params);
    }

    @Override
    public boolean upsert(Map<String, Object> params) {
        return this.getDao().upsert(params);
    }

    @Override
    public T findOne(Map<String, Object> params) {
        return this.getDao().findOne(params);
    }

    @Override
    public T findOne(String id) {
        return this.getDao().findOne(id);
    }

    @Override
    public Collection<T> findAll(Collection<String> idList) {
        return this.getDao().findAll(idList);
    }

    @Override
    public Collection<T> findAll() {
        return this.getDao().findAll();
    }
    
    @Override
    public Collection<T> findAllByUserId(String id) {
        return this.getDao().findAllByUserId(id);
    }

    @Override
    public Page<T> query(Map<String, Object> params,
                         Page<T> page) {
        return this.getDao().query(params, page);
    }

    @Override
    public Page<T> query(Map<String, Object> params,
                         Page<T> page,
                         String sortKey) {
        return this.getDao().query(params, page, sortKey);
    }

    @Override
    public Page<T> query(Map<String, Object> params,
                         Page<T> page,
                         String sortKey,
                         Direction direction) {
        return this.getDao().query(params, page, sortKey,direction);
    }

    @Override
    public List<T> query(Map<String, Object> params) {
        return this.getDao().query(params);
    }

    @Override
    public List<T> query(Map<String, Object> params,
                         String sortKey) {
        return this.getDao().query(params, sortKey);

    }

    @Override
    public List<T> queryByQuery(Query query) {
        return this.getDao().queryByQuery(query);
    }

    @Override
    public String getCollectionName() {
        return this.getCollectionName();
    }

    @Override
    public Class<T> getClassT() {
        return this.getDao().getClassT();
    }

    @Override
    public MongoTemplate getMongoTemplate() {
        return this.getDao().getMongoTemplate();
    }

    @Override
    public long getCount() {
        return this.getDao().getCount();
    }
    @Override
    public long getPointCountByArtId(String id) {
    	return this.getDao().getPointCountByArtId(id);
    }
    public long getCommentCountByArtId(String id) {
    	return this.getDao().getCommentCountByArtId(id);
    }
    
    public long getCollectionCountByArtId(String id) {
    	return this.getDao().getCollectionCountByArtId(id);
    }
    public long getHisCountByArtId(String id) {
    	return this.getDao().getHisCountByArtId(id);
    }
    public long getCountByUserId(String id) {
    	return this.getDao().getCountByUserId(id);
    }
    
    public long getCountById(String id) {
    	return this.getDao().getCountById(id);
    }
    
    @Override
	public List<T> findArtAndComm(String id) {
        return this.getDao().findArtAndComm(id);
    }

    
    
}