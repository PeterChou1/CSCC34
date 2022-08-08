package com.cscc43.mybnb.listing;

import org.hibernate.annotations.Check;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name="Availabilities")
@IdClass(AvailablePK.class)
public class Availability {
    private Double Price;
    @Id
    @Column(name="start_date", nullable = false)
    protected LocalDate startDate;
    @Id
    @Column(name="end_date", nullable = false, columnDefinition = "")
    protected LocalDate endDate;
    @Id
    @ManyToOne
    @JoinColumn(name="listing_id", nullable = false)
    private Listing listing;

    public Availability(Double price, LocalDate startDate, LocalDate endDate, Listing listing) {
        Price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.listing = listing;
    }

    public Availability() {

    }

    public Double getPrice() {
        return Price;
    }

    public void setPrice(Double price) {
        Price = price;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Listing getListing() {
        return listing;
    }

    public void setListing(Listing listing) {
        this.listing = listing;
    }
}
