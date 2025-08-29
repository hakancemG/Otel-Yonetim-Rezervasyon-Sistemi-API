package com.otelyonetim.rezervasyon.mapper;

import com.otelyonetim.rezervasyon.dto.CustomerDTO.CustomerLimitedResponseDTO;
import com.otelyonetim.rezervasyon.dto.PaymentDTO.PaymentLimitedResponseDTO;
import com.otelyonetim.rezervasyon.dto.ReservationDTO.*;
import com.otelyonetim.rezervasyon.entity.Customer;
import com.otelyonetim.rezervasyon.entity.Reservation;
import com.otelyonetim.rezervasyon.entity.Room;

import java.util.List;
import java.util.stream.Collectors;

public final class ReservationMapper {

    private ReservationMapper() {
        throw new UnsupportedOperationException("Utility class");
    }

    // CreateDTO -> Entity
    // Not: customerId/roomId service katmanında DB'den fetch edilip merge edilecektir.
    public static Reservation toEntity(ReservationCreateDTO dto) {
        if (dto == null) return null;

        return Reservation.builder()
                .checkInDate(dto.getCheckInDate())
                .checkOutDate(dto.getCheckOutDate())
                .adultCount(dto.getAdultCount())
                .childCount(dto.getChildCount())
                .totalPrice(dto.getTotalPrice())
                .note(dto.getNote())
                // placeholder olarak sadece id setleniyor; gerçek Customer/Room objeleri servis katmanında alınmalı
                .customer(Customer.builder().id(dto.getCustomerId()).build())
                .room(Room.builder().id(dto.getRoomId()).build())
                .build();
    }

    // Entity -> FullResponseDTO
    public static ReservationFullResponseDTO toFullDTO(Reservation reservation) {
        if (reservation == null) return null;

        // minimal müşteri bilgisi (null-safe)
        CustomerLimitedResponseDTO customerDTO =
                reservation.getCustomer() == null ? null : CustomerMapper.toLimitedDTO(reservation.getCustomer());

        // payment one-to-many, tüm ödemeleri liste olarak dönüştür
        List<PaymentLimitedResponseDTO> payments = reservation.getPayments() != null
                ? reservation.getPayments().stream()
                .map(PaymentMapper::toLimitedDTO)
                .collect(Collectors.toList())
                : List.of();

        return ReservationFullResponseDTO.builder()
                .id(reservation.getId())
                .customer(customerDTO)
                .roomId(reservation.getRoom() != null ? reservation.getRoom().getId() : null)
                .checkInDate(reservation.getCheckInDate())
                .checkOutDate(reservation.getCheckOutDate())
                .adultCount(reservation.getAdultCount())
                .childCount(reservation.getChildCount())
                .totalPrice(reservation.getTotalPrice())
                .confirmationNumber(reservation.getConfirmationNumber()) // EKSİK: confirmationNumber eklendi
                .status(reservation.getStatus())
                .cancellationReason(reservation.getCancellationReason())
                .cancelledAt(reservation.getCancelledAt())
                .checkedInAt(reservation.getCheckedInAt())
                .checkedOutAt(reservation.getCheckedOutAt())
                .note(reservation.getNote())
                .createdAt(reservation.getCreatedAt())
                .updatedAt(reservation.getUpdatedAt())
                .payments(payments)
                .build();
    }

    // Entity -> LimitedResponseDTO
    public static ReservationLimitedResponseDTO toLimitedDTO(Reservation reservation) {
        if (reservation == null) return null;

        List<Long> paymentIds = reservation.getPayments() != null
                ? reservation.getPayments().stream()
                .map(payment -> payment.getId())
                .collect(Collectors.toList())
                : List.of();

        return ReservationLimitedResponseDTO.builder()
                .customerId(reservation.getCustomer() != null ? reservation.getCustomer().getId() : null)
                .roomId(reservation.getRoom() != null ? reservation.getRoom().getId() : null)
                .checkInDate(reservation.getCheckInDate())
                .checkOutDate(reservation.getCheckOutDate())
                .totalPrice(reservation.getTotalPrice())
                .status(reservation.getStatus())
                .paymentIds(paymentIds)
                .build();
    }

    // UpdateDTO -> Entity (patch tarzı: null olmayan alanları uygular)
    public static void updateEntityFromDTO(ReservationUpdateDTO dto, Reservation reservation) {
        if (dto == null || reservation == null) return;

        if (dto.getCheckInDate() != null) reservation.setCheckInDate(dto.getCheckInDate());
        if (dto.getCheckOutDate() != null) reservation.setCheckOutDate(dto.getCheckOutDate());
        if (dto.getAdultCount() != null) reservation.setAdultCount(dto.getAdultCount());
        if (dto.getChildCount() != null) reservation.setChildCount(dto.getChildCount());
        if (dto.getTotalPrice() != null) reservation.setTotalPrice(dto.getTotalPrice());
        if (dto.getStatus() != null) reservation.setStatus(dto.getStatus());
        if (dto.getCancellationReason() != null) reservation.setCancellationReason(dto.getCancellationReason());
        if (dto.getCancelledAt() != null) reservation.setCancelledAt(dto.getCancelledAt());
        if (dto.getCheckedInAt() != null) reservation.setCheckedInAt(dto.getCheckedInAt());
        if (dto.getCheckedOutAt() != null) reservation.setCheckedOutAt(dto.getCheckedOutAt());
        if (dto.getNote() != null) reservation.setNote(dto.getNote());
    }

    // List<Entity> -> List<FullDTO>
    public static List<ReservationFullResponseDTO> toFullDTOList(List<Reservation> reservations) {
        if (reservations == null) return List.of();
        return reservations.stream()
                .map(ReservationMapper::toFullDTO)
                .collect(Collectors.toList());
    }

    // List<Entity> -> List<LimitedDTO>
    public static List<ReservationLimitedResponseDTO> toLimitedDTOList(List<Reservation> reservations) {
        if (reservations == null) return List.of();
        return reservations.stream()
                .map(ReservationMapper::toLimitedDTO)
                .collect(Collectors.toList());
    }
}