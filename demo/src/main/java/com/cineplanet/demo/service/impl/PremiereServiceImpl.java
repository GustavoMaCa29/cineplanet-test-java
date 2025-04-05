package com.cineplanet.demo.service.impl;

import com.cineplanet.demo.dto.PremiereSummaryDto;
import com.cineplanet.demo.entity.Premiere;
import com.cineplanet.demo.service.interfaces.PremiereService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PremiereServiceImpl implements PremiereService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PremiereServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<PremiereSummaryDto> getAllPremieres() {
        String sql = "CALL get_all_premiers()";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(PremiereSummaryDto.class));
    }
}
