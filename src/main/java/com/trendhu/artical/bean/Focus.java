package com.trendhu.artical.bean;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.trendhu.common.bean.mongo.BaseMgBean;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Document(collection = "focus")//关注表
public class Focus extends BaseMgBean<Focus> implements Serializable{
	
	private static final long serialVersionUID = 2667732457391866927L;
	@Id
	private String id;
	private String userId;//用户ID
	private String sourceId;//来源ID
	private Boolean focusSta;//关注状态
	private Date focusDate;//关注时间
	private String artId;//通过哪篇文章关注了文章来源网站
	
	public Focus() {
		
	}
}
