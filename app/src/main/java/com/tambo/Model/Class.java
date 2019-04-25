/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tambo.Model;

import java.io.Serializable;

/**
 *
 * @author usuario
 */
public class Class implements Serializable {
    private Integer classId;
    private String description;
    private int karma;
    private Boolean state;
    private Meeting meetingId;
    private User studentEmail;
    private User teacherEmail;
    private Topic topicId;
    public Class() {
    }

    public Class(Integer classId) {
        this.classId = classId;
    }

    public Class(Integer classId, String description, int karma) {
        this.classId = classId;
        this.description = description;
        this.karma = karma;
    }

    public void setAll(Class classx){
        this.description=classx.getDescription();
        this.karma=classx.karma;
        this.state=classx.state;
        this.teacherEmail=classx.teacherEmail;
        this.topicId=classx.topicId;
    }

    public Class(String description, int karma, Boolean state ,Meeting meetingId, User studentEmail, Topic topicId) {
        this.description = description;
        this.state=state;
        this.karma = karma;
        this.meetingId = meetingId;
        this.studentEmail = studentEmail;
        this.topicId = topicId;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCredit() {
        return karma;
    }

    public void setKarma(int karma) {
        this.karma = karma;
    }

    public Boolean isState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public Topic getTopicId() {
        return topicId;
    }

    public void setTopicId(Topic topicId) {
        this.topicId = topicId;
    }

    public Meeting getMeet() {
        return meetingId;
    }

    public void setMeetingId(Meeting meetingId) {
        this.meetingId = meetingId;
    }

    public User getUserAnsw() {
        return studentEmail;
    }

    public void setStudentEmail(User studentEmail) {
        this.studentEmail = studentEmail;
    }

    public User getTeacherEmail() {
        return teacherEmail;
    }

    public void setTeacherEmail(User teacherEmail) {
        this.teacherEmail = teacherEmail;
    }



    @Override
    public int hashCode() {
        int hash = 0;
        hash += (classId != null ? classId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Class)) {
            return false;
        }
        Class other = (Class) object;
        if ((this.classId == null && other.classId != null) || (this.classId != null && !this.classId.equals(other.classId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.tamboserver.exceptions.Class[ classId=" + classId + " ]";
    }
}

