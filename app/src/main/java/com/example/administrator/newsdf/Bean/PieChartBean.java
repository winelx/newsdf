package com.example.administrator.newsdf.Bean;

/**
 * Created by Administrator on 2018/1/15 0015.
 */

public class PieChartBean {
    private String valuer;      //说明
    private Float angle;    //占的大小
    private Integer color;      //颜色值

    public PieChartBean(String valuer, Float angle, Integer color) {
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

    public Integer getColor() {
        return color;
    }

    public void setColor(Integer color) {
        this.color = color;
    }
}
