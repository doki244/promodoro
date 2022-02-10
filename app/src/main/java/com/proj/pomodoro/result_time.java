package com.proj.pomodoro;

import java.util.Date;

public class result_time {
    private String title;
    private long total_focus_time;
    private long total_rest;
    private Date date;
    private int id;

    public result_time(String title, long focus_time, long total_rest, Date date, int id) {
        this.title = title;
        this.total_focus_time = focus_time;
        this.total_rest = total_rest;
        this.date = date;
        this.id = id;
    }
    public result_time(String title, long focus_time, long total_rest,  Date date) {
        this.title = title;
        this.total_focus_time = focus_time;
        this.total_rest = total_rest;
        this.date = date;

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

    public void setFocus_time(int focus_time) {
        this.total_focus_time = focus_time;
    }

    public long getTotal_rest() {
        return total_rest;
    }

    public void setTotal_rest(int total_rest) {
        this.total_rest = total_rest;
    }

    public long getTotal_focus_time() {
        return total_focus_time;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "result_time{" +
                "title='" + title + '\'' +
                ", focus_time=" + total_focus_time +
                ", total_rest=" + total_rest +
                ", id=" + id +
                '}';
    }
}
