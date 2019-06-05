package com.trendhu.artical.bean;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.trendhu.common.bean.mongo.BaseMgBean;

import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode(callSuper = false)
@Document(collection="administer")//管理员表
public class Administer  extends BaseMgBean<Administer> implements Serializable{

	private static final long serialVersionUID = 2825545658768860966L;
	@Id
	private String id;
	private String admAccount;//管理员账号
	private String admPwd;//密码
	private Integer admSta;//管理员登录状态
	
	public Administer() {
		
	}
	
}
