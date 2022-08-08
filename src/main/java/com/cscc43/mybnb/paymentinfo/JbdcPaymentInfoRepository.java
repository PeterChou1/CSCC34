package com.cscc43.mybnb.paymentinfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JbdcPaymentInfoRepository implements PaymentInfoRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int save(PaymentInfo paymentInfo) {
        return jdbcTemplate.update("INSERT INTO PaymentInfo(owner, paymentInfo) VALUES (?,?)",
                new Object[] { paymentInfo.getOwner(), paymentInfo.getPaymentInfo() });
    }

    @Override
    public int update(PaymentInfo paymentInfo) {
        return jdbcTemplate.update("UPDATE PaymentInfo SET owner=?, paymentInfo=? WHERE owner=?",
                new Object[] { paymentInfo.getOwner(), paymentInfo.getPaymentInfo(), paymentInfo.getOwner() });
    }

    @Override
    public PaymentInfo findByID(int id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM PaymentInfo WHERE ID=?", new Object[] { id }, new BeanPropertyRowMapper<>(PaymentInfo.class));
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<PaymentInfo> findPaymentInfoByUserID(String id) {
        return jdbcTemplate.query("SELECT * FROM PaymentInfo WHERE owner=?", new Object[] { id }, new BeanPropertyRowMapper<>(PaymentInfo.class));
    }

    @Override
    public int deleteById(int id) {
        return jdbcTemplate.update("DELETE FROM PaymentInfo WHERE ID=?", id);
    }

    @Override
    public List<PaymentInfo> findAll() {
        return jdbcTemplate.query("SELECT * FROM PaymentInfo", new BeanPropertyRowMapper<>(PaymentInfo.class));
    }

    @Override
    public int deleteAll() {
        return jdbcTemplate.update("DELETE FROM PaymentInfo");
    }
}

