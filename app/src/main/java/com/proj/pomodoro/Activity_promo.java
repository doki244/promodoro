package com.proj.pomodoro;

import android.graphics.Color;

public class Activity_promo {
    private String title;
    private int min,short_rest,long_rest,Short_rest_step,id;

    public Activity_promo(String title, int min, int short_rest, int long_rest, int short_rest_step) {
        this.title = title;
        this.min = min;
        this.short_rest = short_rest;
        this.long_rest = long_rest;
        Short_rest_step = short_rest_step;
    }
    public Activity_promo(String title, int min, int short_rest, int long_rest, int short_rest_step,int id) {
        this.title = title;
        this.min = min;
        this.short_rest = short_rest;
        this.long_rest = long_rest;
        Short_rest_step = short_rest_step;
        this.id= id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getShort_rest() {
        return short_rest;
    }

    public void setShort_rest(int short_rest) {
        this.short_rest = short_rest;
    }

    public int getLong_rest() {
        return long_rest;
    }

    public void setLong_rest(int long_rest) {
        this.long_rest = long_rest;
    }

    public int getShort_rest_step() {
        return Short_rest_step;
    }

    public void setShort_rest_step(int short_rest_step) {
        Short_rest_step = short_rest_step;
    }



    @Override
    public String toString() {
        return "subject{" +
                "title='" + title + '\'' +
                ", min=" + min +
                ", short_rest=" + short_rest +
                ", long_rest=" + long_rest +
                ", Short_rest_step=" + Short_rest_step +
                '}';
    }
}
