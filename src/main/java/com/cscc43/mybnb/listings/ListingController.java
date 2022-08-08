package com.cscc43.mybnb.listings;


import com.cscc43.mybnb.avaliabilities.Avaliabilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.cscc43.mybnb.avaliabilities.AvaliabilitiesRepository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path="api/listing")
public class ListingController {

    @Autowired
    ListingRepository listingRepository;

    @Autowired
    AvaliabilitiesRepository availabilityRepository;

    @GetMapping
    public ResponseEntity<List<Listing>> getAllListings() {
        List<Listing> listings;
        try {
            listings = listingRepository.findAll();
            return new ResponseEntity<>(listings, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path="/{id}")
    public ResponseEntity getListing(@PathVariable Integer id) {
        Listing listing;
        try {
            listing = listingRepository.findByID(id);
            return new ResponseEntity<>(listing, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // conduct changes for listing
    @PutMapping(path="/{id}")
    public ResponseEntity updateListing(@RequestBody Listing listing, @PathVariable Integer id) {
        try {
            listing.setID(id);
            listingRepository.update(listing);
            return new ResponseEntity<>(listing, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // get all avaliability of a listing
    @GetMapping(path="/{id}/avaliabilities")
    public ResponseEntity getListingAvaliabilities(@PathVariable Integer id) {
        List<Avaliabilities> avaliabilities;
        try {
            avaliabilities = availabilityRepository.findAvailabilitiesByListingID(id);
            return new ResponseEntity<>(avaliabilities, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping(path="/getListingByDistance")
    public ResponseEntity getListingByDistance(@RequestParam Double longitude, @RequestParam Double latitude, @RequestParam Optional<Double> distance) {
        List<Listing> listings;
        Double distanceInMeters = distance.orElse(200.0);
        try {
            listings = listingRepository.findListingByDistance(longitude, latitude, distanceInMeters);
            return new ResponseEntity<>(listings, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping(path="/searchByPostalCode")
    public ResponseEntity searchByPostalCode(@RequestParam String postalCode) {
        List<Listing> listings;
        try {
            listings = listingRepository.searchByPostalCode(postalCode);
            return new ResponseEntity<>(listings, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping(path="/searchByParameters")
    public ResponseEntity searchByParameters(@RequestParam String postalCode, @RequestParam Optional<Date> startDate, @RequestParam Optional<Date> endDate, @RequestParam Optional<Double> price) {
        List<Listing> listings;
        // default date 1800
        Date startDateIn = startDate.orElse(new Date(1800, 1, 1));
        // default end date 2200
        Date endDateIn = endDate.orElse(new Date(2200, 1, 1));
        try {
            listings = listingRepository.searchByParameters(postalCode, startDateIn, endDateIn, price.orElse(100.0));
            return new ResponseEntity<>(listings, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
