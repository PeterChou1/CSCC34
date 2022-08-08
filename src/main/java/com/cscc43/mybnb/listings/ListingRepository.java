package com.cscc43.mybnb.listings;

import java.sql.Date;
import java.util.List;

public interface ListingRepository {
    int save(Listing listing);
    int update(Listing listing);
    Listing findByID(Integer id);
    int deleteById(Integer id);
    List<Listing> findAll();
    List<Listing> findListingByOwner(String id);
    List<Listing> findListingByDistance(Double longitude, Double latitude, Double distance);
    List<Listing> searchByPostalCode(String postalCode);

    List<Listing> searchByParameters(String postalCode, Date startDate, Date endDate, Double price);
    int deleteAll();
}
