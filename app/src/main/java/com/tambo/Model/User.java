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
public class User implements Serializable {
    private String userName;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String phone;
    private String gender;
    private int karma;

    public User(String email,String password) {
        this.password = password;
        this.email = email;
    }



    public User(String email, String userName, String firstName, String secondName, String password, String phone, String gender, int karma) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = secondName;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.karma = karma;
    }

    public User(String email, String userName, String firstName, String secondName, String password, String phone, String gender) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = secondName;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
    }


    public User() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public int getKarma() {
        return karma;
    }

    public void setKarma(int karma) {
        this.karma = karma;
    }


}



