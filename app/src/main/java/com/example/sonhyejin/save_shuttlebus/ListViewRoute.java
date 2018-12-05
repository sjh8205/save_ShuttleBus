package com.example.sonhyejin.save_shuttlebus;

import android.graphics.drawable.Drawable;

public class ListViewRoute {

    private Drawable stationImg;
    private Drawable busImg;
    private String stStr;

    public void setstimg(Drawable stimg){
        stationImg = stimg;
    }

    public void setbusimg(Drawable busimg){
        busImg = busimg;
    }

    public void setstname(String stopStr){
        stStr = stopStr;
    }

    public Drawable getstimg(){
        return this.stationImg;
    }

    public Drawable getbusimg(){
        return this.busImg;
    }

    public String getstname(){
        return this.stStr;
    }

}
