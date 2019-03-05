/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tambo.Model;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 *
 * @author usuario
 */

public class Meeting implements Serializable {
    String id;
    Timestamp date;
    String place;

    public Meeting(Timestamp date, String place) {
        this.date = date;
        this.place = place;
    }

    public Meeting(String id, Timestamp date, String place) {
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

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }


}

