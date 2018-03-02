package com.example.administrator.newsdf.Bean;

/**
 * Created by Administrator on 2017/12/8 0008.
 */

public class Audio {

    String name;
    String content;

    public Audio(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

