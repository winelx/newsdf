package com.example.administrator.newsdf.Adapter;

/**
 * Created by Administrator on 2018/1/17 0017.
 */

public class AllLMAdapter {
    String wbsPath;
    String content;
    String pointName;
    String taskId;
    String id;
    String wbsId;
    String createTime;
    String groupName;
    String cascadeId;
    String isFinish;
    String updateDate;


    public AllLMAdapter(String wbsPath, String content, String pointName, String taskId, String id, String wbsId, String createTime, String groupName, String cascadeId, String isFinish, String updateDate) {
        this.wbsPath = wbsPath;
        this.content = content;
        this.pointName = pointName;
        this.taskId = taskId;
        this.id = id;
        this.wbsId = wbsId;
        this.createTime = createTime;
        this.groupName = groupName;
        this.cascadeId = cascadeId;
        this.isFinish = isFinish;
        this.updateDate = updateDate;
    }

    public String getWbsPath() {
        return wbsPath;
    }

    public void setWbsPath(String wbsPath) {
        this.wbsPath = wbsPath;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWbsId() {
        return wbsId;
    }

    public void setWbsId(String wbsId) {
        this.wbsId = wbsId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getCascadeId() {
        return cascadeId;
    }

    public void setCascadeId(String cascadeId) {
        this.cascadeId = cascadeId;
    }

    public String getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(String isFinish) {
        this.isFinish = isFinish;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }
}
