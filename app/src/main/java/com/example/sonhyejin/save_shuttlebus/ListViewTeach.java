package com.example.sonhyejin.save_shuttlebus;

import android.graphics.drawable.Drawable;

public class ListViewTeach {
    private Drawable img;
    private String nameStr;
    private String classStr;
    private String numStr;

    public void setimg(Drawable stimg){
        img = stimg;
    }

    public void settName(String  name){
        nameStr = name;
    }

    public void settClass(String pClass){
        classStr = pClass;
    }

    public void settNum(String num){
        numStr = num;
    }

    public Drawable getimg(){
        return this.img;
    }

    public String gettName(){
        return this.nameStr;
    }

    public String gettClass(){
        return this.classStr;
    }

    public String gettNum(){
        return this.numStr;
    }
}
