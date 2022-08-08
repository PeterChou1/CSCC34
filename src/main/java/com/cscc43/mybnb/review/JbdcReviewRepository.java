package com.cscc43.mybnb.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JbdcReviewRepository implements ReviewRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int save(Review review) {
        return jdbcTemplate.update("INSERT INTO Review(bookingID, reviewerID, rating, review) VALUES (?,?,?,?)",
                new Object[] { review.getBookingID(), review.getReviewerID(), review.getRating(), review.getReview() });
    }

    @Override
    public int update(Review review) {
        return jdbcTemplate.update("UPDATE Review SET rating=?, review=? WHERE bookingID=? AND reviewerID=?",
                new Object[] {  review.getRating(), review.getReview(), review.getBookingID(), review.getReviewerID() });
    }

    @Override
    public Review findByID(String bookingID, String reviewerID) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Review WHERE bookingID=? AND reviewerID=?", new Object[] { bookingID, reviewerID }, new BeanPropertyRowMapper<>(Review.class));
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Review> findReviewReviewerID(String id) {
        return jdbcTemplate.query("SELECT * FROM Review WHERE reviewerID=?", new Object[] { id }, new BeanPropertyRowMapper<>(Review.class));
    }

    @Override
    public List<Review> findReviewByListingID(int id) {
        return jdbcTemplate.query("SELECT * FROM Review  WHERE bookingID IN (SELECT bookingID FROM booked WHERE listingID=?)", new Object[] { id }, new BeanPropertyRowMapper<>(Review.class));
    }

    @Override
    public List<Review> findReviewByOwnerID(String id) {
        return jdbcTemplate.query("SELECT * FROM Review WHERE bookingID IN (SELECT bookingID FROM booked WHERE listingID IN (SELECT listingID FROM Listing WHERE ownerID=?))", new Object[] { id }, new BeanPropertyRowMapper<>(Review.class));
    }

    @Override
    public int deleteById(String bookingID, String reviewerID) {
        return jdbcTemplate.update("DELETE FROM Review WHERE bookingID=? AND reviewerID=?", bookingID, reviewerID);
    }

    @Override
    public int deleteByReviewerID(String id) {
        return jdbcTemplate.update("DELETE FROM Review WHERE reviewerID=?", id);
    }

    @Override
    public int deleteByListingID(int id) {
        return jdbcTemplate.update("DELETE FROM Review WHERE bookingID IN (SELECT bookingID FROM booked WHERE listingID=?)", id);
    }

    @Override
    public int deleteByOwnerID(String id) {
        return jdbcTemplate.update("DELETE FROM Review WHERE bookingID IN (SELECT bookingID FROM booked WHERE listingID IN (SELECT listingID FROM Listing WHERE ownerID=?))", id);
    }

    @Override
    public List<Review> findAll() {
        return jdbcTemplate.query("SELECT * FROM Review", new BeanPropertyRowMapper<>(Review.class));
    }

    @Override
    public int deleteAll() {
        return jdbcTemplate.update("DELETE FROM Review");
    }
}
