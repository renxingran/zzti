package com.trendhu.artical.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.trendhu.common.bean.mongo.BaseMgBean;
import com.trendhu.common.bean.mongo.Page;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Data
@EqualsAndHashCode(callSuper = false)
@Document("art_recom")
public class ArtRecom extends BaseMgBean<ArtRecom> implements Serializable{

	private static final long serialVersionUID = 2962476481807234286L;
	private String user_id;
    private List<String> art_id_list;
    private Date set_time;
    
    @Autowired
	private Long artHisCount;//文章浏览量
    @Autowired
    private String title;
    @Autowired
	private String imgF;//文章正文的首张图片的链接地址
    @Autowired
	private String source;//文章来源 -- 网站名
    @Autowired
	private  Date artResTime;//发布时间
	@Transient
	private Page<ArtRecom> page;
	@Transient
	private String artId;

    public ArtRecom() {
    }

    public ArtRecom(String user_id, List<String> art_id_list, Date set_time) {
        this.user_id = user_id;
        this.art_id_list = art_id_list;
        this.set_time = set_time;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public List<String> getArt_id_list() {
        return art_id_list;
    }

    public void setArt_id_list(List<String> art_id_list) {
        this.art_id_list = art_id_list;
    }

    public Date getSet_time() {
        return set_time;
    }

    public void setSet_time(Date set_time) {
        this.set_time = set_time;
    }
}
