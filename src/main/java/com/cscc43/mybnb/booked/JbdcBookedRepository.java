package com.cscc43.mybnb.booked;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JbdcBookedRepository implements BookedRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int save(Booked book) {
        return jdbcTemplate.update("INSERT INTO Booked(renterid, listingid, paymentinfo, startdate, enddate, finished) VALUES (?,?,?,?,?,?)",
                new Object[] { book.getRenterID(), book.getListingID(), book.getPaymentInfo(), book.getStartDate(), book.getEndDate(), book.getFinished() });
    }

    @Override
    public int update(Booked book) {
        return jdbcTemplate.update("UPDATE Booked SET paymentinfo=?, renterCanceled=?, hostCanceled=?, finished=? WHERE ID=?",
                new Object[] { book.getPaymentInfo(), book.getStartDate(), book.getEndDate(), book.getFinished(), book.getID() });
    }

    @Override
    public Booked findByBookedID(int id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Booked WHERE ID=?", new Object[] { id }, new BeanPropertyRowMapper<>(Booked.class));
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    @Override
    public int deleteById(int id) {
        return jdbcTemplate.update("DELETE FROM Booked WHERE ID=?", id);
    }

    @Override
    public List<Booked> findAll() {
        return jdbcTemplate.query("SELECT * FROM Booked", new BeanPropertyRowMapper<>(Booked.class));
    }

    @Override
    public List<Booked> findBookByListingID(int id) {
        return jdbcTemplate.query("SELECT * FROM Booked WHERE listingID=?", new Object[] { id }, new BeanPropertyRowMapper<>(Booked.class));
    }

    @Override
    public List<Booked> findBookByRenterID(String id) {
        return jdbcTemplate.query("SELECT * FROM Booked WHERE renterID=?", new Object[] { id }, new BeanPropertyRowMapper<>(Booked.class));
    }

    @Override
    public List<Booked> findBookedByOwner(String id) {
        return jdbcTemplate.query("SELECT * FROM Booked WHERE listingID IN (SELECT listingID FROM Listing WHERE ownerID=?)", new Object[] { id }, new BeanPropertyRowMapper<>(Booked.class));
    }

    @Override
    public int deleteAll() {
        return jdbcTemplate.update("DELETE FROM Booked");
    }
}
