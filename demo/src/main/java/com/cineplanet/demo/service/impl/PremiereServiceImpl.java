package com.cineplanet.demo.service.impl;

import com.cineplanet.demo.controller.CandyStoreController;
import com.cineplanet.demo.dto.PremiereSummaryDto;
import com.cineplanet.demo.service.interfaces.PremiereService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PremiereServiceImpl implements PremiereService {

    private final JdbcTemplate jdbcTemplate;

    private static final Logger logger = LoggerFactory.getLogger(CandyStoreController.class);


    @Autowired
    public PremiereServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<PremiereSummaryDto> getAllPremieres() {
        String sql = "CALL get_all_premiers()";
        try {
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(PremiereSummaryDto.class));
        } catch (DataAccessException e) {
            logger.error("Error al obtener las premieres desde la BD", e);
            throw new RuntimeException("No se pudieron obtener las premieres");
        }
    }
}
