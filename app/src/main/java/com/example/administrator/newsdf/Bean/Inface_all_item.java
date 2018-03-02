package com.example.administrator.newsdf.Bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/1/19 0019.
 */

public class Inface_all_item {
    String wbsPath;
    String updateDate;
    String content;
    String taskId;
    String id;
    String wbsId;
    String createTime;
    String groupName;
    int isFinish;//状态
    String upload_time;
    String userid;
    String uploador;//用户名
    String upload_content;//上传内容
    String upload_addr;//上传地址
    String portrait;//人物头像
    ArrayList<String> upload;
    int comments;

    public Inface_all_item(String wbsPath, String updateDate, String content,
                           String taskId, String id, String wbsId, String createTime,
                           String groupName, int isFinish, String upload_time, String userid,
                           String uploador, String upload_content, String upload_addr,
                           String portrait, ArrayList<String> upload, int comments) {
        this.wbsPath = wbsPath;
        this.updateDate = updateDate;
        this.content = content;
        this.taskId = taskId;
        this.id = id;
        this.wbsId = wbsId;
        this.createTime = createTime;
        this.groupName = groupName;
        this.isFinish = isFinish;
        this.upload_time = upload_time;
        this.userid = userid;
        this.uploador = uploador;
        this.upload_content = upload_content;
        this.upload_addr = upload_addr;
        this.portrait = portrait;
        this.upload = upload;
        this.comments = comments;
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

    public int getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(int isFinish) {
        this.isFinish = isFinish;
    }

    public String getUpload_time() {
        return upload_time;
    }

    public void setUpload_time(String upload_time) {
        this.upload_time = upload_time;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUploador() {
        return uploador;
    }

    public void setUploador(String uploador) {
        this.uploador = uploador;
    }

    public String getUpload_content() {
        return upload_content;
    }

    public void setUpload_content(String upload_content) {
        this.upload_content = upload_content;
    }

    public String getUpload_addr() {
        return upload_addr;
    }

    public void setUpload_addr(String upload_addr) {
        this.upload_addr = upload_addr;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public ArrayList<String> getUpload() {
        return upload;
    }

    public void setUpload(ArrayList<String> upload) {
        this.upload = upload;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }
}
