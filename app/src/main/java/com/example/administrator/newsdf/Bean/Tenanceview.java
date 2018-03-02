package com.example.administrator.newsdf.Bean;

/**
 * Created by Administrator on 2017/12/12 0012.
 */

public class Tenanceview {

    String id;
    String name;
    String unmber;

    public Tenanceview(String id, String name, String unmber) {
        this.id = id;
        this.name = name;
        this.unmber = unmber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnmber() {
        return unmber;
    }

    public void setUnmber(String unmber) {
        this.unmber = unmber;
    }
}
