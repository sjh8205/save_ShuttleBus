package com.example.sonhyejin.save_shuttlebus;

import android.graphics.drawable.Drawable;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class ListViewQR {
    private Drawable status;
    private String nameStr;
    private String classStr;
    private ArrayList<String> names;

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

    public int getCount() { return names.size(); }

}
