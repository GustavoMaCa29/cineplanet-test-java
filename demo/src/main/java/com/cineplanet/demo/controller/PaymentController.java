package com.cineplanet.demo.controller;

import com.cineplanet.demo.entity.PaymentRequest;
import com.cineplanet.demo.entity.TransactionConfirmation;
import com.cineplanet.demo.service.interfaces.TransactionConfirmationService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final RestTemplate restTemplate = new RestTemplate();

    private final TransactionConfirmationService transactionConfirmationService;

    public PaymentController(TransactionConfirmationService transactionConfirmationService) {
        this.transactionConfirmationService = transactionConfirmationService;
    }

    @PostMapping("/payu")
    public ResponseEntity<?> processPayment(@RequestBody PaymentRequest request) {
        String url = "https://sandbox.api.payulatam.com/payments-api/4.0/service.cgi";

        String apiKey = "4Vj8eK4rloUd272L48hsrarnUA";
        String apiLogin = "pRRXKOl8ikMmt9u";
        String merchantId = "508029";
        String accountId = "512323"; // Perú
        String currency = "PEN";
        String referenceCode = "PEDIDO_" + UUID.randomUUID();
        String description = "Compra en Cineplanet";

        String signature = generateSignature(apiKey, merchantId, referenceCode, request.getAmount(), currency);

        Map<String, Object> merchant = new HashMap<>();
        merchant.put("apiKey", apiKey);
        merchant.put("apiLogin", apiLogin);

        Map<String, Object> buyer = new HashMap<>();
        buyer.put("emailAddress", request.getEmail());

        Map<String, Object> txValue = new HashMap<>();
        System.out.println("Amount recibido: " + request.getAmount());
        txValue.put("value", request.getAmount());
        txValue.put("currency", currency);

        Map<String, Object> additionalValues = new HashMap<>();
        additionalValues.put("TX_VALUE", txValue);

        Map<String, Object> order = new HashMap<>();
        order.put("accountId", accountId);
        order.put("referenceCode", referenceCode);
        order.put("description", description);
        order.put("language", "es");
        order.put("signature", signature);
        order.put("buyer", buyer);
        order.put("additionalValues", additionalValues);

        Map<String, Object> creditCard = new HashMap<>();
        creditCard.put("number", request.getCardNumber());
        creditCard.put("securityCode", request.getCvv());
        creditCard.put("expirationDate", request.getExpiration()); // YYYY/MM
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
        return ResponseEntity.ok(response.getBody());
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

    @PostMapping("/complete")
    public ResponseEntity<?> completePayment(@RequestBody TransactionConfirmation request) {
        transactionConfirmationService.saveTransaction(request);

        Map<String, Object> response = new HashMap<>();
        response.put("code", "0");
        response.put("message", "Transacción completada");
        return ResponseEntity.ok(response);
    }
}