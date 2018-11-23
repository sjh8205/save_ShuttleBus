package com.example.sonhyejin.save_shuttlebus;

import android.graphics.drawable.Drawable;

public class ListViewQR {
    private Drawable status;
    private String nameStr;
    private String classStr;

    public void setimg(Drawable stimg){
        status = stimg;
    }

    public void setsName(String  sName){
        nameStr = sName;
    }

    public void setsClass(String sClass){
        classStr = sClass;
    }

    public Drawable getimg(){
        return this.status;
    }

    public String getsName(){
        return this.nameStr;
    }

    public String getsClass(){
        return this.classStr;
    }

}
