package com.cscc43.mybnb.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "availabilities", schema = "testdb", catalog = "")
public class AvailabilitiesEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID", nullable = false)
    private int id;
    @Basic
    @Column(name = "listingID", nullable = false)
    private int listingId;
    @Basic
    @Column(name = "startDate", nullable = false)
    private Date startDate;
    @Basic
    @Column(name = "endDate", nullable = false)
    private Date endDate;
    @Basic
    @Column(name = "price", nullable = false, precision = 0)
    private double price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getListingId() {
        return listingId;
    }

    public void setListingId(int listingId) {
        this.listingId = listingId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AvailabilitiesEntity that = (AvailabilitiesEntity) o;
        return id == that.id && listingId == that.listingId && Double.compare(that.price, price) == 0 && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, listingId, startDate, endDate, price);
    }
}
