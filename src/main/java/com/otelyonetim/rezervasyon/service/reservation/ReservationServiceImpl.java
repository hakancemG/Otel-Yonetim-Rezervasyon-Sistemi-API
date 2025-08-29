package com.otelyonetim.rezervasyon.service.reservation;

import com.otelyonetim.rezervasyon.dto.ReservationDTO.ReservationCreateDTO;
import com.otelyonetim.rezervasyon.dto.ReservationDTO.ReservationFullResponseDTO;
import com.otelyonetim.rezervasyon.dto.ReservationDTO.ReservationLimitedResponseDTO;
import com.otelyonetim.rezervasyon.dto.ReservationDTO.ReservationUpdateDTO;
import com.otelyonetim.rezervasyon.entity.Customer;
import com.otelyonetim.rezervasyon.entity.Reservation;
import com.otelyonetim.rezervasyon.entity.Room;
import com.otelyonetim.rezervasyon.enums.ReservationStatus;
import com.otelyonetim.rezervasyon.exception.ResourceNotFoundException;
import com.otelyonetim.rezervasyon.mapper.ReservationMapper;
import com.otelyonetim.rezervasyon.repository.CustomerRepository;
import com.otelyonetim.rezervasyon.repository.ReservationRepository;
import com.otelyonetim.rezervasyon.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final CustomerRepository customerRepository;
    private final RoomRepository roomRepository;

    @Override
    @Transactional
    public ReservationFullResponseDTO createReservation(ReservationCreateDTO reservationCreateDTO) {
        // Müşteri ve oda varlık kontrolleri
        Customer customer = customerRepository.findByIdAndNotDeleted(reservationCreateDTO.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Müşteri bulunamadı. ID: " + reservationCreateDTO.getCustomerId()));

        Room room = roomRepository.findByIdAndNotDeleted(reservationCreateDTO.getRoomId())
                .orElseThrow(() -> new ResourceNotFoundException("Oda bulunamadı. ID: " + reservationCreateDTO.getRoomId()));

        // Oda müsaitlik kontrolü
        if (!hasRoomAvailability(room.getId(), reservationCreateDTO.getCheckInDate(), reservationCreateDTO.getCheckOutDate())) {
            throw new IllegalArgumentException("Bu tarihler arasında oda müsait değil");
        }

        // Kapasite kontrolü
        if (reservationCreateDTO.getAdultCount() > room.getCapacity()) {
            throw new IllegalArgumentException("Oda kapasitesi yetersiz. Oda kapasitesi: " + room.getCapacity());
        }

        Reservation reservation = ReservationMapper.toEntity(reservationCreateDTO);
        reservation.setCustomer(customer);
        reservation.setRoom(room);
        reservation.setStatus(ReservationStatus.CONFIRMED);

        Reservation savedReservation = reservationRepository.save(reservation);
        return ReservationMapper.toFullDTO(savedReservation);
    }

    @Override
    @Transactional(readOnly = true)
    public ReservationFullResponseDTO getReservationById(Long id) {
        Reservation reservation = reservationRepository.findByIdAndNotDeleted(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rezervasyon bulunamadı. ID: " + id));
        return ReservationMapper.toFullDTO(reservation);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationFullResponseDTO> getAllReservations() {
        List<Reservation> reservations = reservationRepository.findAllActive();
        return ReservationMapper.toFullDTOList(reservations);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationLimitedResponseDTO> getAllReservationsLimited() {
        List<Reservation> reservations = reservationRepository.findAllActive();
        return ReservationMapper.toLimitedDTOList(reservations);
    }

    @Override
    @Transactional
    public ReservationFullResponseDTO updateReservation(Long id, ReservationUpdateDTO reservationUpdateDTO) {
        Reservation existingReservation = reservationRepository.findByIdAndNotDeleted(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rezervasyon bulunamadı. ID: " + id));

        // Tarih değişikliği varsa müsaitlik kontrolü
        if ((reservationUpdateDTO.getCheckInDate() != null || reservationUpdateDTO.getCheckOutDate() != null) &&
                !hasRoomAvailability(existingReservation.getRoom().getId(),
                        reservationUpdateDTO.getCheckInDate() != null ? reservationUpdateDTO.getCheckInDate() : existingReservation.getCheckInDate(),
                        reservationUpdateDTO.getCheckOutDate() != null ? reservationUpdateDTO.getCheckOutDate() : existingReservation.getCheckOutDate(),
                        existingReservation.getId())) {
            throw new IllegalArgumentException("Bu tarihler arasında oda müsait değil");
        }

        ReservationMapper.updateEntityFromDTO(reservationUpdateDTO, existingReservation);
        Reservation updatedReservation = reservationRepository.save(existingReservation);
        return ReservationMapper.toFullDTO(updatedReservation);
    }

    @Override
    @Transactional
    public void deleteReservation(Long id) {
        Reservation reservation = reservationRepository.findByIdAndNotDeleted(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rezervasyon bulunamadı. ID: " + id));
        reservationRepository.delete(reservation);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationFullResponseDTO> getReservationsByCustomerId(Long customerId) {
        List<Reservation> reservations = reservationRepository.findByCustomerId(customerId);
        return ReservationMapper.toFullDTOList(reservations);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationFullResponseDTO> getReservationsByRoomId(Long roomId) {
        List<Reservation> reservations = reservationRepository.findByRoomId(roomId);
        return ReservationMapper.toFullDTOList(reservations);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationFullResponseDTO> getReservationsByStatus(ReservationStatus status) {
        List<Reservation> reservations = reservationRepository.findByStatus(status);
        return ReservationMapper.toFullDTOList(reservations);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationFullResponseDTO> getReservationsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        List<Reservation> reservations = reservationRepository.findByCheckInDateBetween(startDate, endDate);
        return ReservationMapper.toFullDTOList(reservations);
    }

    @Override
    @Transactional
    public ReservationFullResponseDTO cancelReservation(Long id, String cancellationReason) {
        Reservation reservation = reservationRepository.findByIdAndNotDeleted(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rezervasyon bulunamadı. ID: " + id));

        reservation.setStatus(ReservationStatus.CANCELLED);
        reservation.setCancellationReason(cancellationReason);
        reservation.setCancelledAt(LocalDateTime.now());

        Reservation cancelledReservation = reservationRepository.save(reservation);
        return ReservationMapper.toFullDTO(cancelledReservation);
    }

    @Override
    @Transactional
    public ReservationFullResponseDTO checkIn(Long id) {
        Reservation reservation = reservationRepository.findByIdAndNotDeleted(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rezervasyon bulunamadı. ID: " + id));

        if (reservation.getStatus() != ReservationStatus.CONFIRMED) {
            throw new IllegalStateException("Sadece CONFIRMED durumundaki rezervasyonlar check-in yapabilir");
        }

        reservation.setCheckedInAt(LocalDateTime.now());
        reservation.setStatus(ReservationStatus.CHECKED_IN);

        Reservation checkedInReservation = reservationRepository.save(reservation);
        return ReservationMapper.toFullDTO(checkedInReservation);
    }

    @Override
    @Transactional
    public ReservationFullResponseDTO checkOut(Long id) {
        Reservation reservation = reservationRepository.findByIdAndNotDeleted(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rezervasyon bulunamadı. ID: " + id));

        if (reservation.getStatus() != ReservationStatus.CHECKED_IN) {
            throw new IllegalStateException("Sadece CHECKED_IN durumundaki rezervasyonlar check-out yapabilir");
        }

        reservation.setCheckedOutAt(LocalDateTime.now());
        reservation.setStatus(ReservationStatus.CONFIRMED);

        Reservation checkedOutReservation = reservationRepository.save(reservation);
        return ReservationMapper.toFullDTO(checkedOutReservation);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasRoomAvailability(Long roomId, LocalDateTime checkInDate, LocalDateTime checkOutDate) {
        List<Reservation> conflicts = reservationRepository.findConflictingReservations(roomId, checkInDate, checkOutDate);
        return conflicts.isEmpty();
    }

    // Overload for update with exclusion of current reservation
    private boolean hasRoomAvailability(Long roomId, LocalDateTime checkInDate, LocalDateTime checkOutDate, Long excludeReservationId) {
        List<Reservation> conflicts = reservationRepository.findConflictingReservations(roomId, checkInDate, checkOutDate);
        return conflicts.stream().noneMatch(r -> !r.getId().equals(excludeReservationId));
    }
}