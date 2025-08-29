package com.otelyonetim.rezervasyon.entity;

import com.otelyonetim.rezervasyon.enums.RoomType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rooms", indexes = {
        @Index(name = "idx_room_number", columnList = "roomNumber"),
        @Index(name = "idx_room_type", columnList = "roomType"),
        @Index(name = "idx_room_active", columnList = "is_deleted")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE rooms SET is_deleted = true, deleted_at = NOW() WHERE id=?")
@Where(clause = "is_deleted = false")
public class Room extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Oda numarası boş bırakılamaz!")
    @Column(nullable = false, unique = true, length = 3)
    private String roomNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private RoomType roomType;

    @Min(0)
    @Column(nullable = false)
    private Integer capacity;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal pricePerNight;

    @Column(columnDefinition = "TEXT")
    private String description;

    // RELATION İÇİN.
    @OneToMany(mappedBy = "room", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private List<Reservation> reservations = new ArrayList<>();
}