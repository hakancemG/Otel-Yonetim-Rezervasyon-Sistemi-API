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
public class PaymentLimitedResponseDTO {
    private BigDecimal amount;
    private Currency currency;
    private PaymentMethod PaymentMethod;
    private PaymentStatus status;
    private LocalDateTime paymentDate;
}
