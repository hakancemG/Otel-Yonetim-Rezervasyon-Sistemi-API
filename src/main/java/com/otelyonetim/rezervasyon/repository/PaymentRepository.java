package com.otelyonetim.rezervasyon.repository;

import com.otelyonetim.rezervasyon.entity.Payment;
import com.otelyonetim.rezervasyon.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    // Rezervasyon ID'ye göre ödemeleri getir
    @Query("SELECT p FROM Payment p WHERE p.reservation.id = :reservationId ORDER BY p.paymentDate DESC")
    List<Payment> findByReservationId(@Param("reservationId") Long reservationId);

    // Statüye göre ödemeleri getir
    @Query("SELECT p FROM Payment p WHERE p.paymentStatus = :status ORDER BY p.paymentDate DESC")
    List<Payment> findByStatus(@Param("status") PaymentStatus status);

    // Tarih aralığındaki ödemeleri getir
    @Query("SELECT p FROM Payment p WHERE p.paymentDate BETWEEN :startDate AND :endDate ORDER BY p.paymentDate DESC")
    List<Payment> findByPaymentDateBetween(@Param("startDate") LocalDateTime startDate,
                                           @Param("endDate") LocalDateTime endDate);

    // Transaction ID'ye göre ödeme bul
    @Query("SELECT p FROM Payment p WHERE p.transactionId = :transactionId")
    Optional<Payment> findByTransactionId(@Param("transactionId") String transactionId);

    // Belirli tutarın üzerindeki ödemeleri getir
    @Query("SELECT p FROM Payment p WHERE p.amount >= :minAmount ORDER BY p.amount DESC")
    List<Payment> findByAmountGreaterThanEqual(@Param("minAmount") BigDecimal minAmount);

    // İade edilen ödemeleri getir
    @Query("SELECT p FROM Payment p WHERE p.refundedAmount IS NOT NULL ORDER BY p.refundDate DESC")
    List<Payment> findRefundedPayments();

    // Ödeme yöntemine göre ödemeleri getir
    @Query("SELECT p FROM Payment p WHERE p.paymentMethod = :paymentMethod ORDER BY p.paymentDate DESC")
    List<Payment> findByPaymentMethod(@Param("paymentMethod") String paymentMethod);

    // Rezervasyon ID ve statüye göre ödeme bul
    @Query("SELECT p FROM Payment p WHERE p.reservation.id = :reservationId AND p.paymentStatus = :status")
    Optional<Payment> findByReservationIdAndStatus(@Param("reservationId") Long reservationId,
                                                   @Param("status") PaymentStatus status);

    // Toplam ödeme tutarını hesapla (belirli tarih aralığında)
    @Query("SELECT COALESCE(SUM(p.amount), 0) FROM Payment p WHERE p.paymentDate BETWEEN :startDate AND :endDate AND p.paymentStatus = 'COMPLETED'")
    BigDecimal getTotalRevenueBetweenDates(@Param("startDate") LocalDateTime startDate,
                                           @Param("endDate") LocalDateTime endDate);

    // Toplam iade tutarını hesapla (belirli tarih aralığında)
    @Query("SELECT COALESCE(SUM(p.refundedAmount), 0) FROM Payment p WHERE p.refundDate BETWEEN :startDate AND :endDate")
    BigDecimal getTotalRefundsBetweenDates(@Param("startDate") LocalDateTime startDate,
                                           @Param("endDate") LocalDateTime endDate);
}