package com.tambo.model;

import java.io.Serializable;
import java.util.Date;

public class Meeting implements Serializable {
    String id;
    Date date;
    String place;

    public Meeting(String id, Date date, String place) {
        this.id = id;
        this.date = date;
        this.place = place;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

}
