package com.otelyonetim.rezervasyon.repository;

import com.otelyonetim.rezervasyon.entity.Reservation;
import com.otelyonetim.rezervasyon.enums.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    // Tüm aktif rezervasyonları getir (silinmemiş)
    @Query("SELECT r FROM Reservation r WHERE r.isDeleted = false ORDER BY r.checkInDate")
    List<Reservation> findAllActive();

    // ID ile rezervasyon bul (silinmemiş)
    @Query("SELECT r FROM Reservation r WHERE r.id = :id AND r.isDeleted = false")
    Optional<Reservation> findByIdAndNotDeleted(@Param("id") Long id);

    // Müşteri ID'ye göre rezervasyonları getir
    @Query("SELECT r FROM Reservation r WHERE r.customer.id = :customerId AND r.isDeleted = false ORDER BY r.checkInDate")
    List<Reservation> findByCustomerId(@Param("customerId") Long customerId);

    // Oda ID'ye göre rezervasyonları getir
    @Query("SELECT r FROM Reservation r WHERE r.room.id = :roomId AND r.isDeleted = false ORDER BY r.checkInDate")
    List<Reservation> findByRoomId(@Param("roomId") Long roomId);

    // Statüye göre rezervasyonları getir
    @Query("SELECT r FROM Reservation r WHERE r.status = :status AND r.isDeleted = false ORDER BY r.checkInDate")
    List<Reservation> findByStatus(@Param("status") ReservationStatus status);

    // Tarih aralığındaki rezervasyonları getir
    @Query("SELECT r FROM Reservation r WHERE r.checkInDate BETWEEN :startDate AND :endDate AND r.isDeleted = false ORDER BY r.checkInDate")
    List<Reservation> findByCheckInDateBetween(@Param("startDate") LocalDateTime startDate,
                                               @Param("endDate") LocalDateTime endDate);

    // Oda çakışma kontrolü
    @Query("SELECT r FROM Reservation r WHERE r.room.id = :roomId AND r.isDeleted = false " +
            "AND r.status NOT IN ('CANCELLED', 'NOSHOW') " +
            "AND ((r.checkInDate < :checkOutDate AND r.checkOutDate > :checkInDate))")
    List<Reservation> findConflictingReservations(@Param("roomId") Long roomId,
                                                  @Param("checkInDate") LocalDateTime checkInDate,
                                                  @Param("checkOutDate") LocalDateTime checkOutDate);

    // Müşteriye göre aktif rezervasyonlar
    @Query("SELECT r FROM Reservation r WHERE r.customer.id = :customerId AND r.isDeleted = false " +
            "AND r.status NOT IN ('CANCELLED', 'NOSHOW', 'COMPLETED') " +
            "ORDER BY r.checkInDate")
    List<Reservation> findActiveReservationsByCustomerId(@Param("customerId") Long customerId);

    // Yaklaşan check-in'ler
    @Query("SELECT r FROM Reservation r WHERE r.checkInDate BETWEEN :start AND :end " +
            "AND r.isDeleted = false AND r.status = 'CONFIRMED' " +
            "ORDER BY r.checkInDate")
    List<Reservation> findUpcomingCheckIns(@Param("start") LocalDateTime start,
                                           @Param("end") LocalDateTime end);
}