package com.otelyonetim.rezervasyon.service.payment;

import com.otelyonetim.rezervasyon.dto.PaymentDTO.PaymentCreateDTO;
import com.otelyonetim.rezervasyon.dto.PaymentDTO.PaymentFullResponseDTO;
import com.otelyonetim.rezervasyon.dto.PaymentDTO.PaymentLimitedResponseDTO;
import com.otelyonetim.rezervasyon.dto.PaymentDTO.PaymentUpdateDTO;
import com.otelyonetim.rezervasyon.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface PaymentService {

    PaymentFullResponseDTO createPayment(PaymentCreateDTO paymentCreateDTO, Long reservationId);

    PaymentFullResponseDTO getPaymentById(Long id);

    List<PaymentFullResponseDTO> getAllPayments();

    List<PaymentLimitedResponseDTO> getAllPaymentsLimited();

    PaymentFullResponseDTO updatePayment(Long id, PaymentUpdateDTO paymentUpdateDTO);

    void deletePayment(Long id);

    List<PaymentFullResponseDTO> getPaymentsByReservationId(Long reservationId);

    List<PaymentFullResponseDTO> getPaymentsByStatus(PaymentStatus status);

    List<PaymentFullResponseDTO> getPaymentsByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    PaymentFullResponseDTO getPaymentByTransactionId(String transactionId);

    PaymentFullResponseDTO processRefund(Long id, BigDecimal refundAmount, String refundReason);

    PaymentFullResponseDTO updatePaymentStatus(Long id, PaymentStatus status);

    BigDecimal getTotalRevenue(LocalDateTime startDate, LocalDateTime endDate);

    BigDecimal getTotalRefunds(LocalDateTime startDate, LocalDateTime endDate);
}