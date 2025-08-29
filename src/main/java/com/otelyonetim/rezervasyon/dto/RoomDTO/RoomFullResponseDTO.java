package com.otelyonetim.rezervasyon.dto.RoomDTO;

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
public class RoomFullResponseDTO {
    private Long id;
    private String roomNumber;
    private String roomType;
    private Integer capacity;
    private BigDecimal pricePerNight;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private Boolean isDeleted;
    private List<Long> reservationIds;
}
