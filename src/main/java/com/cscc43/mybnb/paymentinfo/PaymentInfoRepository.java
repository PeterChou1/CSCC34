package com.cscc43.mybnb.paymentinfo;

import java.util.List;

public interface PaymentInfoRepository {
    int save(PaymentInfo paymentInfo);
    int update(PaymentInfo paymentInfo);
    PaymentInfo findByID(int id);
    List<PaymentInfo> findPaymentInfoByUserID(String id);
    int deleteById(int id);
    List<PaymentInfo> findAll();
    int deleteAll();
}
