package com.cscc43.mybnb.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "review", schema = "testdb", catalog = "")
@IdClass(ReviewEntityPK.class)
public class ReviewEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "bookingID", nullable = false)
    private int bookingId;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "reviewerID", nullable = false, length = 30)
    private String reviewerId;
    @Basic
    @Column(name = "rating", nullable = false)
    private int rating;
    @Basic
    @Column(name = "review", nullable = false, length = -1)
    private String review;
    @ManyToOne
    @JoinColumn(name = "bookingID", referencedColumnName = "ID", nullable = false)
    private BookedEntity bookedByBookingId;

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public String getReviewerId() {
        return reviewerId;
    }

    public void setReviewerId(String reviewerId) {
        this.reviewerId = reviewerId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReviewEntity that = (ReviewEntity) o;
        return bookingId == that.bookingId && rating == that.rating && Objects.equals(reviewerId, that.reviewerId) && Objects.equals(review, that.review);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookingId, reviewerId, rating, review);
    }

    public BookedEntity getBookedByBookingId() {
        return bookedByBookingId;
    }

    public void setBookedByBookingId(BookedEntity bookedByBookingId) {
        this.bookedByBookingId = bookedByBookingId;
    }
}
