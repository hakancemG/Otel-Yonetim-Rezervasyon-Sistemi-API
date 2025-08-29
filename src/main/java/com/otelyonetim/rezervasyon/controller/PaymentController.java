package com.otelyonetim.rezervasyon.controller;

import com.otelyonetim.rezervasyon.dto.PaymentDTO.PaymentCreateDTO;
import com.otelyonetim.rezervasyon.dto.PaymentDTO.PaymentFullResponseDTO;
import com.otelyonetim.rezervasyon.dto.PaymentDTO.PaymentLimitedResponseDTO;
import com.otelyonetim.rezervasyon.dto.PaymentDTO.PaymentUpdateDTO;
import com.otelyonetim.rezervasyon.enums.PaymentStatus;
import com.otelyonetim.rezervasyon.service.payment.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/reservation/{reservationId}")
    public ResponseEntity<PaymentFullResponseDTO> createPayment(
            @PathVariable Long reservationId,
            @Valid @RequestBody PaymentCreateDTO paymentCreateDTO) {
        PaymentFullResponseDTO createdPayment = paymentService.createPayment(paymentCreateDTO, reservationId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPayment);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentFullResponseDTO> getPaymentById(@PathVariable Long id) {
        PaymentFullResponseDTO payment = paymentService.getPaymentById(id);
        return ResponseEntity.ok(payment);
    }

    @GetMapping
    public ResponseEntity<List<PaymentFullResponseDTO>> getAllPayments() {
        List<PaymentFullResponseDTO> payments = paymentService.getAllPayments();
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/limited")
    public ResponseEntity<List<PaymentLimitedResponseDTO>> getAllPaymentsLimited() {
        List<PaymentLimitedResponseDTO> payments = paymentService.getAllPaymentsLimited();
        return ResponseEntity.ok(payments);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentFullResponseDTO> updatePayment(
            @PathVariable Long id,
            @Valid @RequestBody PaymentUpdateDTO paymentUpdateDTO) {
        PaymentFullResponseDTO updatedPayment = paymentService.updatePayment(id, paymentUpdateDTO);
        return ResponseEntity.ok(updatedPayment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/reservation/{reservationId}")
    public ResponseEntity<List<PaymentFullResponseDTO>> getPaymentsByReservationId(
            @PathVariable Long reservationId) {
        List<PaymentFullResponseDTO> payments = paymentService.getPaymentsByReservationId(reservationId);
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<PaymentFullResponseDTO>> getPaymentsByStatus(
            @PathVariable PaymentStatus status) {
        List<PaymentFullResponseDTO> payments = paymentService.getPaymentsByStatus(status);
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<PaymentFullResponseDTO>> getPaymentsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<PaymentFullResponseDTO> payments = paymentService.getPaymentsByDateRange(startDate, endDate);
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/transaction/{transactionId}")
    public ResponseEntity<PaymentFullResponseDTO> getPaymentByTransactionId(
            @PathVariable String transactionId) {
        PaymentFullResponseDTO payment = paymentService.getPaymentByTransactionId(transactionId);
        return ResponseEntity.ok(payment);
    }

    @PostMapping("/{id}/refund")
    public ResponseEntity<PaymentFullResponseDTO> processRefund(
            @PathVariable Long id,
            @RequestParam BigDecimal refundAmount,
            @RequestParam String refundReason) {
        PaymentFullResponseDTO refundedPayment = paymentService.processRefund(id, refundAmount, refundReason);
        return ResponseEntity.ok(refundedPayment);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<PaymentFullResponseDTO> updatePaymentStatus(
            @PathVariable Long id,
            @RequestParam PaymentStatus status) {
        PaymentFullResponseDTO updatedPayment = paymentService.updatePaymentStatus(id, status);
        return ResponseEntity.ok(updatedPayment);
    }

    @GetMapping("/revenue")
    public ResponseEntity<BigDecimal> getTotalRevenue(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        BigDecimal revenue = paymentService.getTotalRevenue(startDate, endDate);
        return ResponseEntity.ok(revenue);
    }

    @GetMapping("/refunds")
    public ResponseEntity<BigDecimal> getTotalRefunds(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        BigDecimal refunds = paymentService.getTotalRefunds(startDate, endDate);
        return ResponseEntity.ok(refunds);
    }
}