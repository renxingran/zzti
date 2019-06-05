package com.trendhu.artical.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import com.trendhu.common.bean.mongo.BaseMgBean;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Document(collection = "user")
public class User extends BaseMgBean<User>implements Serializable{
	
	private static final long serialVersionUID = 2134744768339704457L;
		@Id
		private String id;
		private String userName;//用户名
		private Integer age;//年龄
		private String sex;//性别
		private String phone;//手机号
		private String email;//邮箱
		private String pwd;//密码
		private Date schTime;//入学时间
		private String useImg;//用户头像
		private Integer loginStatus;//登录状态
		private Date registrationTime;//注册时间
		@Transient
		private String oldPwd;//老密码
		@Transient
		private Long focusNum;//关注量
		@Transient
		private Long collectionNum;//收藏量
		@Transient
		private List<String> artPreference;//文章偏好  [ 就业、学习.......]
		
}