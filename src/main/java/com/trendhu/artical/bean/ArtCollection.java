package com.trendhu.artical.bean;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.trendhu.common.bean.mongo.BaseMgBean;
import com.trendhu.common.bean.mongo.Page;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Document(collection = "collection")//文章评论表

public class ArtCollection extends BaseMgBean<ArtCollection>implements Serializable{
	
	static final long serialVersionUID = 2134744768339704457L;
		@Id
		private String id;
		private String userId;//用户ID
		private String artId;//文章ID
		private Date artCollectionDate;//收藏时间
		private  Boolean collectionSta;//收藏状态
		
		@Transient
		Page<ArtCollection> page;
		@Transient
		private Long collectionCount;//收藏量
		
		public ArtCollection() {

		}
}