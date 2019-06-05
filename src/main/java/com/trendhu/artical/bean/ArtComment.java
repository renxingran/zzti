package com.trendhu.artical.bean;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.SafeHtml.Tag;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.trendhu.common.bean.mongo.BaseMgBean;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Document(collection = "artComment")//文章评论表
/**
 * 注意：实体类  不能用int、char等
 * @ClassName:  ArtComment   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 任星燃 
 * @date:   May 14, 2019 4:10:11 PM   
 *
 */
public class ArtComment extends BaseMgBean<ArtComment>implements Serializable{
	
	static final long serialVersionUID = 2134744768339704457L;
		@Id
		private String id;//评论ID
		private String userId;//用户ID
		private String artId;//文章ID
		private String comContent;//评论内容
		private Date comTime;//评论时间
		private Integer comSta;//评论状态  0否、1是
		@Transient
		private Long commentCount;
		@Transient
		private String userName;
		@Transient
		private String useImg;
		
		public ArtComment() {
			
		}
}