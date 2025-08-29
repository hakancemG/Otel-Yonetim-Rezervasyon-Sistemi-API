package com.otelyonetim.rezervasyon.dto.PaymentDTO;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentUpdateDTO {
    @DecimalMin(value = "0.0", inclusive = false, message = "İade miktarı sıfırdan büyük olmalı!")
    private BigDecimal refundedAmount;

    @Size(max = 200, message = "İade sebebi 200 karakteri aşamaz!")
    private String refundReason;

    private String note;
}
