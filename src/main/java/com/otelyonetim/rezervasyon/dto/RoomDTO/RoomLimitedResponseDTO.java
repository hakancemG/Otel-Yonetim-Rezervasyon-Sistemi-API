package com.otelyonetim.rezervasyon.dto.RoomDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomLimitedResponseDTO {
    private String roomNumber;
    private String roomType;
    private Integer capacity;
    private BigDecimal pricePerNight;
}
