package com.cscc43.mybnb.avaliabilities;

import java.util.List;

public interface AvaliabilitiesRepository {
    int save(Avaliabilities interval);
    int update(Avaliabilities interval);
    Avaliabilities findByAvailabilitiesID(int id);
    List<Avaliabilities> findAvailabilitiesByListingID(int id);

    List<Avaliabilities> findAvailabilitiesByOwner(String id);
    int deleteById(int id);
    List<Avaliabilities> findAll();
    int deleteAll();
}
