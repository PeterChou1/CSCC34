package com.cscc43.mybnb.booked;

import java.util.List;

public interface BookedRepository {
    int save(Booked book);
    int update(Booked book);
    Booked findByBookedID(int id);
    int deleteById(int id);
    List<Booked> findAll();
    List<Booked> findBookByListingID(int id);
    List<Booked> findBookByRenterID(String id);
    List<Booked> findBookedByOwner(String id);
    int deleteAll();
}
