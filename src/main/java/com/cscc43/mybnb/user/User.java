package com.cscc43.mybnb.user;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Date;

public class User {

    private String userName;
    private String name;
    private String postCode;
    private String city;
    private String country;
    private String sin;
    private Date dob;

    public User(String userName, String name, String postCode, String city, String country, String sin, Date dob) {
        this.userName = userName;
        this.name = name;
        this.postCode = postCode;
        this.city = city;
        this.country = country;
        this.sin = sin;
        this.dob = dob;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", name='" + name + '\'' +
                ", postCode='" + postCode + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", sin='" + sin + '\'' +
                ", date of birth=" + dob +
                '}';
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSin() {
        return sin;
    }

    public void setSin(String sin) {
        this.sin = sin;
    }

    public Date getDate() {
        return dob;
    }

    public void setDate(Date date) {
        this.dob = date;
    }
}
