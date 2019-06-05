package com.trendhu.common.dao.mongo.imp;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.trendhu.common.bean.mongo.Page;
import com.trendhu.common.dao.mongo.BaseDao;

/**
 * 所有Dao层的根基接口实现
 * 
 */
@Repository("baseDao")

public abstract class BaseDaoImp<T> implements BaseDao<T> {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public MongoTemplate getMongoTemplate() {
        return mongoTemplate;
    }

    public String collectionName;

    @Override
    public String getCollectionName() {
        this.checkAndSet();
        return this.collectionName;
    }

    public Class<T> classT;

    @Override
    public Class<T> getClassT() {
        this.checkAndSet();
        return this.classT;
    }

    @SuppressWarnings("unchecked")
    private void checkAndSet() {
        if (this.collectionName == null) {
            synchronized (this) {
                ParameterizedType pt = (ParameterizedType) getClass().getGenericSuperclass();
                this.classT = (Class<T>) pt.getActualTypeArguments()[0];
                Document doc = classT.getAnnotation(Document.class);
                if (doc != null) {
                    this.collectionName = doc.collection();
                } else {
                    logger.error("所查询对象没有collection！");
                }
            }
        }
    }

    /**
     * 供后续覆盖使用，修改初始化此类时打印的日志语句
     * 
     * @return String 初始化此类时打印的日志语句
     */
    protected String postFixLog() {
        return "";
    }

    // 初始化此类时执行
    @PostConstruct
    private void makeLogs() {
        logger.info("初始化controller：" + this.getClass().getSimpleName() + "成功." + postFixLog());
    }

    @Override
    public T insert(T object) {
        logger.debug(object.toString());
        return this.getMongoTemplate().insert(object, this.getCollectionName());
    }

    @Override
    public Collection<T> insertAll(Collection<T> object) {
        return this.getMongoTemplate().insertAll(object);
    }

