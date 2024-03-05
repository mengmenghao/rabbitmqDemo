package com.lmh.rabbit.exchange;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lmh
 * @description: 一句话描述该类的功能
 * @projectName: rabbitmq
 * @className: News
 * @createDate: 2024/3/5 20:39
 */
public class News implements Serializable {
    private String source;
    private String title;
    private Date createTime;
    private String content;

    public News(String source, String title, Date createTime, String content) {
        this.source = source;
        this.title = title;
        this.createTime = createTime;
        this.content = content;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
