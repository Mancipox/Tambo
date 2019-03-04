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
    private String id;
    private User userDo;
    private User userAnsw;
    private boolean state;
    private String description;
    private int credit;
    private Meeting meet;

    public Question(User userDo, boolean state, String description, int credit, Meeting meet) {
        this.userDo = userDo;
        this.state = state;
        this.description = description;
        this.credit = credit;
        this.meet = meet;
    }

    public Question(String id, User userDo, User userAnsw, boolean state, String description, int credit, Meeting meet) {
        this.id = id;
        this.userDo = userDo;
        this.userAnsw = userAnsw;
        this.state = state;
        this.description = description;
        this.credit = credit;
        this.meet = meet;
    }

    public Question(String id, User userDo, boolean state, String description, int credit) {
        this.id = id;
        this.userDo = userDo;
        this.state = state;
        this.description = description;
        this.credit = credit;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUserDo() {
        return userDo;
    }

    public void setUserDo(User userDo) {
        this.userDo = userDo;
    }

    public User getUserAnsw() {
        return userAnsw;
    }

    public void setUserAnsw(User userAnsw) {
        this.userAnsw = userAnsw;
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
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public Meeting getMeet() {
        return meet;
    }

    public void setMeet(Meeting meet) {
        this.meet = meet;
    }

    @Override
    public String toString() {
        return "Question "+description+" - "+((userAnsw==null)?"Usuario vacío ":userAnsw.getUsername());
    }

}
