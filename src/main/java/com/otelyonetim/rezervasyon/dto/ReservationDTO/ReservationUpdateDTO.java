package com.otelyonetim.rezervasyon.dto.ReservationDTO;

import com.otelyonetim.rezervasyon.enums.ReservationStatus;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
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
public class ReservationUpdateDTO {
    @FutureOrPresent(message = "Check-in tarihi bugün veya gelecekteki bir gün olmalıdır!")
    private LocalDateTime checkInDate;

    @FutureOrPresent(message = "Check-out tarihi bugün veya gelecekteki bir gün olmalıdır!")
    private LocalDateTime checkOutDate;

    @Min(value = 1, message = "En az 1 yetişkin gereklidir!") // EKSİK: Validasyon eklendi
    private Integer adultCount;

    @Min(value = 0, message = "Çocuk sayısı 0 veya daha büyük olmalıdır!") // EKSİK: Validasyon eklendi
    private Integer childCount;

    @Min(value = 0, message = "Toplam fiyat 0 veya daha büyük olmalıdır!") // EKSİK: Validasyon eklendi
    private BigDecimal totalPrice;

    private ReservationStatus status;

    @Size(max = 250, message = "İptal sebebi 250 karakterden fazla olamaz!") // 200 → 250 (entity 250 karakter)
    private String cancellationReason;

    private LocalDateTime cancelledAt;

    private LocalDateTime checkedInAt;

    private LocalDateTime checkedOutAt;

    @Size(max = 500, message = "Not, 500 karakteri aşamaz!") // 200 → 500 (entity TEXT olduğu için)
    private String note;
}