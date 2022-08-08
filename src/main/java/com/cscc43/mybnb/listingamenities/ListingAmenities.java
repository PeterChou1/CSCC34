package com.cscc43.mybnb.listingamenities;

public class ListingAmenities {
    Integer listingID;
    String amenitiesName;

    public ListingAmenities(Integer listingID, String amenitiesName) {
        this.listingID = listingID;
        this.amenitiesName = amenitiesName;
    }

    @Override
    public String toString() {
        return "ListingAmenities{" +
                "listingID=" + listingID +
                ", amenitiesName='" + amenitiesName + '\'' +
                '}';
    }

    public Integer getListingID() {
        return listingID;
    }

    public void setListingID(Integer listingID) {
        this.listingID = listingID;
    }

    public String getAmenitiesName() {
        return amenitiesName;
    }

    public void setAmenitiesName(String amenitiesName) {
        this.amenitiesName = amenitiesName;
    }

}
