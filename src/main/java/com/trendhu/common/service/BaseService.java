package com.trendhu.common.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import com.trendhu.common.bean.mongo.Page;

/**
 * 所有Service层的根基接口，对Dao层的再次封装
 * 
 */
public interface BaseService<T> {

    /**
     * 添加
     * 
     * @param object
     *            T 添加的数据
     * @return T 添加成功的数据
     */
    public T insert(T object);

    /**
     * 批添加
     * 
     * @param object
     *            Collection<T> 需要批量添加的数据集合
     * @return Collection<T> 添加的结果集
     */
    public Collection<T> insertAll(Collection<T> object);

    /**
     * 根据 Id 删除单条记录
     * 
     * @param id
     *            String 记录Id
     * @return boolean 是否删除成功
     */
    public boolean remove(String id);

    /**
     * 根据 Id列表 删除多条记录
     * 
     * @param idList
     *            List<String> Id列表
     * @return Long 删除的记录数
     */
    public long removeAll(List<String> idList);

    /**
     * 更具删除条件删除记录
     * 
     * @param params
     *            Map<String, Object> 删除条件
     * @return Long 删除的记录数
     */
    public long remove(Map<String, Object> params);

    /**
     * 修改，当字段不存在时则不进行操作
     * 
     * @param params
     *            Map<String, Object> 查询条件
     * @return Boolean 修改结果
     */
    public boolean update(Map<String, Object> params);

    /**
     * 修改，当字段不存在时进行操作插入操作
     * 
     * @param params
     *            Map<String, Object> 查询条件
     * @return Boolean 修改结果
     */
    public boolean upsert(Map<String, Object> params);

    /**
     * 条件查找单条记录
     * 
     * @param params
     *            Map<String, Object> 查询条件
     * @return T 查询结果
     */
    public T findOne(Map<String, Object> params);

    /**
     * 根据Id查找单条记录
     * 
     * @param id
     *            String Id
     * @return T 查询结果数据
     */
    public T findOne(String id);

    /**
     * 批量Id查询批量记录
     * 
     * @param idList
     *            Collection<String> 查询的Id集合
     * @return Collection<T> 结果集合
     */
    public Collection<T> findAll(Collection<String> idList);

    /**
     * 查询所有数据
     * 
     * @return Collection<T> 结果集合（数据库中全部此类数据）
     */
    public Collection<T> findAll();

    /**
     * 条件查询，分页
     * 
     * @param params
     *            Map<String, Object> 查询条件
     * @param page
     *            Page<T> 分页条件
     * @return Page<T> 查询结果
     */
    public Page<T> query(Map<String, Object> params,
                         Page<T> page);

    /**
     * 条件查询，分页，指定排序值
     * 
     * @param params
     *            Map<String, Object> 查询条件
     * @param page
     *            Page<T> 分页条件
     * @param sortKey
     *            String 排序值
     * @return Page<T> 查询结果
     */
    public Page<T> query(Map<String, Object> params,
                         Page<T> page,
                         String sortKey);

    /**
     * 条件查询，分页，指定排序值，指定 正序/倒序
     * 
     * @param params
     *            Map<String, Object> 查询条件
     * @param page
     *            Page<T> 分页条件
     * @param sortKey
     *            String 排序值
     * @param direction
     *            Direction 正序/倒序
     * @return Page<T> 查询结果
     */
    public Page<T> query(Map<String, Object> params,
                         Page<T> page,
                         String sortKey,
                         Direction direction);

    /**
     * 条件查询，不分页，将所有满足查询条件的都返回
     *
     * @param params
     *            Map<String, Object> 查询条件
     * @return List<T> 查询结果
     */
    public List<T> query(Map<String, Object> params);

    /**
     * 条件查询，不分页，指定排序key，将所有满足查询条件的遵从排序值都返回，
     * 
     * @param params
     *            Map<String, Object> 查询条件
     * @return List<T> 查询结果
     */
    public List<T> query(Map<String, Object> params,
                         String sortKey);

    /**
     * 条件查询，查询对象为 Query类型
     * 
     * @param query
     *            Query 查询条件
     * @return List<T>
     */
    public List<T> queryByQuery(Query query);

    /**
     * 获取当前对象在数据库中的 表（集合）名
     * 
     * @return String 表（集合）名
     */
    public String getCollectionName();

    /**
     * 获取当前对象的 泛型类型
     * 
     * @return Class<T> Class类型
     */
    public Class<T> getClassT();

    /**
     * 获得模板对象，用于数据库操作
     * 
     * @return MongoTemplate 模板对象
     */
    public MongoTemplate getMongoTemplate();

    /**
     * 获取当前对象数据库存储总数
     * 
     * @return Long 对象统计总数
     */
    public long getCount();
    
    public long getPointCountByArtId(String id);
    
    public long getCommentCountByArtId(String id);
    
    public long getCountByUserId(String id);
    
    public long getCollectionCountByArtId(String id);
    
    public long getHisCountByArtId(String id);
    
    public long getCountById(String id);
    
    public List<T>  findArtAndComm(String id);

	public Collection<T> findAllByUserId(String id);
	
	

}