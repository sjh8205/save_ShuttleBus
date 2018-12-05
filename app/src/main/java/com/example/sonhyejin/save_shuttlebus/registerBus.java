package com.example.sonhyejin.save_shuttlebus;

public class registerBus {
    //private  String Routine;
    private String Station;
    private String time;
    private boolean allCheck;
    private boolean busishere;

    public registerBus(){}

    public registerBus(String Station, String time,boolean allCheck,boolean busishere){
        //this.Routine=Routine;
        this.Station=Station;
        this.time=time;
        this.allCheck=allCheck;
        this.busishere=busishere;
    }

    public boolean isAllCheck() {
        return allCheck;
    }

    public void setAllCheck(boolean allCheck) {
        this.allCheck = allCheck;
    }

    public boolean isBusishere(){return busishere;}

    public void SetBusishere(boolean busishere){ this.busishere = busishere;}

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
