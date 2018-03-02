package com.example.administrator.newsdf.Bean;

/**
 * Created by Administrator on 2017/12/27 0027.
 */

public class NotupBean {
    String id;
    String Title;
    String titme;
    String content;
    String wbsnam;

    public NotupBean(String id, String title, String titme, String content, String wbsnam) {
        this.id = id;
        Title = title;
        this.titme = titme;
        this.content = content;
        this.wbsnam = wbsnam;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getTitme() {
        return titme;
    }

    public void setTitme(String titme) {
        this.titme = titme;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWbsnam() {
        return wbsnam;
    }

    public void setWbsnam(String wbsnam) {
        this.wbsnam = wbsnam;
    }
}
