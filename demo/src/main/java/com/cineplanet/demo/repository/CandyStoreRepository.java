package com.cineplanet.demo.repository;

import com.cineplanet.demo.entity.CandyStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandyStoreRepository extends JpaRepository<CandyStore, Long> {
}
