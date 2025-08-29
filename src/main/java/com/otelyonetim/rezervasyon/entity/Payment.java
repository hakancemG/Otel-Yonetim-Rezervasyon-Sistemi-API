package com.otelyonetim.rezervasyon.entity;

import com.otelyonetim.rezervasyon.enums.Currency;
import com.otelyonetim.rezervasyon.enums.PaymentMethod;
import com.otelyonetim.rezervasyon.enums.PaymentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name="payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ÖDEME TUTARI
    @Column(nullable = false)
    @DecimalMin(value = "0.0", inclusive = false, message = "Ödeme tutarı 0'dan büyük olmalıdır")
    private BigDecimal amount;

    // KART BİLGİLERİ İÇİN.
    @Size(min=6, max=40, message = "Kart sahibi adı 6-40 karakter arasında olmalıdır!")
    private String cardHolderName;

    @Pattern(regexp = "^[0-9]{4}$", message = "Son 4 hane olmalıdır")
    private String cardLastDigits;

    private String cardBrand;

    @FutureOrPresent(message = "Ödeme tarihi geçmişte olamaz!")
    private LocalDateTime cardExpiryDate;

    // ÖDEME SAĞLAYICISI İÇİN.
    private String cardGateway; // iyzico, stripe vs.

    private String gatewayTransactionId;

    private String gatewayResponseCode;

    private String gatewayResponseMessage;

    @Column(columnDefinition = "TEXT")
    private String note;

    // KUR İÇİN.
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 3)
    private Currency currency = Currency.TRY;

    // TARİH.
    @Column(nullable = false)
    private LocalDateTime paymentDate;

    // ÖDEME YÖNTEMİ.
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod paymentMethod;

    // DURUM.
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;

    // SİSTEM İÇİ TRANSACTION ID İÇİN.
    @Column(unique = true)
    private String transactionId;

    // İADE İÇİN.
    private BigDecimal refundedAmount;
    private String refundReason;
    private LocalDateTime refundDate;
    private String refundTransactionId;

    // LIFECYCLE İÇİN.
    @PrePersist
    protected void onCreatePayment() {
        super.onCreate(); // BaseEntity, createdAt ve updatedAt setler.
        if (paymentDate == null) {
            paymentDate = LocalDateTime.now();
        }
    }

    // RELATION İÇİN.
    @OneToOne
    @JoinColumn(name="reservation_id", nullable = false)
    private Reservation reservation;
}
