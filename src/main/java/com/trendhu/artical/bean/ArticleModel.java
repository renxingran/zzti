package com.trendhu.artical.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;

/**
 * 内容画像
 */
@Document(collection = "article_model")
public class ArticleModel {
    @Id
    private String art_id;
    private String title;
    private String split_title;
    private String split_content;
    private Date published_time;
    private String source;
    private ArrayList<String> features;
    private long praise_num;
    private long store_num;
    private long comment_num;

    public String getArt_id() {
        return art_id;
    }

    public void setArt_id(String art_id) {
        this.art_id = art_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSplit_title() {
        return split_title;
    }

    public void setSplit_title(String split_title) {
        this.split_title = split_title;
    }

    public String getSplit_content() {
        return split_content;
    }

    public void setSplit_content(String split_content) {
        this.split_content = split_content;
    }

    public Date getPublished_time() {
        return published_time;
    }

    public void setPublished_time(Date published_time) {
        this.published_time = published_time;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public ArrayList<String> getFeatures() {
        return features;
    }

    public void setFeatures(ArrayList<String> features) {
        this.features = features;
    }

    public long getPraise_num() {
        return praise_num;
    }

    public void setPraise_num(long praise_num) {
        this.praise_num = praise_num;
    }

    public long getStore_num() {
        return store_num;
    }

    public void setStore_num(long store_num) {
        this.store_num = store_num;
    }

    public long getComment_num() {
        return comment_num;
    }

    public void setComment_num(long comment_num) {
        this.comment_num = comment_num;
    }
}
