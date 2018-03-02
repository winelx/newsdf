package com.example.administrator.newsdf.View;

/**
 * Created by Administrator on 2018/1/16 0016.
 */

public class PieChartBeans {
    private String valuer;      //说明
    private Float angle;    //占的大小
    private String color;      //颜色值

    public PieChartBeans(String valuer, Float angle, String color) {
        this.valuer = valuer;
        this.angle = angle;
        this.color = color;
    }

    public String getValuer() {
        return valuer;
    }

    public void setValuer(String valuer) {
        this.valuer = valuer;
    }

    public Float getAngle() {
        return angle;
    }

    public void setAngle(Float angle) {
        this.angle = angle;
    }

    public String getColor() {
        return color;
    }

}
