package com.example.sonhyejin.save_shuttlebus;

public class PeopleListViewItem {
    private String nameStr;
    private String classStr;
    private String numStr;

    public void setpName(String  name){
        nameStr = name;
    }

    public void setpClass(String pClass){
        classStr = pClass;
    }

    public void setpNum(String num){
        numStr = num;
    }

    public String getpName(){
        return this.nameStr;
    }

    public String getpClass(){
        return this.classStr;
    }

    public String getpNum(){
        return this.numStr;
    }
}
