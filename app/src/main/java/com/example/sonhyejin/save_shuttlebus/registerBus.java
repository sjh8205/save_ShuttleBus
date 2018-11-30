package com.example.sonhyejin.save_shuttlebus;

public class registerBus {
    String Routine;
    String Station;
    int time;
    public registerBus(){}
    public registerBus(String routine,String station,int time){
        this.Routine=routine;
        this.Station=station;
        this.time=time;
    }

    public int getTime() {
        return time;
    }

    public String getRoutine() {
        return Routine;
    }

    public String getStation() {
        return Station;
    }

    public void setRoutine(String routine) {
        Routine = routine;
    }

    public void setStation(String station) {
        Station = station;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
