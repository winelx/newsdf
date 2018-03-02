package com.example.administrator.newsdf.Adapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/12/13 0013.
 */

public class Aduio_data {
    String replyID;//唯一标识
    String uploadId;//上传人ID
    String replyUserName;// 上传人姓名 （路径：subWbsTaskMains  -> uploadUser -> realname）
    String replyUserHeaderURL;// 上传人头像  （ 路径：subWbsTaskMains  -> uploadUser -> portrait）

    String name;///检查点
    String wbsName;//wbsName
    String uploadContent;//上传内容说明
    String updateDate;//上传时间
    String uploadAddr;//上传地点
    String leaderName;//任务负责人人
    String leaderId;//任务负责人id
    String iscallback;//是否被打回
    String callbackContent;//打回说明
    String callbackTime;//打回说明
    String callbackId;//打回人ID
    ArrayList<String> attachments;//附件list（
    String commentCount; //评论条数
      String userpath;


    public Aduio_data(String replyID, String uploadId, String replyUserName,
                      String replyUserHeaderURL, String name, String wbsName, String uploadContent,
                      String updateDate, String uploadAddr, String leaderName, String leaderId,
                      String iscallback, String callbackContent, String callbackTime,
                      String callbackId, ArrayList<String> attachments, String commentCount,String userpath
                     ) {
        this.replyID = replyID;
        this.uploadId = uploadId;
        this.replyUserName = replyUserName;
        this.replyUserHeaderURL = replyUserHeaderURL;
        this.name = name;
        this.wbsName = wbsName;
        this.uploadContent = uploadContent;
        this.updateDate = updateDate;
        this.uploadAddr = uploadAddr;
        this.leaderName = leaderName;
        this.leaderId = leaderId;
        this.iscallback = iscallback;
        this.callbackContent = callbackContent;
        this.callbackTime = callbackTime;
        this.callbackId = callbackId;
        this.attachments = attachments;
        this.commentCount = commentCount;
        this.userpath = userpath;

    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    public String getReplyID() {
        return replyID;
    }

    public void setReplyID(String replyID) {
        this.replyID = replyID;
    }

    public String getUploadId() {
        return uploadId;
    }

    public void setUploadId(String uploadId) {
        this.uploadId = uploadId;
    }

    public String getReplyUserName() {
        return replyUserName;
    }

    public void setReplyUserName(String replyUserName) {
        this.replyUserName = replyUserName;
    }

    public String getReplyUserHeaderURL() {
        return replyUserHeaderURL;
    }

    public void setReplyUserHeaderURL(String replyUserHeaderURL) {
        this.replyUserHeaderURL = replyUserHeaderURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWbsName() {
        return wbsName;
    }

    public void setWbsName(String wbsName) {
        this.wbsName = wbsName;
    }

    public String getUploadContent() {
        return uploadContent;
    }

    public void setUploadContent(String uploadContent) {
        this.uploadContent = uploadContent;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getUploadAddr() {
        return uploadAddr;
    }

    public void setUploadAddr(String uploadAddr) {
        this.uploadAddr = uploadAddr;
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

    public String getIscallback() {
        return iscallback;
    }

    public void setIscallback(String iscallback) {
        this.iscallback = iscallback;
    }

    public String getCallbackContent() {
        return callbackContent;
    }

    public void setCallbackContent(String callbackContent) {
        this.callbackContent = callbackContent;
    }

    public String getCallbackTime() {
        return callbackTime;
    }

    public void setCallbackTime(String callbackTime) {
        this.callbackTime = callbackTime;
    }

    public String getCallbackId() {
        return callbackId;
    }

    public void setCallbackId(String callbackId) {
        this.callbackId = callbackId;
    }

    public ArrayList<String> getAttachments() {
        return attachments;
    }

    public void setAttachments(ArrayList<String> attachments) {
        this.attachments = attachments;
    }

    public String getUserpath() {
        return userpath;
    }

    public void setUserpath(String userpath) {
        this.userpath = userpath;
    }
}
