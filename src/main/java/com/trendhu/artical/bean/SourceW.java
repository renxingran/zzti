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
@Document(collection = "sourceW")	//来源维表
public class SourceW extends BaseMgBean<SourceW>implements Serializable{

	private static final long serialVersionUID = -4355611527484134170L;
	@Id
	private String id;
	private String sourceName;//名称
	private String imgSouce;//图标网址
	private String sourceURL;//网站URL
	@Transient
	private Boolean focusSta;//关注状态
	@Transient
	private Long  focusNum;//关注量即为粉丝量
	@Transient
	private Long releaseNum;//发布量
	@Transient
	private Date focusDate;
	
	
	public SourceW() {
		
	}
}