package com.cscc43.mybnb.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "paymentinfo", schema = "testdb", catalog = "")
public class PaymentinfoEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID", nullable = false)
    private int id;
    @Basic
    @Column(name = "owner", nullable = false, length = 30)
    private String owner;
    @Basic
    @Column(name = "paymentInfo", nullable = false, length = 255)
    private String paymentInfo;
    @OneToMany(mappedBy = "paymentinfoByPaymentInfo")
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

    public String getPaymentInfo() {
        return paymentInfo;
    }

    public void setPaymentInfo(String paymentInfo) {
        this.paymentInfo = paymentInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentinfoEntity that = (PaymentinfoEntity) o;
        return id == that.id && Objects.equals(owner, that.owner) && Objects.equals(paymentInfo, that.paymentInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, owner, paymentInfo);
    }

    public Collection<BookedEntity> getBookedsById() {
        return bookedsById;
    }

    public void setBookedsById(Collection<BookedEntity> bookedsById) {
        this.bookedsById = bookedsById;
    }
}
