package com.example.administrator.newsdf.Bean;

/**
 * 作者：winelx
 * 时间：2017/12/4 0004:上午 9:18
 * 说明：
 */
public class Push_item {

    String content;//所属名称
    String id;//ID
    String label;//内容
    String preconditionsCurid;//先决条件
    String leaderName;//负责人
    String sendTime;//发送时间
    String sendTimes;//发送次数
    private Boolean checked;


    public Push_item(String content, String id, String label, String preconditionsCurid, String leaderName, String sendTime, String sendTimes, Boolean checked) {
        this.content = content;
        this.id = id;
        this.label = label;
        this.preconditionsCurid = preconditionsCurid;
        this.leaderName = leaderName;
        this.sendTime = sendTime;
        this.sendTimes = sendTimes;
        this.checked = checked;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getPreconditionsCurid() {
        return preconditionsCurid;
    }

    public void setPreconditionsCurid(String preconditionsCurid) {
        this.preconditionsCurid = preconditionsCurid;
    }

    public String getLeaderName() {
        return leaderName;
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getSendTimes() {
        return sendTimes;
    }

    public void setSendTimes(String sendTimes) {
        this.sendTimes = sendTimes;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }
}
