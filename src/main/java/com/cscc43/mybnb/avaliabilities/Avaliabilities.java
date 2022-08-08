package com.cscc43.mybnb.avaliabilities;

import java.sql.Date;

public class Avaliabilities {

    private Integer ID;
    private Integer listingID;
    private Date startDate;
    private Date endDate;
    private Double price;

    public Avaliabilities(Integer ID, Integer listingID, Date startDate, Date endDate, Double price) {
        this.ID = ID;
        this.listingID = listingID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Avaliabilities{" +
                "ID=" + ID +
                ", listingID=" + listingID +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", price=" + price +
                '}';
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getListingID() {
        return listingID;
    }

    public void setListingID(Integer listingID) {
        this.listingID = listingID;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
