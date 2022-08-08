package com.cscc43.mybnb.listings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public class JbdcListingRepository implements  ListingRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int save(Listing listing) {
        return jdbcTemplate.update("INSERT INTO Listing(owner, name, latitude, longitude, postalCode, postalCode, city, country) VALUES(?,?,?,?,?,?,?,?)",
                new Object[] { listing.getOwner(), listing.getName(), listing.getLatitude(), listing.getLongitude(), listing.getPostalCode(), listing.getCity(), listing.getCountry() });
    }

    @Override
    public int update(Listing listing) {
        return jdbcTemplate.update("UPDATE Listing SET name=?, latitude=?, longitude=?, postalCode=?, city=?, country=? WHERE ID=?",
                new Object[] { listing.getName(), listing.getLatitude(), listing.getLongitude(), listing.getPostalCode(), listing.getCity(), listing.getCountry(), listing.getID() });
    }

    @Override
    public Listing findByID(Integer id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Listing WHERE ID=?", new Object[] { id }, new BeanPropertyRowMapper<>(Listing.class));
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    @Override
    public int deleteById(Integer id) {
        return jdbcTemplate.update("DELETE FROM Listing WHERE ID=?", id);
    }

    @Override
    public List<Listing> findAll() {
        return jdbcTemplate.query("SELECT * FROM Listing", new BeanPropertyRowMapper<>(Listing.class));
    }

    @Override
    public List<Listing> findListingByOwner(String id) {
        return jdbcTemplate.query("SELECT * FROM Listing WHERE owner=?", new Object[] { id }, new BeanPropertyRowMapper<>(Listing.class));
    }

    @Override
    public List<Listing> findListingByDistance(Double longitude, Double latitude, Double distance) {
        return jdbcTemplate.query("SELECT * FROM (SELECT *,(((acos(sin((?*pi()/180)) * sin((Latitude*pi()/180))+cos((?*pi()/180)) * cos((Latitude*pi()/180)) * cos(((? - Longitude)*pi()/180))))*180/pi())*60*1.1515*1.609344) as distance FROM Listing) as ListingExpanded WHERE distance <= ?;",
                new Object[] { latitude, latitude, longitude, distance }, new BeanPropertyRowMapper<>(Listing.class));
    }

    @Override
    public List<Listing> searchByPostalCode(String postalCode) {
        return jdbcTemplate.query("SELECT * FROM listing t1\n" +
                "INNER JOIN (\n" +
                "SELECT DISTINCT ID FROM\n" +
                "(SELECT length(?) - length(m2.postalCode) AS diff, ID\n" +
                "FROM listing m1\n" +
                "WHERE diff = 0) t2 ON t2.ID=t1.ID;", new Object[] { postalCode }, new BeanPropertyRowMapper<>(Listing.class));
    }

    @Override
    public List<Listing> searchByParameters(String postalCode, Date startDate, Date endDate, Double price) {
        return jdbcTemplate.query("SELECT * FROM listing t1\n" +
                "INNER JOIN (\n" +
                "SELECT DISTINCT ID FROM\n" +
                "(SELECT length(?) - length(m2.postalCode) AS diff, ID\n" +
                "FROM listing m1\n" +
                "WHERE diff = 0) t2 ON t2.ID=t1.ID\n" +
                "WHERE t1.price <= ? AND t1.startDate >= ? AND t1.endDate <= ?;", new Object[] { postalCode, price, startDate, endDate }, new BeanPropertyRowMapper<>(Listing.class));
    }


    @Override
    public int deleteAll() {
        return jdbcTemplate.update("DELETE FROM Listing");
    }
}
