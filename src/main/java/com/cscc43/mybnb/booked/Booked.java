package com.cscc43.mybnb.booked;

import java.sql.Date;

public class Booked {
    private Integer ID;
    private String renterID;
    private Integer listingID;
    private Integer paymentInfo;
    private Date startDate;
    private Date endDate;
    private Double price;
    private Boolean renterCancelled;
    private Boolean hostCancelled;
    private Boolean finished;

    public Booked(Integer ID, String renterID, Integer listingID, Integer paymentInfo, Date startDate, Date endDate, Double price, Boolean renterCancelled, Boolean hostCancelled, Boolean finished) {
        this.ID = ID;
        this.renterID = renterID;
        this.listingID = listingID;
        this.paymentInfo = paymentInfo;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.renterCancelled = renterCancelled;
        this.hostCancelled = hostCancelled;
        this.finished = finished;
    }

    @Override
    public String toString() {
        return "Booked{" +
                "ID=" + ID +
                ", renterID='" + renterID + '\'' +
                ", listingID=" + listingID +
                ", paymentInfo=" + paymentInfo +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", price=" + price +
                ", renterCancelled=" + renterCancelled +
                ", hostCancelled=" + hostCancelled +
                ", finished=" + finished +
                '}';
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getRenterID() {
        return renterID;
    }

    public void setRenterID(String renterID) {
        this.renterID = renterID;
    }

    public Integer getListingID() {
        return listingID;
    }

    public void setListingID(Integer listingID) {
        this.listingID = listingID;
    }

    public Integer getPaymentInfo() {
        return paymentInfo;
    }

    public void setPaymentInfo(Integer paymentInfo) {
        this.paymentInfo = paymentInfo;
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

    public Boolean getRenterCancelled() {
        return renterCancelled;
    }

    public void setRenterCancelled(Boolean renterCancelled) {
        this.renterCancelled = renterCancelled;
    }

    public Boolean getHostCancelled() {
        return hostCancelled;
    }

    public void setHostCancelled(Boolean hostCancelled) {
        this.hostCancelled = hostCancelled;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }
}
