package com.otelyonetim.rezervasyon.dto.RoomDTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomCreateDTO {
    @NotBlank
    private String roomNumber;

    @NotBlank
    private String roomType;

    @NotNull
    @Min(0)
    private Integer capacity;

    @NotNull
    private BigDecimal pricePerNight;

    private String description;
}
