package com.example.sonhyejin.save_shuttlebus;

public class headJoin {
    String headNum;
    String kindNum;
    String kindName;
    public headJoin(){}
    public headJoin(String headNum,String kindNum,String kindName){
        this.headNum=headNum;
        this.kindName=kindName;
        this.kindNum=kindNum;
    }
    public String getHeadNum() {
        return headNum;
    }

    public String getKindName() {
        return kindName;
    }

    public String getKindNum() {
        return kindNum;
    }

    public void setHeadNum(String headNum) {
        this.headNum = headNum;
    }

    public void setKindName(String kindName) {
        this.kindName = kindName;
    }

    public void setKindNum(String kindNum) {
        this.kindNum = kindNum;
    }

}
