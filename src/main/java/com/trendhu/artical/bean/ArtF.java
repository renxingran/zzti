package com.trendhu.artical.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.SafeHtml.Tag;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.trendhu.common.bean.mongo.BaseMgBean;
import com.trendhu.common.bean.mongo.Page;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Document(collection = "artF")//文章初表
public class ArtF extends BaseMgBean<ArtF>implements Serializable{
	
	private static final long serialVersionUID = 2134744768339704457L;
		@Id
		private String id;
		private  Date artResTime;//发布时间
		private String title;//文章标题
		private String content;//文章内容
		private String url;//文章URL
		private String source;//文章来源 -- 网站名
		private String imgF;//文章正文的首张图片的链接地址
		private Integer examineN;//审核状态码,待审核0、审核通过1、审核不通过2  默认为0
		private Integer releaseN;//发布状态码   待发布0、发布成功1  默认为0
		private Date admArtResTime;//管理员发布新闻的时间
		private String articleStatus;//文章状态--空字段，供前端使用
		
		@Transient
		private Page<ArtF> page;
		@Transient
		private List<ArtComment> artcomments;
		@Transient
		private ArtComment comment;
		@Transient
		private Long commentCount;
		@Transient
		private Long pointCount;
		@Transient
		private Long collectionCount;
		@Transient
		private  Boolean collectionSta;//收藏状态
		@Transient
		private  Boolean pointPriseSta;//点赞状态
		@Transient
		private  Boolean focusSta;//关注状态
		@Transient
		private Date focusDate;//关注时间
		@Transient
		private Date artCollectionDate;//收藏时间
		@Transient
		private String userId;
		@Transient
		private Long artHisCount;//文章浏览量
		@Transient
		private Date pointPriseDate;//点赞时间
		@Transient
	    private ArrayList<String> features;//新闻标签
	    @Transient
	    private Boolean asc; // 显示的时候，是否是正序显示
	    @Transient
	    private String sortKey; // 排序的key值应该用什么
	    @Transient
	    private String imgSource;//文章的来源的图片
	    @Transient
	    private String sourceId;
	    @Transient
	    private Date comTime;
	    @Transient
	    private String keyWords;// 搜索关键词
		public ArtF() {
			
		}
}