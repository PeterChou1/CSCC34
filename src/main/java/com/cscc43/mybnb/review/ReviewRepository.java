package com.cscc43.mybnb.review;

import java.util.List;

public interface ReviewRepository {
    int save(Review review);
    int update(Review review);
    Review findByID(String bookingID, String reviewerID);
    List<Review> findReviewReviewerID(String id);
    List<Review> findReviewByListingID(int id);
    List<Review> findReviewByOwnerID(String id);

    int deleteById(String bookingID, String reviewerID);
    int deleteByReviewerID(String id);
    int deleteByListingID(int id);
    int deleteByOwnerID(String id);
    List<Review> findAll();
    int deleteAll();
}
