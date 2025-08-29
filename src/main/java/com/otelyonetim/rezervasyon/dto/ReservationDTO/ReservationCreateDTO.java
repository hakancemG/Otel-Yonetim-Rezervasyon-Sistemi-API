package com.otelyonetim.rezervasyon.dto.ReservationDTO;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class ReservationCreateDTO {
    @NotNull(message = "Müşteri ID boş bırakılamaz!")
    private Long customerId;

    @NotNull(message = "Oda ID gereklidir!")
    private Long roomId;

    @NotNull(message = "Check-in tarihi gereklidir!")
    @FutureOrPresent(message = "Check-in tarihi bugün veya gelecekteki bir gün olmalıdır!")
    private LocalDateTime checkInDate;

    @NotNull(message = "Check-out tarihi gereklidir!")
    @FutureOrPresent(message = "Check-out tarihi bugün veya gelecekteki bir gün olmalıdır!")
    private LocalDateTime checkOutDate;

    @NotNull(message = "Yetişkin sayısı gereklidir!")
    @Min(value = 1, message = "En az 1 yetişkin gereklidir!")
    private Integer adultCount;

    @Min(value = 0, message = "Çocuk sayısı 0 veya daha büyük olmalıdır!") // EKSİK: Validasyon eklendi
    private Integer childCount;

    @NotNull(message = "Toplam fiyat gereklidir!")
    @Min(value = 0, message = "Toplam fiyat 0 veya daha büyük olmalıdır!")
    private BigDecimal totalPrice;

    @Size(max = 500, message = "Not, 500 karakteri aşamaz!") // 200 → 500 (entity TEXT olduğu için)
    private String note;
}