package com.cineplanet.demo.service.impl;

import com.cineplanet.demo.config.PayuProperties;
import com.cineplanet.demo.controller.CandyStoreController;
import com.cineplanet.demo.entity.PaymentRequest;
import com.cineplanet.demo.entity.TransactionConfirmation;
import com.cineplanet.demo.service.interfaces.TransactionConfirmationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class TransactionConfirmationServiceImpl implements TransactionConfirmationService {

    private final JdbcTemplate jdbcTemplate;

    private final PayuProperties properties;

    private final RestTemplate restTemplate = new RestTemplate();

    private static final Logger logger = LoggerFactory.getLogger(CandyStoreController.class);


    @Autowired
    public TransactionConfirmationServiceImpl(JdbcTemplate jdbcTemplate, PayuProperties properties) {
        this.jdbcTemplate = jdbcTemplate;
        this.properties = properties;
    }

    @Override
    public String processPayment(PaymentRequest request) {
        String url = properties.getUrl();
        String referenceCode = "PEDIDO_" + UUID.randomUUID();
        String signature = generateSignature(properties.getApiKey(), properties.getMerchantId(), referenceCode, request.getAmount(), properties.getCurrency());

        Map<String, Object> merchant = new HashMap<>();
        merchant.put("apiKey", properties.getApiKey());
        merchant.put("apiLogin", properties.getApiLogin());

        Map<String, Object> buyer = new HashMap<>();
        buyer.put("emailAddress", request.getEmail());

        Map<String, Object> txValue = new HashMap<>();
        txValue.put("value", request.getAmount());
        txValue.put("currency", properties.getCurrency());

        Map<String, Object> additionalValues = new HashMap<>();
        additionalValues.put("TX_VALUE", txValue);

        Map<String, Object> order = new HashMap<>();
        order.put("accountId", properties.getAccountId());
        order.put("referenceCode", referenceCode);
        order.put("description", properties.getDescription());
        order.put("language", "es");
        order.put("signature", signature);
        order.put("buyer", buyer);
        order.put("additionalValues", additionalValues);

        Map<String, Object> creditCard = new HashMap<>();
        creditCard.put("number", request.getCardNumber());
        creditCard.put("securityCode", request.getCvv());
        creditCard.put("expirationDate", request.getExpiration());
        creditCard.put("name", request.getName());

        Map<String, Object> payer = new HashMap<>();
        payer.put("emailAddress", request.getEmail());
        payer.put("fullName", request.getName());

        Map<String, Object> transaction = new HashMap<>();
        transaction.put("order", order);
        transaction.put("creditCard", creditCard);
        transaction.put("type", "AUTHORIZATION_AND_CAPTURE");
        transaction.put("paymentMethod", "VISA");
        transaction.put("paymentCountry", "PE");
        transaction.put("payer", payer);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("language", "es");
        requestBody.put("command", "SUBMIT_TRANSACTION");
        requestBody.put("merchant", merchant);
        requestBody.put("transaction", transaction);
        requestBody.put("test", true);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
        return response.getBody();
    }

    private String generateSignature(String apiKey, String merchantId, String referenceCode, Double amount, String currency) {
        try {
            String data = apiKey + "~" + merchantId + "~" + referenceCode + "~" + String.format("%.1f", amount) + "~" + currency;
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(data.getBytes(StandardCharsets.UTF_8));
            BigInteger number = new BigInteger(1, digest);
            return String.format("%032x", number);
        } catch (Exception e) {
            throw new RuntimeException("Error al generar firma", e);
        }
    }

    @Override
    public void saveTransaction(TransactionConfirmation confirmation) {
        String sql = "CALL save_transaction_confirmation(?, ?, ?, ?, ?)";

        try {
            jdbcTemplate.update(sql,
                    confirmation.getEmail(),
                    confirmation.getName(),
                    confirmation.getDocumentNumber(),
                    confirmation.getTransactionId(),
                    confirmation.getOperationDate()
            );
        } catch (Exception e) {
            logger.error("Error al guardar la transacción: " + e.getMessage(), e);
            throw new RuntimeException("No se pudo concretar la operación");
        }
    }
}