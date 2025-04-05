package com.cineplanet.demo.service.impl;

import com.cineplanet.demo.controller.CandyStoreController;
import com.cineplanet.demo.dto.CandyStoreSummaryDto;
import com.cineplanet.demo.entity.CandyStore;
import com.cineplanet.demo.service.interfaces.CandyStoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandyStoreServiceImpl implements CandyStoreService {

    private final JdbcTemplate jdbcTemplate;

    private static final Logger logger = LoggerFactory.getLogger(CandyStoreController.class);

    @Autowired
    public CandyStoreServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<CandyStoreSummaryDto> getAllCandyStores() {
        String sql = "CALL get_all_candy_stores()";
        logger.info("Ejecutando procedimiento: {}", sql);

        try {
            List<CandyStoreSummaryDto> result = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(CandyStoreSummaryDto.class));
            logger.info("Se obtuvieron {} productos de la dulcería", result.size());
            return result;
        } catch (Exception e) {
            logger.error("Error al ejecutar procedimiento almacenado", e);
            throw e;
        }
    }

    @Override
    public CandyStore getCandyStoreById(Long id) {
        // Obtener una tienda de caramelos por su ID (con JPA, o si usas procedimiento almacenado, implementa uno para esto)
        return null;
    }
}
