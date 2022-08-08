package com.cscc43.mybnb.user;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "Users")
public class User {
    @Id
    private String username;
    private String name;
    // addresss
    private String postalCode;
    private String city;
    private String country;
    // private credentials
    private String sin;
    private LocalDate dateOfBirth;
    private Boolean isRenter;
    private Boolean isHost;

    public User(String username, String name, String postalCode, String city, String country, String sin, LocalDate dateOfBirth, Boolean isRenter, Boolean isHost) {
        this.username = username;
        this.name = name;
        this.postalCode = postalCode;
        this.city = city;
        this.country = country;
        this.sin = sin;
        this.dateOfBirth = dateOfBirth;
        this.isRenter = isRenter;
        this.isHost = isHost;
    }

    public User() {
    }

    public Boolean getRenter() {
        return isRenter;
    }

    public void setRenter(Boolean renter) {
        isRenter = renter;
    }

    public Boolean getHost() {
        return isHost;
    }

    public void setHost(Boolean host) {
        isHost = host;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
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

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", sin='" + sin + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }
}
