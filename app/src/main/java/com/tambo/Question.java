package com.tambo;

import java.io.Serializable;

public class Question implements Serializable {
    private String id;
    private User userDo;
    private User userAnsw;
    private boolean state;
    private String description;
    private int credit;
    private Meeting meet;

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

}

