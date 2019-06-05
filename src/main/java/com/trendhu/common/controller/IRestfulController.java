package com.trendhu.common.controller;

import java.util.List;
import com.trendhu.common.bean.http.ResponseBean;

public interface IRestfulController<T> {
    /**
     * 增加
     * 
     * @param object
     *            T 数据
     * @return ResponseBean 响应结果
     */
    public abstract ResponseBean insert(T object);

    /**
     * 批量增加
     * 
     * @param object
     *            List<T> 数据
     * @return ResponseBean 响应结果
     */
    public abstract ResponseBean insertAll(List<T> object);
    
    /**
     * 删除，根据Id删除记录
     * 
     * @param id
     *            String 对象Id
     * @return ResponseBean 响应结果
     */
    public abstract ResponseBean remove(String id);
    
    /**
     * 批量删除，根据Id列表删除多条记录
     * 
     * @param idList
     *            List<String> 对象Id列表
     * @return ResponseBean 响应结果
     */
    ResponseBean removeAll(List<String> idList);

    /**
     * 修改
     * 
     * @param object
     *            T 数据
     * @return ResponseBean 响应结果
     */
    public abstract ResponseBean update(T object);

    /**
     * 根据Id查询数据
     * 
     * @param id
     *            String 对象Id
     * @return ResponseBean 响应结果
     */
    public abstract ResponseBean findOne(String id);

    /**
     * 条件查询
     * 
     * @param object
     *            T 查询条件
     * @return ResponseBean 响应结果
     */
    public abstract ResponseBean query(T object);
    
    
    /**
     * 查询数据总数
     * 
     * @return ResponseBean 响应结果
     */
    public abstract ResponseBean count();

	public abstract ResponseBean findArtAndComm(String id);

	public abstract ResponseBean queryMyCollection(T object);

	public abstract ResponseBean queryArtList(T object);

	public abstract ResponseBean queryMyComments(T object);


}
