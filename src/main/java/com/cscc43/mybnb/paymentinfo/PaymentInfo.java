package com.cscc43.mybnb.paymentinfo;

public class PaymentInfo {
    Integer ID;
    String owner;
    String paymentInfo;

    public PaymentInfo(Integer ID, String owner, String paymentInfo) {
        this.ID = ID;
        this.owner = owner;
        this.paymentInfo = paymentInfo;
    }

    @Override
    public String toString() {
        return "PaymentInfo{" +
                "ID=" + ID +
                ", owner='" + owner + '\'' +
                ", paymentInfo='" + paymentInfo + '\'' +
                '}';
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getPaymentInfo() {
        return paymentInfo;
    }

    public void setPaymentInfo(String paymentInfo) {
        this.paymentInfo = paymentInfo;
    }
}
