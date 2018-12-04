package com.example.sonhyejin.save_shuttlebus;

public class registerTeacher {
    String name;
    String tClass;
    String phone;
    String imgPath;
    boolean todayTeacher;

    public registerTeacher(){}
    public registerTeacher(String name,String tClass,String phone,String imgPath,
                           boolean todayTeacher){
        this.name=name;
        this.tClass=tClass;
        this.phone=phone;
        this.imgPath=imgPath;
        this.todayTeacher=todayTeacher;
    }
    public registerTeacher(String name,String tClass,String phone,String imgPath){
        this.name=name;
        this.tClass=tClass;
        this.phone=phone;
        this.imgPath=imgPath;
    }

    public boolean isTodayTeacher() {
        return todayTeacher;
    }

    public void setTodayTeacher(boolean todayTeacher) {
        this.todayTeacher = todayTeacher;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String gettClass() {
        return tClass;
    }
    public String getImgPath(){return imgPath;}
    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void settClass(String tClass) {
        this.tClass = tClass;
    }

    public void setImgPath(String imgPath){this.imgPath=imgPath;}
}
