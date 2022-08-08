package com.cscc43.mybnb.review;

public class Review {
    Integer bookingID;
    String reviewerID;
    Integer rating;
    String review;

    public Review(Integer bookingID, String reviewerID, Integer rating, String review) {
        this.bookingID = bookingID;
        this.reviewerID = reviewerID;
        this.rating = rating;
        this.review = review;
    }

    @Override
    public String toString() {
        return "Review{" +
                "bookingID=" + bookingID +
                ", reviewerID=" + reviewerID +
                ", rating=" + rating +
                ", review='" + review + '\'' +
                '}';
    }

    public Integer getBookingID() {
        return bookingID;
    }

    public void setBookingID(Integer bookingID) {
        this.bookingID = bookingID;
    }

    public String getReviewerID() {
        return reviewerID;
    }

    public void setReviewerID(String reviewerID) {
        this.reviewerID = reviewerID;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
