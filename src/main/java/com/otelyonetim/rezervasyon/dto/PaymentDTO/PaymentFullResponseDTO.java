package com.otelyonetim.rezervasyon.dto.PaymentDTO;

import com.otelyonetim.rezervasyon.enums.Currency;
import com.otelyonetim.rezervasyon.enums.PaymentMethod;
import com.otelyonetim.rezervasyon.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentFullResponseDTO {
    private Long id;
    private BigDecimal amount;
    private Currency currency;
    private PaymentMethod method;
    private PaymentStatus status;
    private String transactionId;
    private Long reservationId;
    private LocalDateTime paymentDate;
    private BigDecimal refundedAmount;
    private String refundReason;
    private LocalDateTime refundDate;
    private String cardHolderName;
    private String cardBrand;
    private String cardLastDigits;
    private LocalDateTime cardExpiryDate;
    private String note;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
