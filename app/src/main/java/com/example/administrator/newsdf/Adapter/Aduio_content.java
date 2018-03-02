package com.example.administrator.newsdf.Adapter;

/**
 * Created by Administrator on 2017/12/13 0013.
 */
/**
 * description:
 * @author lx
 * date: 2018/2/6 0006 下午 1:58
 * update: 2018/2/6 0006
 * version:
*/

public class Aduio_content {
    //唯一标识
    String id;
    ///检查点
    String name;
    //状态
    String status;
    //推送内容
    String content;
    //负责人
    String leaderName;
    //负责人ID
    String leaderId;
    //是否已读
    String isread;
    //创建人ID  (路径：wtMain –> createBy -> id)
    String createByUserID;
    //是否被打回过
    String iscallback;
    //创建时间
    String createDate;
    //wbsname
    String wbsName;
    //转交id
    String changeId;
    //最后时间
    String backdata;


    public Aduio_content(String id, String name, String status, String content, String leaderName,
                         String leaderId, String isread, String createByUserID,
                         String iscallback, String createDate,
                         String wbsName, String changeId, String backdata) {
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
        this.backdata = backdata;

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

    public String getIscallback() {
        return iscallback;
    }

    public void setIscallback(String iscallback) {
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

    public String getBackdata() {
        return backdata;
    }

    public void setBackdata(String backdata) {
        this.backdata = backdata;
    }


}
