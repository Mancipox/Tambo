package com.tambo;

import java.io.Serializable;

public class User implements Serializable {
    private String username;
    private String firstName;
    private String secondName;
    private String password;
    private String email;
    private String phone;
    private String gender;
    private int credits;

    public User(String email,String password) {
        this.password = password;
        this.email = email;
    }



    public User(String email,String username, String firstName, String secondName, String password,  String phone, String gender, int credits) {
        this.username = username;
        this.firstName = firstName;
        this.secondName = secondName;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.credits = credits;
    }


    public User(String email,String username, String firstName, String secondName, String password,  String phone, String gender) {
        this.username = username;
        this.firstName = firstName;
        this.secondName = secondName;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
    }

    public User() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

}

