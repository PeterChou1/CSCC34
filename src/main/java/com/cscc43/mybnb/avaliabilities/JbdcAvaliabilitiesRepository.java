package com.cscc43.mybnb.avaliabilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JbdcAvaliabilitiesRepository implements AvaliabilitiesRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int save(Avaliabilities interval) {
        return jdbcTemplate.update("INSERT INTO Availabilities(listingID, startDate, endDate, price) VALUES (?,?,?,?)",
                new Object[] { interval.getListingID(), interval.getStartDate(), interval.getEndDate(), interval.getPrice() });
    }

    @Override
    public int update(Avaliabilities interval) {
        return jdbcTemplate.update("UPDATE Availabilities SET startDate=?, endDate=?, price=? WHERE listingID=?",
                new Object[] { interval.getStartDate(), interval.getEndDate(), interval.getPrice(), interval.getListingID() });
    }

    @Override
    public Avaliabilities findByAvailabilitiesID(int id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Availabilities WHERE listingID=?", new Object[] { id }, new BeanPropertyRowMapper<>(Avaliabilities.class));
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Avaliabilities> findAvailabilitiesByListingID(int id) {
        return jdbcTemplate.query("SELECT * FROM Availabilities WHERE listingID=?", new Object[] { id }, new BeanPropertyRowMapper<>(Avaliabilities.class));
    }

    @Override
    public List<Avaliabilities> findAvailabilitiesByOwner(String id) {
        return jdbcTemplate.query("SELECT * FROM Availabilities WHERE listingID IN (SELECT listingID FROM Listing WHERE ownerID=?)", new Object[] { id }, new BeanPropertyRowMapper<>(Avaliabilities.class));
    }

    @Override
    public int deleteById(int id) {
        return jdbcTemplate.update("DELETE FROM Availabilities WHERE listingID=?", id);
    }

    @Override
    public List<Avaliabilities> findAll() {
        return jdbcTemplate.query("SELECT * FROM Availabilities", new BeanPropertyRowMapper<>(Avaliabilities.class));
    }

    @Override
    public int deleteAll() {
        return jdbcTemplate.update("DELETE FROM Availabilities");
    }
}
