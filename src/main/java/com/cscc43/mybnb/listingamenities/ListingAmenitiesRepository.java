package com.cscc43.mybnb.listingamenities;

import java.util.List;

public interface ListingAmenitiesRepository {
    int save(ListingAmenities listingAmenities);
    int update(ListingAmenities listingAmenities);
    ListingAmenities findByListingID(int id);
    int deleteById(int id);
    List<ListingAmenities> findAll();
    int deleteAll();
}
