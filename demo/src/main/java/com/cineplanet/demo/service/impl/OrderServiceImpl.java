package com.cineplanet.demo.service.impl;

import com.cineplanet.demo.controller.OrderController;
import com.cineplanet.demo.entity.Orders;
import com.cineplanet.demo.service.interfaces.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public OrderServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Orders createOrder(Orders order) {
        String sql = "CALL create_order(?, ?)";
        logger.info("Creando orden para usuario: {}, total: {}", order.getUser(), order.getTotal());

        try {
            jdbcTemplate.update(sql, order.getUser(), order.getTotal());
            logger.info("Orden creada exitosamente");
        } catch (Exception e) {
            logger.error("Error al crear la orden", e);
            throw e;
        }

        return order;
    }
}
