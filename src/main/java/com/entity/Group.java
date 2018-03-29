package com.entity;

public class Group extends Entity {
    private int id;
    private String name;
    private String periodStart;
    private String periodFinish;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPeriodStart() {
        return periodStart;
    }

    public void setPeriodStart(String periodStart) {
        this.periodStart = periodStart;
    }

    public String getPeriodFinish() {
        return periodFinish;
    }

    public void setPeriodFinish(String periodFinish) {
        this.periodFinish = periodFinish;
    }
}
