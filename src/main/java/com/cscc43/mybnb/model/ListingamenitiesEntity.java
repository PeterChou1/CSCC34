package com.cscc43.mybnb.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "listingamenities", schema = "testdb", catalog = "")
public class ListingamenitiesEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "listingID", nullable = false)
    private int listingId;
    @Basic
    @Column(name = "amenitiesName", nullable = false, length = 255)
    private String amenitiesName;
    @OneToOne
    @JoinColumn(name = "listingID", referencedColumnName = "ID", nullable = false)
    private ListingEntity listingByListingId;

    public int getListingId() {
        return listingId;
    }

    public void setListingId(int listingId) {
        this.listingId = listingId;
    }

    public String getAmenitiesName() {
        return amenitiesName;
    }

    public void setAmenitiesName(String amenitiesName) {
        this.amenitiesName = amenitiesName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListingamenitiesEntity that = (ListingamenitiesEntity) o;
        return listingId == that.listingId && Objects.equals(amenitiesName, that.amenitiesName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(listingId, amenitiesName);
    }

    public ListingEntity getListingByListingId() {
        return listingByListingId;
    }

    public void setListingByListingId(ListingEntity listingByListingId) {
        this.listingByListingId = listingByListingId;
    }
}
