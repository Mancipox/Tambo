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
public class Question implements Serializable {
    private String questionId;
    private User studentEmail;
    private User teacherEmail;
    private boolean state;
    private String description;
    private int karma;
    private Meeting meetingId;

    public Question(User userDo, boolean state, String description, int credit, Meeting meet) {
        this.studentEmail = userDo;
        this.state = state;
        this.description = description;
        this.karma = credit;
        this.meetingId = meet;
    }

    public Question(String id, User userDo, User userAnsw, boolean state, String description, int credit, Meeting meet) {
        this.questionId = id;
        this.studentEmail = userDo;
        this.teacherEmail = userAnsw;
        this.state = state;
        this.description = description;
        this.karma = credit;
        this.meetingId = meet;
    }

    public Question(String id, User userDo, boolean state, String description, int credit) {
        this.questionId = id;
        this.studentEmail = userDo;
        this.state = state;
        this.description = description;
        this.karma = credit;
    }

    public String getId() {
        return questionId;
    }

    public void setId(String id) {
        this.questionId = id;
    }

    public User getUserDo() {
        return studentEmail;
    }

    public void setUserDo(User userDo) {
        this.studentEmail = userDo;
    }

    public User getUserAnsw() {
        return teacherEmail;
    }

    public void setUserAnsw(User userAnsw) {
        this.teacherEmail = userAnsw;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
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

    public void setCredit(int credit) {
        this.karma = credit;
    }

    public Meeting getMeet() {
        return meetingId;
    }

    public void setMeet(Meeting meet) {
        this.meetingId = meet;
    }

    @Override
    public String toString() {
        return "Question "+description+" - AskBy: "+studentEmail.getUserName()+"\n AnswBy: "+((teacherEmail==null)?"No teacher":teacherEmail.getUserName())+"\n Place: "+meetingId.getPlace()+"\n Date: "+meetingId.getMeetingDate().toString();
    }

}

