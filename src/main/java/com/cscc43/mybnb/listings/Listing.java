package com.cscc43.mybnb.listings;

public class Listing {
    private Integer ID;
    private String Owner;
    private String name;
    private String type;
    private Double longitude;
    private Double latitude;
    private String postalCode;
    private String city;
    private String country;

    public Listing(Integer ID, String owner, String name, String type, Double longitude, Double latitude, String postalCode, String city, String country) {
        this.ID = ID;
        Owner = owner;
        this.name = name;
        this.type = type;
        this.longitude = longitude;
        this.latitude = latitude;
        this.postalCode = postalCode;
        this.city = city;
        this.country = country;
    }

    @Override
    public String toString() {
        return "Listing{" +
                "ID=" + ID +
                ", Owner='" + Owner + '\'' +
                ", type='" + type + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", postalCode='" + postalCode + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getOwner() {
        return Owner;
    }

    public void setOwner(String owner) {
        Owner = owner;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
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
}
