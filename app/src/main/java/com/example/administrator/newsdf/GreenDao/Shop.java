package com.example.administrator.newsdf.GreenDao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

/**
 * Created by handsome on 2016/4/8.
 */
@Entity
public class Shop {

    public static final int TYPE_CART = 0x01;
    public static final int TYPE_LOVE = 0x02;

    //不能用int
    @Id(autoincrement = true)
    private Long id;

    @Property(nameInDb = "price")
    private String user; //用户
    private String project;//项目
    private String name;//保存名字
    private String timme;//时间
    private String image_url; //图片
    private String content;//内容
    private String websid;//wbsID
    private String checkname;//检查点ID
    private String checkid;//检查点
    private int type;

    @Generated(hash = 1546035254)
    public Shop(Long id, String user, String project, String name, String timme,
                String image_url, String content, String websid, String checkname,
                String checkid, int type) {
        this.id = id;
        this.user = user;
        this.project = project;
        this.name = name;
        this.timme = timme;
        this.image_url = image_url;
        this.content = content;
        this.websid = websid;
        this.checkname = checkname;
        this.checkid = checkid;
        this.type = type;
    }

    @Generated(hash = 633476670)
    public Shop() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser() {
        return this.user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getProject() {
        return this.project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTimme() {
        return this.timme;
    }

    public void setTimme(String timme) {
        this.timme = timme;
    }

    public String getImage_url() {
        return this.image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWebsid() {
        return this.websid;
    }

    public void setWebsid(String websid) {
        this.websid = websid;
    }

    public String getCheckname() {
        return this.checkname;
    }

    public void setCheckname(String checkname) {
        this.checkname = checkname;
    }

    public String getCheckid() {
        return this.checkid;
    }

    public void setCheckid(String checkid) {
        this.checkid = checkid;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }


}
