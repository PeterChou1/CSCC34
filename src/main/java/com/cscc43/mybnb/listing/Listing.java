package com.cscc43.mybnb.listing;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="Listings")

public class Listing {
    @Id
    private Long Id;
    private String listingType;
    private Double longitude;
    private Double lattitude;
    private String postalCode;
    private String city;
    private String country;

}
