package com.example.sonhyejin.save_shuttlebus;

public class registerBus {
    //private  String Routine;
    private String Station;
    private String time;
    private boolean allCheck;

    public registerBus(){}

    public registerBus(String Station, String time,boolean allCheck){
        //this.Routine=Routine;
        this.Station=Station;
        this.time=time;
        this.allCheck=allCheck;
    }

    public boolean isAllCheck() {
        return allCheck;
    }

    public void setAllCheck(boolean allCheck) {
        this.allCheck = allCheck;
    }

    public String getTime() {
        return time;
    }

    /*public String getRoutine() {
        return Routine;
    }*/

    public String getStation() {
        return Station;
    }

    /*public void setRoutine(String routine) {
        Routine = routine;
    }*/

    public void setStation(String station) {
        Station = station;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
