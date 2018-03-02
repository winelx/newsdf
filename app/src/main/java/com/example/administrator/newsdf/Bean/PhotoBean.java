package com.example.administrator.newsdf.Bean;

/**
 * Created by Administrator on 2017/12/26 0026.
 */

public class PhotoBean {
    String id;//Id
    String filePath;    //路径
    String drawingNumber;    //图纸编号
    String drawingName;//	图纸名称
    String drawingGroupName;    //图册名称

    public PhotoBean(String id, String filePath, String drawingNumber, String drawingName, String drawingGroupName) {
        this.id = id;
        this.filePath = filePath;
        this.drawingNumber = drawingNumber;
        this.drawingName = drawingName;
        this.drawingGroupName = drawingGroupName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getDrawingNumber() {
        return drawingNumber;
    }

    public void setDrawingNumber(String drawingNumber) {
        this.drawingNumber = drawingNumber;
    }

    public String getDrawingName() {
        return drawingName;
    }

    public void setDrawingName(String drawingName) {
        this.drawingName = drawingName;
    }

    public String getDrawingGroupName() {
        return drawingGroupName;
    }

    public void setDrawingGroupName(String drawingGroupName) {
        this.drawingGroupName = drawingGroupName;
    }
}
