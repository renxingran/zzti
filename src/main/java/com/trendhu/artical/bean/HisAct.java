package com.trendhu.artical.bean;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.trendhu.common.bean.mongo.BaseMgBean;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Document(collection = "hisAct")//浏览历史行为表
public class HisAct extends BaseMgBean<HisAct> implements Serializable{

	private static final long serialVersionUID = -8632225357727241165L;
	@Id
	private String id;
	private String userId;//用户ID
	private String artId;//文章ID
	private Date browseTime;//浏览时间
	
	public  HisAct() {
		
	}
	
//	5cda76b6e731f036432d8e8b
//	5cdc10334f0964e8c3239240
}
