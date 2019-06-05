package com.trendhu.common.bean.mongo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.trendhu.artical.bean.ArtF;
import com.trendhu.artical.bean.ArtRecom;

/**
 * 实现分页的基类
 * 
 */
public class Page<T> implements Pageable {

    private int pageNumber = 1;
    private int pageSize = 10;
    private int totalSize;
    private int totalPage;
    private Sort sort;

    /*
     * 记录当前页中的数据条目 < 注意这里的类型是问号的原因是为了避免查询类型的相互影响 >
     */
    private List<?> list = new ArrayList<>();

    public Page() {
    	
    }
    public Page(List<ArtF> artL, int i, int j, int k) {
      			this.list = artL;
      			this.pageNumber = i;
      			this.pageSize =  j;
      			this.totalSize = k;
    }

    public Page(List<ArtRecom> artL, int i, int j) {
			this.list = artL;
			this.pageNumber = i;
			this.pageSize =  j;
}
    
    
    public Page(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    @Override
    public Pageable first() {
        return null;
    }

    @Override
    public long getOffset() {
        return (getPageNumber() - 1) * getPageSize();
    }

    @Override
    public int getPageNumber() {
        return pageNumber;
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }

    @Override
    public Sort getSort() {
        return sort;
    }

    @Override
    public boolean hasPrevious() {
        return false;
    }

    @Override
    public Pageable next() {
        return null;
    }

    @Override
    public Pageable previousOrFirst() {
        return null;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
        this.totalPage = totalSize % pageSize == 0 ? totalSize / pageSize
                : totalSize / pageSize + 1;
    }

    public int getTotalPage() {

        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }

}
