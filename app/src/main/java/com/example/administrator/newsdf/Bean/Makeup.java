package com.example.administrator.newsdf.Bean;

/**
 * Created by Administrator on 2017/12/29 0029.
 */
//切换组织
public class Makeup {
    String name;
    String id;

    public Makeup(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
