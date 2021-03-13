package com.bcu.cloudmonitor;

public class Job {
    private String id;
    private String state;
    private String startTime;
    private String endTime;

    public Job() {
    }

    public Job(String id, String state, String startTime, String endTime) {
        this.id = id;
        this.state = state;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }


}
