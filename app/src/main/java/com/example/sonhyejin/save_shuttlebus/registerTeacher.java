package com.example.sonhyejin.save_shuttlebus;

public class registerTeacher {
    String name;
    String tClass;
    String phone;

    public registerTeacher(){}
    public registerTeacher(String name,String tClass,String phone){
        this.name=name;
        this.tClass=tClass;
        this.phone=phone;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void settClass(String tClass) {
        this.tClass = tClass;
    }
}
