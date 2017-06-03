package com.lzj.domain;

/**
 * Created by Administrator on 2017/5/25 0025.
 */
public class WebEntity {
    private String id;
    private String url;
    public WebEntity(String id, String url) {
        this.id = id;
        this.url = url;
    }

    public WebEntity() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "WebEntity{" +
                "id='" + id + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
