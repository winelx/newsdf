package com.example.administrator.newsdf.Bean;

/**
 * Created by Administrator on 2017/11/21 0021.
 */

public class Tab {
    private int title;
    private int icon;
    private Class fragemnt;

    public Tab(Class fragemnt, int title, int icon) {
        this.fragemnt = fragemnt;
        this.title = title;
        this.icon = icon;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public Class getFragemnt() {
        return fragemnt;
    }

    public void setFragemnt(Class fragemnt) {
        this.fragemnt = fragemnt;
    }
}
