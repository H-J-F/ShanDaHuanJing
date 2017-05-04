package com.example.a11691.shandahuanjing;

import java.io.Serializable;

/**
 * Created by 11691 on 2017/4/28.
 */

public class MarkerInfoUtil implements Serializable{
    private static final long serialVersionUID = 8633299996744734593L;

    private double latitude;//纬度
    private double longitude;//经度
    private String name;//名字
    private String table;
    private int tableIndex;
    //构造方法
    public MarkerInfoUtil(double latitude, double longitude, String name, String table, int tableIndex) {
        super();
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.table = table;
        this.tableIndex = tableIndex;
    }
    //toString方法
    @Override
    public String toString() {
        return "MarkerInfoUtil [latitude=" + latitude + ", longitude=" + longitude + ", name=" + name + "]";
    }

    //getter setter
    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTable(){
        return this.table;
    }

    public int getTableIndex(){
        return this.tableIndex;
    }
}
