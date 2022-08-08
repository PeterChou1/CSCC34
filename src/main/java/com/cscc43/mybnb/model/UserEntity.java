package com.cscc43.mybnb.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "user", schema = "testdb", catalog = "")
public class UserEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "userName", nullable = false, length = 30)
    private String userName;
    @Basic
    @Column(name = "name", nullable = false, length = 255)
    private String name;
    @Basic
    @Column(name = "postalCode", nullable = false, length = 255)
    private String postalCode;
    @Basic
    @Column(name = "city", nullable = false, length = 255)
    private String city;
    @Basic
    @Column(name = "country", nullable = false, length = 255)
    private String country;
    @Basic
    @Column(name = "sin", nullable = false, length = 255)
    private String sin;
    @Basic
    @Column(name = "dob", nullable = false)
    private Date dob;
    @OneToMany(mappedBy = "userByRenterId")
    private Collection<BookedEntity> bookedsByUserName;
    @OneToMany(mappedBy = "userByOwner")
    private Collection<ListingEntity> listingsByUserName;
    @OneToMany(mappedBy = "userByOwner")
    private Collection<PaymentinfoEntity> paymentinfosByUserName;
    @OneToMany(mappedBy = "userByReviewerId")
    private Collection<ReviewEntity> reviewsByUserName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getSin() {
        return sin;
    }

    public void setSin(String sin) {
        this.sin = sin;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(userName, that.userName) && Objects.equals(name, that.name) && Objects.equals(postalCode, that.postalCode) && Objects.equals(city, that.city) && Objects.equals(country, that.country) && Objects.equals(sin, that.sin) && Objects.equals(dob, that.dob);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, name, postalCode, city, country, sin, dob);
    }

    public Collection<BookedEntity> getBookedsByUserName() {
        return bookedsByUserName;
    }

    public void setBookedsByUserName(Collection<BookedEntity> bookedsByUserName) {
        this.bookedsByUserName = bookedsByUserName;
    }

    public Collection<ListingEntity> getListingsByUserName() {
        return listingsByUserName;
    }

    public void setListingsByUserName(Collection<ListingEntity> listingsByUserName) {
        this.listingsByUserName = listingsByUserName;
    }

    public Collection<PaymentinfoEntity> getPaymentinfosByUserName() {
        return paymentinfosByUserName;
    }

    public void setPaymentinfosByUserName(Collection<PaymentinfoEntity> paymentinfosByUserName) {
        this.paymentinfosByUserName = paymentinfosByUserName;
    }

    public Collection<ReviewEntity> getReviewsByUserName() {
        return reviewsByUserName;
    }

    public void setReviewsByUserName(Collection<ReviewEntity> reviewsByUserName) {
        this.reviewsByUserName = reviewsByUserName;
    }
}
