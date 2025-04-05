package com.cineplanet.demo.repository;

import com.cineplanet.demo.entity.TransactionConfirmation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionConfirmationRepository extends JpaRepository<TransactionConfirmation, Long> {
}