package com.cineplanet.demo.repository;

import com.cineplanet.demo.entity.Premiere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PremiereRepository extends JpaRepository<Premiere, Long> {
}
