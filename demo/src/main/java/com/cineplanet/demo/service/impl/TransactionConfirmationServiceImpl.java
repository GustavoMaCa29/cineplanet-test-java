package com.cineplanet.demo.service.impl;

import com.cineplanet.demo.entity.TransactionConfirmation;
import com.cineplanet.demo.service.interfaces.TransactionConfirmationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class TransactionConfirmationServiceImpl implements TransactionConfirmationService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TransactionConfirmationServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveTransaction(TransactionConfirmation confirmation) {
        String sql = "CALL save_transaction_confirmation(?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                confirmation.getEmail(),
                confirmation.getName(),
                confirmation.getDocumentNumber(),
                confirmation.getTransactionId(),
                confirmation.getOperationDate()
        );
    }
}