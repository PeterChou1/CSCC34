package com.cscc43.mybnb.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "booked", schema = "testdb", catalog = "")
public class BookedEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID", nullable = false)
    private int id;
    @Basic
    @Column(name = "renterID", nullable = false, length = 30)
    private String renterId;
    @Basic
    @Column(name = "listingID", nullable = false)
    private int listingId;
    @Basic
    @Column(name = "paymentInfo", nullable = false)
    private int paymentInfo;
    @Basic
    @Column(name = "startDate", nullable = false)
    private Date startDate;
    @Basic
    @Column(name = "endDate", nullable = false)
    private Date endDate;
    @Basic
    @Column(name = "price", nullable = false, precision = 0)
    private double price;
    @Basic
    @Column(name = "renterCanceled", nullable = true)
    private Byte renterCanceled;
    @Basic
    @Column(name = "hostCanceled", nullable = true)
    private Byte hostCanceled;
    @Basic
    @Column(name = "finished", nullable = true)
    private Byte finished;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRenterId() {
        return renterId;
    }

    public void setRenterId(String renterId) {
        this.renterId = renterId;
    }

    public int getListingId() {
        return listingId;
    }

    public void setListingId(int listingId) {
        this.listingId = listingId;
    }

    public int getPaymentInfo() {
        return paymentInfo;
    }

    public void setPaymentInfo(int paymentInfo) {
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Byte getRenterCanceled() {
        return renterCanceled;
    }

    public void setRenterCanceled(Byte renterCanceled) {
        this.renterCanceled = renterCanceled;
    }

    public Byte getHostCanceled() {
        return hostCanceled;
    }

    public void setHostCanceled(Byte hostCanceled) {
        this.hostCanceled = hostCanceled;
    }

    public Byte getFinished() {
        return finished;
    }

    public void setFinished(Byte finished) {
        this.finished = finished;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookedEntity that = (BookedEntity) o;
        return id == that.id && listingId == that.listingId && paymentInfo == that.paymentInfo && Double.compare(that.price, price) == 0 && Objects.equals(renterId, that.renterId) && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate) && Objects.equals(renterCanceled, that.renterCanceled) && Objects.equals(hostCanceled, that.hostCanceled) && Objects.equals(finished, that.finished);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, renterId, listingId, paymentInfo, startDate, endDate, price, renterCanceled, hostCanceled, finished);
    }
}
