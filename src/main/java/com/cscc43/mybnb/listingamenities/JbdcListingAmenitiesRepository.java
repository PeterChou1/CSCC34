package com.cscc43.mybnb.listingamenities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class JbdcListingAmenitiesRepository implements ListingAmenitiesRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public int save(ListingAmenities listingAmenities) {
        return jdbcTemplate.update("INSERT INTO ListingAmenities(listingID, amenitiesName) VALUES (?,?)",
                new Object[] { listingAmenities.getListingID(), listingAmenities.getAmenitiesName() });
    }

    @Override
    public int update(ListingAmenities listingAmenities) {
        return jdbcTemplate.update("UPDATE ListingAmenities SET listingID=?, amenitiesName=? WHERE listingID=?",
                new Object[] { listingAmenities.getListingID(), listingAmenities.getAmenitiesName(), listingAmenities.getListingID() });
    }

    @Override
    public ListingAmenities findByListingID(int id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM ListingAmenities WHERE listingID=?", new Object[] { id }, new BeanPropertyRowMapper<>(ListingAmenities.class));
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    @Override
    public int deleteById(int id) {
        return jdbcTemplate.update("DELETE FROM ListingAmenities WHERE listingID=?", id);
    }

    @Override
    public List<ListingAmenities> findAll() {
        return jdbcTemplate.query("SELECT * FROM ListingAmenities", new BeanPropertyRowMapper<>(ListingAmenities.class));
    }

    @Override
    public int deleteAll() {
        return jdbcTemplate.update("DELETE FROM ListingAmenities");
    }
}
