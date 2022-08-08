package com.cscc43.mybnb.listing;

import org.hibernate.annotations.Check;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.time.LocalDate;

public class AvailablePK implements Serializable {
    @Column(name="start_date", nullable = false)
    protected LocalDate startDate;

    @Column(name="end_date", nullable = false)
    protected LocalDate endDate;

    @ManyToOne
    @JoinColumn(name="listing_id", nullable = false)
    private Listing listing;

    public AvailablePK() {}

    public AvailablePK(LocalDate startDate, LocalDate endDate, Listing listing) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.listing = listing;
    }

    // equals, hashCode
}
