package com.cscc43.mybnb.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "listing", schema = "testdb", catalog = "")
public class ListingEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID", nullable = false)
    private int id;
    @Basic
    @Column(name = "owner", nullable = true, length = 30)
    private String owner;
    @Basic
    @Column(name = "name", nullable = true, length = 255)
    private String name;
    @Basic
    @Column(name = "type", nullable = true)
    private Object type;
    @Basic
    @Column(name = "latitude", nullable = false, precision = 4)
    private BigDecimal latitude;
    @Basic
    @Column(name = "longitude", nullable = false, precision = 4)
    private BigDecimal longitude;
    @Basic
    @Column(name = "postalCode", nullable = false, length = 255)
    private String postalCode;
    @Basic
    @Column(name = "city", nullable = false, length = 255)
    private String city;
    @Basic
    @Column(name = "country", nullable = false, length = 255)
    private String country;
    @OneToMany(mappedBy = "listingByListingId")
    private Collection<AvailabilitiesEntity> availabilitiesById;
    @OneToMany(mappedBy = "listingByListingId")
    private Collection<BookedEntity> bookedsById;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getType() {
        return type;
    }

    public void setType(Object type) {
        this.type = type;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListingEntity that = (ListingEntity) o;
        return id == that.id && Objects.equals(owner, that.owner) && Objects.equals(name, that.name) && Objects.equals(type, that.type) && Objects.equals(latitude, that.latitude) && Objects.equals(longitude, that.longitude) && Objects.equals(postalCode, that.postalCode) && Objects.equals(city, that.city) && Objects.equals(country, that.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, owner, name, type, latitude, longitude, postalCode, city, country);
    }

    public Collection<AvailabilitiesEntity> getAvailabilitiesById() {
        return availabilitiesById;
    }

    public void setAvailabilitiesById(Collection<AvailabilitiesEntity> availabilitiesById) {
        this.availabilitiesById = availabilitiesById;
    }

    public Collection<BookedEntity> getBookedsById() {
        return bookedsById;
    }

    public void setBookedsById(Collection<BookedEntity> bookedsById) {
        this.bookedsById = bookedsById;
    }
}
