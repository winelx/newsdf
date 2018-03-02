package com.example.administrator.newsdf.Bean;

/**
 * @author ：winelx
 * 时间：2017/11/27 0027:下午 14:54
 * 说明：
 */
public class List_interface {
    String id;
    //任务id
    String taskId;
    ///检查点id
    String cascadeId;
    //任务状态，0 和1;
    String isFinish;
    //推送内容
    String content;
    //负责人
    String groupName;
    //创建时间;
    String createTime;
    //检查点名称
    String pointName;
    // Wbs路径;
    String wbsPath;
    //wbsID
    String wbsId;
    public List_interface(String id, String taskId, String cascadeId, String isFinish,
                          String content, String groupName, String createTime,
                          String pointName, String wbsPath,String wbsId) {
        this.id = id;
        this.taskId = taskId;
        this.cascadeId = cascadeId;
        this.isFinish = isFinish;
        this.content = content;
        this.groupName = groupName;
        this.createTime = createTime;
        this.pointName = pointName;
        this.wbsPath = wbsPath;
        this.wbsId =wbsId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public String getWbsPath() {
        return wbsPath;
    }

    public void setWbsPath(String wbsPath) {
        this.wbsPath = wbsPath;
    }

    public String getWbsId() {
        return wbsId;
    }

    public void setWbsId(String wbsId) {
        this.wbsId = wbsId;
    }
}
