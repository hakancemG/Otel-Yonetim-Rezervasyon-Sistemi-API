package com.otelyonetim.rezervasyon.dto.ReservationDTO;

import com.otelyonetim.rezervasyon.dto.CustomerDTO.CustomerLimitedResponseDTO;
import com.otelyonetim.rezervasyon.dto.PaymentDTO.PaymentLimitedResponseDTO;
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
public class ReservationFullResponseDTO {
    private Long id;
    private CustomerLimitedResponseDTO customer; // Nested minimal customer info
    private Long roomId; // Room detay gerekiyorsa nested RoomSummaryDTO eklenebilir
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;
    private Integer adultCount;
    private Integer childCount;
    private BigDecimal totalPrice;
    private String confirmationNumber;
    private ReservationStatus status;
    private String cancellationReason;
    private LocalDateTime cancelledAt;
    private LocalDateTime checkedInAt;
    private LocalDateTime checkedOutAt;
    private String note;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<PaymentLimitedResponseDTO> payments; // Minimal payment info
}
