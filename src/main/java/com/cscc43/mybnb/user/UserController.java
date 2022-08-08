package com.cscc43.mybnb.user;

import java.util.ArrayList;
import java.util.List;

import com.cscc43.mybnb.avaliabilities.Avaliabilities;
import com.cscc43.mybnb.avaliabilities.AvaliabilitiesRepository;
import com.cscc43.mybnb.booked.Booked;
import com.cscc43.mybnb.booked.BookedRepository;
import com.cscc43.mybnb.listings.Listing;
import com.cscc43.mybnb.listings.ListingRepository;
import com.cscc43.mybnb.paymentinfo.PaymentInfo;
import com.cscc43.mybnb.paymentinfo.PaymentInfoRepository;
import com.cscc43.mybnb.review.Review;
import com.cscc43.mybnb.review.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path="api/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PaymentInfoRepository paymentInfoRepository;

    @Autowired
    private ListingRepository listingRepository;

    @Autowired
    private BookedRepository bookedRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private AvaliabilitiesRepository availabilityRepository;


    @GetMapping
    public ResponseEntity<List<User>> getAllUser() {
        List<User> users;
        try {
            users = userRepository.findAll();
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path="/{id}")
    public ResponseEntity getUser(@PathVariable String id) {
        User user;
        try {
            user = userRepository.findByUsername(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity addUser(@RequestBody User user) {
        try {
            userRepository.save(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity updateUser(@RequestBody User user) {
        try {
            userRepository.update(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity deleteUser(@PathVariable String id) {
        try {
            userRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity deleteAllUser() {
        try {
            userRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping(path="/{id}/paymentinfo")
    public ResponseEntity getPaymentInfo(@PathVariable String id) {
        List<PaymentInfo> paymentInfo;
        try {
            paymentInfo = paymentInfoRepository.findPaymentInfoByUserID(id);
            return new ResponseEntity<>(paymentInfo, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    // get all listing owned by user
    @GetMapping(path="/{id}/listings")
    public ResponseEntity getListings(@PathVariable String id) {
        List<Listing> listings;
        try {
            listings = listingRepository.findListingByOwner(id);
            return new ResponseEntity<>(listings, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    // create a listing owned by user
    @PostMapping(path="/{id}/listings")
    public ResponseEntity addListing(@PathVariable String id, @RequestBody Listing listing) {
        try {
            listing.setOwner(id);
            listingRepository.save(listing);
            return new ResponseEntity<>(listing, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    // get availabilities of listing owned by user
    @GetMapping(path="/{id}/listings/available")
    public ResponseEntity getAvailableListings(@PathVariable String id) {
        List<Listing> listings;
        try {
            listings = listingRepository.findListingByOwner(id);
            return new ResponseEntity<>(listings, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // get all booking by user
    @GetMapping(path="/{id}/bookings")
    public ResponseEntity getBookings(@PathVariable String id) {
        List<Booked> bookings;
        try {
            bookings = bookedRepository.findBookByRenterID(id);
            return new ResponseEntity<>(bookings, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // get all reviews for a user
    @GetMapping(path="/{id}/reviews")
    public ResponseEntity getReviews(@PathVariable String id) {
        List<Review> reviews;
        try {
            reviews = reviewRepository.findReviewReviewerID(id);
            return new ResponseEntity<>(reviews, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // insert reviews for a booking
    @PostMapping(path="/{id}/reviews/{bookingID}")
    public ResponseEntity addReview(@PathVariable String id, @PathVariable Integer bookingID, @RequestBody Review review) {
        try {
            review.setReviewerID(id);
            review.setBookingID(bookingID);
            reviewRepository.save(review);
            return new ResponseEntity<>(review, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


}
