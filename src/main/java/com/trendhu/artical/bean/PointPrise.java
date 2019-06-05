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
@Document(collection = "pointPrise")

public class PointPrise extends BaseMgBean<PointPrise>implements Serializable{
	
	static final long serialVersionUID = 2134744768339704457L;
		@Id
		private String id;
		private String userId;//用户ID
		private String artId;//文章ID
		private Boolean pointPriseSta;//点赞状态
		private Date pointPriseDate;//点赞时间
		
		@Transient
		private Long pointCount;//点赞量
		
		public PointPrise() {
			
		}
}