package com.peng.mantis.model.db.entity;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Unique;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 今天天气
 * Created by pyt on 2017/7/11.
 */
@Entity
public class TodayWeather {

    /**
     * date : 11日星期二
     * high : 高温 35℃
     * fengli : 微风级
     * low : 低温 28℃
     * fengxiang : 南风
     * type : 多云
     */
    @Unique
    private String city;
    private String todayDate;
    private String date;
    private String high;
    private String fengli;
    private String low;
    private String fengxiang;
    private String type;
    private long cacheTime;
    @Generated(hash = 871522415)
    public TodayWeather(String city, String todayDate, String date, String high,
            String fengli, String low, String fengxiang, String type,
            long cacheTime) {
        this.city = city;
        this.todayDate = todayDate;
        this.date = date;
        this.high = high;
        this.fengli = fengli;
        this.low = low;
        this.fengxiang = fengxiang;
        this.type = type;
        this.cacheTime = cacheTime;
    }
    @Generated(hash = 940679256)
    public TodayWeather() {
    }
    public String getCity() {
        return this.city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getTodayDate() {
        return this.todayDate;
    }
    public void setTodayDate(String todayDate) {
        this.todayDate = todayDate;
    }
    public String getDate() {
        return this.date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getHigh() {
        return this.high;
    }
    public void setHigh(String high) {
        this.high = high;
    }
    public String getFengli() {
        return this.fengli;
    }
    public void setFengli(String fengli) {
        this.fengli = fengli;
    }
    public String getLow() {
        return this.low;
    }
    public void setLow(String low) {
        this.low = low;
    }
    public String getFengxiang() {
        return this.fengxiang;
    }
    public void setFengxiang(String fengxiang) {
        this.fengxiang = fengxiang;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public long getCacheTime() {
        return this.cacheTime;
    }
    public void setCacheTime(long cacheTime) {
        this.cacheTime = cacheTime;
    }
}
