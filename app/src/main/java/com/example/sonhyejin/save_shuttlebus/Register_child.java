package com.example.sonhyejin.save_shuttlebus;

public class Register_child {
    public String childName;
    public String childClass;
    public String childPhoneNum;
    public String childBusStation;
    public Boolean childBus;
    public int childOnBus;
    public String qrUri;

    public Register_child(){}

    public Register_child(String Name, String Class, String Phone, String Station,
                          Boolean Ride, int status,String qr){
        this.childName=Name;
        this.childClass=Class;
        this.childPhoneNum=Phone;
        this.childBusStation=Station;
        this.childBus=Ride;
        this.childOnBus=status;
        this.qrUri=qr;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public void setChildClass(String childClass) {
        this.childClass = childClass;
    }

    public void setChildPhoneNum(String childPhoneNum) {
        this.childPhoneNum = childPhoneNum;
    }

    public void setChildBusStation(String childBusStation) {
        this.childBusStation = childBusStation;
    }

    public void setChildBus(boolean childBus) {
        this.childBus = childBus;
    }

    public void setChildOnBus(int childOnBus) {
        this.childOnBus = childOnBus;
    }

    public void setQrUri(String qrUri) {
        this.qrUri = qrUri;
    }

    public String getQrUri() {
        return qrUri;
    }

    public String getChildName() {
        return childName;
    }

    public String getChildClass() {
        return childClass;
    }

    public String getChildPhoneNum() {
        return childPhoneNum;
    }

    public String getChildBusStation() {
        return childBusStation;
    }

    public int isChildOnBus() {
        return childOnBus;
    }

    public Boolean isChildBus() {
        return childBus;
    }

}
