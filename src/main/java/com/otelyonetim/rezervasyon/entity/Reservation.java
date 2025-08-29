package com.otelyonetim.rezervasyon.entity;

import com.otelyonetim.rezervasyon.enums.ReservationStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(
        name = "reservations",
        indexes = {
                @Index(name = "idx_reservation_status", columnList = "status"),
                @Index(name = "idx_check_in_date", columnList = "check_in_date"),
                @Index(name = "idx_check_out_date", columnList = "check_out_date"),
                @Index(name = "idx_reservation_customer", columnList = "customer_id"),
                @Index(name = "idx_reservation_room", columnList = "room_id")
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE reservations SET is_deleted = true, deleted_at = NOW() WHERE id=?")
@Where(clause = "is_deleted = false")
public class Reservation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "check_in_date", nullable = false)
    @FutureOrPresent(message = "Check-in tarihi gelecekte veya bugünde olmalıdır!")
    private LocalDateTime checkInDate;

    @Column(name = "check_out_date", nullable = false)
    @FutureOrPresent(message = "Check-out tarihi gelecekte veya bugünde olmalıdır!")
    private LocalDateTime checkOutDate;

    @NotNull(message = "Yetişkin sayısı belirtilmelidir!")
    @Column(name = "adult_count", nullable = false)
    private Integer adultCount;

    @Column(name = "child_count")
    private Integer childCount;

    @Column(name = "total_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice;

    @Column(columnDefinition = "TEXT")
    private String note;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ReservationStatus status = ReservationStatus.PENDING;

    @Column(name = "cancellation_reason", length = 250)
    private String cancellationReason;

    @Column(name = "confirmation_number", unique = true, length = 20)
    private String confirmationNumber;

    @Column(name = "cancelled_at")
    private LocalDateTime cancelledAt;

    @Column(name = "checked_in_at")
    private LocalDateTime checkedInAt;

    @Column(name = "checked_out_at")
    private LocalDateTime checkedOutAt;

    // HELPER METOTLAR
    public boolean isActive() {
        return status != ReservationStatus.CANCELLED &&
                status != ReservationStatus.NOSHOW;
    }

    public boolean isCheckedIn() {
        return checkedInAt != null;
    }

    public boolean isCheckedOut() {
        return checkedOutAt != null;
    }

    public boolean isCancelled() {
        return status == ReservationStatus.CANCELLED;
    }

    // LIFECYCLE: Status default ayarı için override
    @PrePersist
    @Override
    protected void onCreate() {
        super.onCreate();
        if (status == null) {
            status = ReservationStatus.PENDING;
        }
        // Onay numarası oluştur
        if (confirmationNumber == null) {
            this.confirmationNumber = generateConfirmationNumber();
        }
    }

    private String generateConfirmationNumber() {
        return "RES" + System.currentTimeMillis() + (int)(Math.random() * 1000);
    }

    // RELATIONS
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id",
            nullable = false,
            foreignKey = @ForeignKey(name ="fk_reservation_customer"))
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_id",
            nullable = false,
            foreignKey = @ForeignKey(name="fk_reservation_room"))
    private Room room;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, fetch = FetchType.LAZY) // OneToOne yerine OneToMany
    private List<Payment> payments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_user_id",
            foreignKey = @ForeignKey(name="fk_reservation_created_by"))
    private User createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by_user_id",
            foreignKey = @ForeignKey(name="fk_reservation_updated_by"))
    private User updatedBy;
}