package com.otelyonetim.rezervasyon.service.reservation;

import com.otelyonetim.rezervasyon.dto.ReservationDTO.ReservationCreateDTO;
import com.otelyonetim.rezervasyon.dto.ReservationDTO.ReservationFullResponseDTO;
import com.otelyonetim.rezervasyon.dto.ReservationDTO.ReservationLimitedResponseDTO;
import com.otelyonetim.rezervasyon.dto.ReservationDTO.ReservationUpdateDTO;
import com.otelyonetim.rezervasyon.enums.ReservationStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationService {

    ReservationFullResponseDTO createReservation(ReservationCreateDTO reservationCreateDTO);

    ReservationFullResponseDTO getReservationById(Long id);

    List<ReservationFullResponseDTO> getAllReservations();

    List<ReservationLimitedResponseDTO> getAllReservationsLimited();

    ReservationFullResponseDTO updateReservation(Long id, ReservationUpdateDTO reservationUpdateDTO);

    void deleteReservation(Long id);

    List<ReservationFullResponseDTO> getReservationsByCustomerId(Long customerId);

    List<ReservationFullResponseDTO> getReservationsByRoomId(Long roomId);

    List<ReservationFullResponseDTO> getReservationsByStatus(ReservationStatus status);

    List<ReservationFullResponseDTO> getReservationsByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    ReservationFullResponseDTO cancelReservation(Long id, String cancellationReason);

    ReservationFullResponseDTO checkIn(Long id);

    ReservationFullResponseDTO checkOut(Long id);

    boolean hasRoomAvailability(Long roomId, LocalDateTime checkInDate, LocalDateTime checkOutDate);
}