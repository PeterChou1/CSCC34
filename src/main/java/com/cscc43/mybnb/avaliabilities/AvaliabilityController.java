package com.cscc43.mybnb.avaliabilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="api/avaliabilities")
public class AvaliabilityController {

    @Autowired
    AvaliabilitiesRepository availabilityRepository;

    // conduct changes for price and availability of a listing
    @PutMapping(path="/avaliabilities/{aId}")
    public ResponseEntity updateListingAvaliabilities(@PathVariable Integer aId, @RequestBody Avaliabilities avaliabilities) {
        try {
            avaliabilities.setID(aId);
            availabilityRepository.update(avaliabilities);
            return new ResponseEntity<>(avaliabilities, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
