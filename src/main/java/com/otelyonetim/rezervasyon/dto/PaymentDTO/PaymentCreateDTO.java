package com.otelyonetim.rezervasyon.dto.PaymentDTO;

import com.otelyonetim.rezervasyon.enums.Currency;
import com.otelyonetim.rezervasyon.enums.PaymentMethod;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
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
public class PaymentCreateDTO {

    @NotNull(message = "Miktar giriniz!")
    @DecimalMin(value = "0.0", inclusive = false, message = "Miktar sıfır'dan büyük olmalıdır!")
    private BigDecimal amount;

    @NotNull(message = "Kur gereklidir!")
    private Currency currency;

    @NotNull(message = "Ödeme yöntemi giriniz!")
    private PaymentMethod PaymentMethod;

    @Size(min = 6, max = 40, message = "Kart sahibi ismi 6-40 karakter arasında olmalıdır!")
    private String cardHolderName;

    @Pattern(regexp = "^[0-9]{4}$", message = "Yalnızca 4 rakam kabul edilmektedir!")
    private String cardLastDigits;

    private String cardBrand;

    @FutureOrPresent(message = "Kart son kullanma tarihi eçmiş bir zaman olamaz!")
    private LocalDateTime cardExpiryDate;

    @Column(columnDefinition = "TEXT")
    private String note;
}
