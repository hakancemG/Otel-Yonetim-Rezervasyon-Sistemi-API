package com.otelyonetim.rezervasyon.controller;

import com.otelyonetim.rezervasyon.dto.ReservationDTO.ReservationCreateDTO;
import com.otelyonetim.rezervasyon.dto.ReservationDTO.ReservationFullResponseDTO;
import com.otelyonetim.rezervasyon.dto.ReservationDTO.ReservationLimitedResponseDTO;
import com.otelyonetim.rezervasyon.dto.ReservationDTO.ReservationUpdateDTO;
import com.otelyonetim.rezervasyon.enums.ReservationStatus;
import com.otelyonetim.rezervasyon.service.reservation.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationFullResponseDTO> createReservation(
            @Valid @RequestBody ReservationCreateDTO reservationCreateDTO) {
        ReservationFullResponseDTO createdReservation = reservationService.createReservation(reservationCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReservation);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationFullResponseDTO> getReservationById(@PathVariable Long id) {
        ReservationFullResponseDTO reservation = reservationService.getReservationById(id);
        return ResponseEntity.ok(reservation);
    }

    @GetMapping
    public ResponseEntity<List<ReservationFullResponseDTO>> getAllReservations() {
        List<ReservationFullResponseDTO> reservations = reservationService.getAllReservations();
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/limited")
    public ResponseEntity<List<ReservationLimitedResponseDTO>> getAllReservationsLimited() {
        List<ReservationLimitedResponseDTO> reservations = reservationService.getAllReservationsLimited();
        return ResponseEntity.ok(reservations);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservationFullResponseDTO> updateReservation(
            @PathVariable Long id,
            @Valid @RequestBody ReservationUpdateDTO reservationUpdateDTO) {
        ReservationFullResponseDTO updatedReservation = reservationService.updateReservation(id, reservationUpdateDTO);
        return ResponseEntity.ok(updatedReservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<ReservationFullResponseDTO>> getReservationsByCustomerId(
            @PathVariable Long customerId) {
        List<ReservationFullResponseDTO> reservations = reservationService.getReservationsByCustomerId(customerId);
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<ReservationFullResponseDTO>> getReservationsByRoomId(
            @PathVariable Long roomId) {
        List<ReservationFullResponseDTO> reservations = reservationService.getReservationsByRoomId(roomId);
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<ReservationFullResponseDTO>> getReservationsByStatus(
            @PathVariable ReservationStatus status) {
        List<ReservationFullResponseDTO> reservations = reservationService.getReservationsByStatus(status);
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<ReservationFullResponseDTO>> getReservationsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<ReservationFullResponseDTO> reservations = reservationService.getReservationsByDateRange(startDate, endDate);
        return ResponseEntity.ok(reservations);
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<ReservationFullResponseDTO> cancelReservation(
            @PathVariable Long id,
            @RequestParam String cancellationReason) {
        ReservationFullResponseDTO cancelledReservation = reservationService.cancelReservation(id, cancellationReason);
        return ResponseEntity.ok(cancelledReservation);
    }

    @PostMapping("/{id}/checkin")
    public ResponseEntity<ReservationFullResponseDTO> checkIn(@PathVariable Long id) {
        ReservationFullResponseDTO checkedInReservation = reservationService.checkIn(id);
        return ResponseEntity.ok(checkedInReservation);
    }

    @PostMapping("/{id}/checkout")
    public ResponseEntity<ReservationFullResponseDTO> checkOut(@PathVariable Long id) {
        ReservationFullResponseDTO checkedOutReservation = reservationService.checkOut(id);
        return ResponseEntity.ok(checkedOutReservation);
    }

    @GetMapping("/availability")
    public ResponseEntity<Boolean> checkRoomAvailability(
            @RequestParam Long roomId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime checkInDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime checkOutDate) {
        boolean available = reservationService.hasRoomAvailability(roomId, checkInDate, checkOutDate);
        return ResponseEntity.ok(available);
    }
}