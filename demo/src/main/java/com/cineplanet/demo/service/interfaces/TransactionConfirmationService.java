package com.cineplanet.demo.service.interfaces;

import com.cineplanet.demo.entity.PaymentRequest;
import com.cineplanet.demo.entity.TransactionConfirmation;

public interface TransactionConfirmationService {
    void saveTransaction(TransactionConfirmation confirmation);

    String processPayment(PaymentRequest request);
}
