/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tambo.Model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;


/**
 *
 * @author usuario
 */

public class Meeting implements Serializable {
    String meetingId;
    Date meetingDate;
    String place;
    String description;
    Double xPos;
    Double yPos;

    public Meeting(Date meetingDate, String place, String description, Double xPos, Double yPos) {
        this.meetingDate = meetingDate;
        this.place = place;
        this.description=description;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public Meeting(String meetingId, Date meetingDate, String place) {
        this.meetingId = meetingId;
        this.meetingDate = meetingDate;
        this.place = place;
    }

    public String getmeetingId() {
        return meetingId;
    }

    public void setmeetingId(String id) {
        this.meetingId = id;
    }

    public Date getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(Timestamp meetingDate) {
        this.meetingDate = meetingDate;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDescription(){
        return description;
    }

}

