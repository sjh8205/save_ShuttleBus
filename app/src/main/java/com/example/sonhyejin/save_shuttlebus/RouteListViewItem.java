package com.example.sonhyejin.save_shuttlebus;

import android.graphics.drawable.Drawable;

public class RouteListViewItem {

    private Drawable img;
    private String stStr;

    public void setimg(Drawable stimg){
        img = stimg;
    }

    public void setstname(String stopStr){
        stStr = stopStr;
    }

    public Drawable getimg(){
        return this.img;
    }

    public String getstname(){
        return this.stStr;
    }

}
