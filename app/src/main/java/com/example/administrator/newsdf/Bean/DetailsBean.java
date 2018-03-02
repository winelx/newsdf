package com.example.administrator.newsdf.Bean;

/**
 * Created by Administrator on 2017/12/13 0013.
 */

public class DetailsBean {

    String id;//唯一id
    String name;//检查点
    String status;//状态
    String content;//推送内容
    String leaderName;//负责人
    String leaderId;//负责人ID
    String isread;//是否已读
    String createByUserID;//创建人ID
    boolean iscallback;//是否大回复哦
    String createDate; //创建时间
    String wbsName;//wbsnama
    String changeId;//转交ID

    public DetailsBean(String id, String name, String status, String content, String leaderName, String leaderId, String isread, String createByUserID, boolean iscallback, String createDate, String wbsName, String changeId) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.content = content;
        this.leaderName = leaderName;
        this.leaderId = leaderId;
        this.isread = isread;
        this.createByUserID = createByUserID;
        this.iscallback = iscallback;
        this.createDate = createDate;
        this.wbsName = wbsName;
        this.changeId = changeId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLeaderName() {
        return leaderName;
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }

    public String getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(String leaderId) {
        this.leaderId = leaderId;
    }

    public String getIsread() {
        return isread;
    }

    public void setIsread(String isread) {
        this.isread = isread;
    }

    public String getCreateByUserID() {
        return createByUserID;
    }

    public void setCreateByUserID(String createByUserID) {
        this.createByUserID = createByUserID;
    }

    public boolean iscallback() {
        return iscallback;
    }

    public void setIscallback(boolean iscallback) {
        this.iscallback = iscallback;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getWbsName() {
        return wbsName;
    }

    public void setWbsName(String wbsName) {
        this.wbsName = wbsName;
    }

    public String getChangeId() {
        return changeId;
    }

    public void setChangeId(String changeId) {
        this.changeId = changeId;
    }
}