    @Override
    public boolean remove(String id) {
        DeleteResult result = this.getMongoTemplate()
                                  .remove(new Query(Criteria.where("id").is(id)),
                                          this.getClassT(),
                                          this.getCollectionName());
        if (result.getDeletedCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public long removeAll(List<String> idList) {
        DeleteResult result = this.getMongoTemplate()
                                  .remove(new Query(Criteria.where("id").in(idList)),
                                          this.getClassT(),
                                          this.getCollectionName());
        return result.getDeletedCount();
    }

    @Override
    public long remove(Map<String, Object> params) {
        Query query = new Query();
        for (Entry<String, Object> entry : params.entrySet()) {
            if (entry.getKey() != null && entry.getValue() != null) {
                query.addCriteria(Criteria.where(entry.getKey()).is(entry.getValue()));
            }
        }
        DeleteResult result = this.getMongoTemplate()
                                  .remove(query, this.getClassT(), this.getCollectionName());
        return result.getDeletedCount();
    }

    @Override
    public boolean update(Map<String, Object> params) {

        if (!params.containsKey("id")) {
            return false;
        }

        Query query = new Query();
        Update update = new Update();

        for (Entry<String, Object> entry : params.entrySet()) {
            if (entry.getKey().equals("id") && entry.getValue() != null) {
                query.addCriteria(Criteria.where(entry.getKey()).is(entry.getValue()));
            } else {
                update.set(entry.getKey(), entry.getValue());
            }
        }

        UpdateResult result = this.getMongoTemplate().updateFirst(query, update, this.getClassT());
        if (result.getModifiedCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean upsert(Map<String, Object> params) {
        if (!params.containsKey("id")) {
            return false;
        }

        Query query = new Query();
        Update update = new Update();

        for (Entry<String, Object> entry : params.entrySet()) {
            if (entry.getKey().equals("id") && entry.getValue() != null) {
                query.addCriteria(Criteria.where(entry.getKey()).is(entry.getValue()));
            } else {
                update.set(entry.getKey(), entry.getValue());
            }
        }

        UpdateResult result = this.getMongoTemplate().upsert(query, update, this.getClassT());
        if (result.getModifiedCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public T findOne(Map<String, Object> params) {
        logger.debug(this.getClassT() + " - 查询参数" + params);
        Query query = new Query();
        for (Entry<String, Object> entry : params.entrySet()) {
            if (entry.getKey() != null && entry.getValue() != null) {
                query.addCriteria(Criteria.where(entry.getKey()).is(entry.getValue()));
            }
        }
        return this.getMongoTemplate().findOne(query, this.getClassT(), this.getCollectionName());
    }

    @Override
    public T findOne(String id) {
        return this.getMongoTemplate()
                   .findOne(new Query(Criteria.where("id").is(id)),
                            this.getClassT(),
                            this.getCollectionName());
    }

    @Override
    public Collection<T> findAll(Collection<String> idList) {
        return this.getMongoTemplate()
                   .find(Query.query(Criteria.where("id").in(idList)), getClassT());
    }

    @Override
    public Collection<T> findAll() {
        return this.getMongoTemplate().findAll(this.getClassT());
    }
    
    @Override
    public Collection<T> findAllByUserId(String id) {
        return this.getMongoTemplate().find(new Query(Criteria.where("userId").is(id).and("focusSta").is(true)),this.getClassT() );
    }

    @Override
    public Page<T> query(Map<String, Object> params,
                         Page<T> page) {
        // 以Id为排序值进行查询
        return this.query(params, page, "id");
    }

    protected Query getGeneralQuery(Map<String, Object> params,
                                    Page<T> page,
                                    String sortKey,
                                    Direction direction) {
        // 默认以Id为排序值进行查询
        sortKey = sortKey == null ? "id" : sortKey;
        Query query = new Query();
        if (params != null) {
            logger.debug("查询参数：" + params);
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if (entry.getKey() != null && entry.getValue() != null) {
                    switch (entry.getValue().getClass().getName()) {
                    case "java.util.ArrayList":
                    case "java.util.LinkedList":
                        query.addCriteria(Criteria.where(entry.getKey()).in(entry.getValue()));
                        break;
                    default:
                        query.addCriteria(Criteria.where(entry.getKey()).is(entry.getValue()));
                        break;
                    }
                }
            }
        }
        List<Order> orders = new ArrayList<Order>(); // 排序
        orders.add(new Order(direction, sortKey));
        Sort sort = Sort.by(orders);
        if (page != null) {
            page.setSort(sort);
            // 查询出一共的条数
            Long count = getMongoTemplate().count(query, getClassT(), getCollectionName());
            page.setTotalSize(count.intValue());
            query.with(page);
        } else {
            query.with(sort);
        }
        return query;
    }

    @Override
    public Page<T> query(Map<String, Object> params,
                         Page<T> page,
                         String sortKey) {
        return this.query(params, page, sortKey, Direction.DESC);
    }
    
    public Page<T> query(Map<String, Object> params,
                         Page<T> page,
                         String sortKey,
                         Direction direction){
        Query query = this.getGeneralQuery(params, page, sortKey, direction);
        List<T> list = this.getMongoTemplate()
                           .find(query, this.getClassT(), this.getCollectionName());
        page.setList(list);
        return page;
    }

    @Override
    public List<T> query(Map<String, Object> params) {
        // 以Id为排序值进行查询
        return this.query(params, "id");
    }

    @Override
    public List<T> query(Map<String, Object> params,
                         String sortKey) {
        logger.debug("查询参数：" + params);
        sortKey = sortKey == null ? "id" : sortKey;
        Query query = new Query();
        if (params != null) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if (entry.getKey() != null && entry.getValue() != null) {
                    if (entry.getValue().equals("exists")) {// 判断该字段是否存在
                        query.addCriteria(Criteria.where(entry.getKey()).exists(true));
                    } else if (entry.getValue().equals("notexist")) {
                        query.addCriteria(Criteria.where(entry.getKey()).exists(false));
                    } else {
                        query.addCriteria(Criteria.where(entry.getKey()).is(entry.getValue()));
                    }
                }
            }
        }
        // 设置排序
        query.with(Sort.by(new Order(Direction.DESC, sortKey)));
        return (List<T>) this.getMongoTemplate()
                             .find(query, this.getClassT(), this.getCollectionName());
    }

    @Override
    public List<T> queryByQuery(Query query) {
        return (List<T>) this.getMongoTemplate()
                             .find(query, this.getClassT(), this.getCollectionName());
    }

    @Override
    public long getCount() {
        return this.getMongoTemplate().count(new Query(), this.getClassT());
    }
    @Override
    public long getPointCountByArtId(String id) {
        return this.getMongoTemplate().count(
        		new Query(Criteria.where("artId").is(id).and("pointPriseSta").is(true)),
        		 this.getClassT(),"pointPrise");
    }
    @Override
    public long getCommentCountByArtId(String id) {
    	return this.getMongoTemplate().count(
        		new Query(Criteria.where("artId").is(id).and("comSta").is(1)),
       		 this.getClassT(),"artComment");
    }
    
    public long getCollectionCountByArtId(String id) {
    	return this.getMongoTemplate().count(
        		new Query(Criteria.where("artId").is(id).and("collectionSta").is(true)),
       		 this.getClassT(),"collection");
    }
    
    public long getHisCountByArtId(String id) {
    	return this.getMongoTemplate().count(
        		new Query(Criteria.where("artId").is(id)),
       		 this.getClassT());
    }


    @Override
    public long getCountByUserId(String id) {
        return this.getMongoTemplate().count(
        		new Query(Criteria.where("userId").is(id)),
        		 this.getClassT());
    }

    public long getCountById(String id) {
    	return this.getMongoTemplate().count(new Query(Criteria.where(id).is(id)), this.getClassT());
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<T> findArtAndComm(String id) {
    	
    	Object article = new Object();
    	article = this.getMongoTemplate()
                .findOne(new Query(Criteria.where("id").is(id)),
                        this.getClassT(),
                        "artF");
    	
    	Object comment = new Object();
    	comment = this.getMongoTemplate()
                .findOne(new Query(Criteria.where("artId").is(id)),
                        this.getClassT(),
                       "artComment");
    	List<T> list = new ArrayList<>();
    	list.add((T) article);
    	System.out.println(list.toString());
    	list.add((T) comment);
    	System.out.println("!!!!!!!!!!!!");
    	System.out.println(list.toString());
        return list;
    }
}