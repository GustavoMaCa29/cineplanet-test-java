package com.cineplanet.demo.controller;

import com.cineplanet.demo.entity.PaymentRequest;
import com.cineplanet.demo.entity.TransactionConfirmation;
import com.cineplanet.demo.service.interfaces.TransactionConfirmationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final TransactionConfirmationService transactionConfirmationService;

    public PaymentController(TransactionConfirmationService transactionConfirmationService) {
        this.transactionConfirmationService = transactionConfirmationService;
    }

    @PostMapping("/payu")
    public ResponseEntity<?> processPayment(@Valid @RequestBody PaymentRequest request) {
        try {
            String response = transactionConfirmationService.processPayment(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    new Error("Error al procesar el pago: " + e.getMessage())
            );
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