package com.trendhu.artical.bean;

import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.trendhu.common.bean.mongo.BaseMgBean;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Document(collection = "fm")//频道表
public class FM  extends BaseMgBean<FM> implements Serializable{
	
	static final long serialVersionUID = 2134744768339704457L;
		@Id
		private String id;
		private String fmname;//频道名字
		private String artId;//文章ID
		@Transient
		private String artContent;
		
		
		public FM() {
			
		}
}