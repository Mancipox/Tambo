package com.tambo.Model;

import java.util.Date;

/**
 *
 * @author mancipox
 */
public class Meeting {
    private int id;
    private String date;
    private String place;
    private boolean state;

    public Meeting() {
    }

    public Meeting(int id, String date, String place, boolean state) {
        this.id = id;
        this.date = date;
        this.place = place;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Meeting id: "+this.id+" - date: "+this.date+" - place: "+this.place;
    }



}