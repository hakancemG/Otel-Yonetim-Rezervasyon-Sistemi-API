package com.otelyonetim.rezervasyon.dto.ReservationDTO;

import com.otelyonetim.rezervasyon.enums.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationLimitedResponseDTO {
    private Long customerId;
    private Long roomId;
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;
    private BigDecimal totalPrice;
    private ReservationStatus status;
    // Ödeme bilgileri minimal (ID listesi)
    private List<Long> paymentIds;
}
