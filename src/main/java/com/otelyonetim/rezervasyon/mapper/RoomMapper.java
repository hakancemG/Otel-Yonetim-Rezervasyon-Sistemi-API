package com.otelyonetim.rezervasyon.mapper;

import com.otelyonetim.rezervasyon.dto.RoomDTO.RoomCreateDTO;
import com.otelyonetim.rezervasyon.dto.RoomDTO.RoomFullResponseDTO;
import com.otelyonetim.rezervasyon.dto.RoomDTO.RoomLimitedResponseDTO;
import com.otelyonetim.rezervasyon.dto.RoomDTO.RoomUpdateDTO;
import com.otelyonetim.rezervasyon.entity.Reservation;
import com.otelyonetim.rezervasyon.entity.Room;
import com.otelyonetim.rezervasyon.enums.RoomType;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public final class RoomMapper {

    private RoomMapper() {
        throw new UnsupportedOperationException("Utility class");
    }

    // CreateDTO -> Entity
    public static Room toEntity(RoomCreateDTO dto) {
        if (dto == null) return null;
        return Room.builder()
                .roomNumber(trimOrNull(dto.getRoomNumber()))
                .roomType(parseRoomType(dto.getRoomType()))
                .capacity(dto.getCapacity())
                .pricePerNight(dto.getPricePerNight())
                .description(trimOrNull(dto.getDescription()))
                .build();
    }

    // Entity -> FullResponseDTO
    public static RoomFullResponseDTO toFullDTO(Room room) {
        if (room == null) return null;

        List<Long> reservationIds =
                room.getReservations() == null ? List.of()
                        : room.getReservations().stream()
                        .filter(Objects::nonNull)
                        .map(Reservation::getId)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());

        return RoomFullResponseDTO.builder()
                .id(room.getId())
                .roomNumber(room.getRoomNumber())
                .roomType(stringify(room.getRoomType()))
                .capacity(room.getCapacity())
                .pricePerNight(room.getPricePerNight())
                .description(room.getDescription())
                .createdAt(room.getCreatedAt())
                .updatedAt(room.getUpdatedAt())
                .deletedAt(room.getDeletedAt())
                .isDeleted(room.isDeleted())
                .reservationIds(reservationIds)
                .build();
    }

    // Entity -> LimitedResponseDTO
    public static RoomLimitedResponseDTO toLimitedDTO(Room room) {
        if (room == null) return null;
        return RoomLimitedResponseDTO.builder()
                .roomNumber(room.getRoomNumber())
                .roomType(stringify(room.getRoomType()))
                .capacity(room.getCapacity())
                .pricePerNight(room.getPricePerNight())
                .build();
    }

    // UpdateDTO -> Entity (partial update; null olmayan alanları uygular)
    public static void updateEntityFromDTO(RoomUpdateDTO dto, Room room) {
        if (dto == null || room == null) return;

        if (dto.getRoomNumber() != null) room.setRoomNumber(trimOrNull(dto.getRoomNumber()));
        if (dto.getRoomType() != null)   room.setRoomType(parseRoomType(dto.getRoomType()));
        if (dto.getCapacity() != null)   room.setCapacity(dto.getCapacity());
        if (dto.getPricePerNight() != null) room.setPricePerNight(dto.getPricePerNight());
        if (dto.getDescription() != null)   room.setDescription(trimOrNull(dto.getDescription()));
    }

    // Entity -> UpdateDTO (ters)
    public static RoomUpdateDTO toUpdateDTO(Room room) {
        if (room == null) return null;
        return RoomUpdateDTO.builder()
                .roomNumber(room.getRoomNumber())
                .roomType(stringify(room.getRoomType()))
                .capacity(room.getCapacity())
                .pricePerNight(room.getPricePerNight())
                .description(room.getDescription())
                .build();
    }

    // List<Entity> -> List<FullDTO>
    public static List<RoomFullResponseDTO> toFullDTOList(List<Room> rooms) {
        if (rooms == null) return List.of();
        return rooms.stream()
                .map(RoomMapper::toFullDTO)
                .toList();
    }

    // List<Entity> -> List<LimitedDTO>
    public static List<RoomLimitedResponseDTO> toLimitedDTOList(List<Room> rooms) {
        if (rooms == null) return List.of();
        return rooms.stream()
                .map(RoomMapper::toLimitedDTO)
                .toList();
    }

    // FullDTO -> LimitedDTO (isteğe bağlı yardımcı)
    public static RoomLimitedResponseDTO fullToLimited(RoomFullResponseDTO dto) {
        if (dto == null) return null;
        return RoomLimitedResponseDTO.builder()
                .roomNumber(dto.getRoomNumber())
                .roomType(dto.getRoomType())
                .capacity(dto.getCapacity())
                .pricePerNight(dto.getPricePerNight())
                .build();
    }

    // Optional<FullDTO> -> Optional<LimitedDTO> (isteğe bağlı yardımcı)
    public static Optional<RoomLimitedResponseDTO> fullToLimited(Optional<RoomFullResponseDTO> dto) {
        if (dto == null) return Optional.empty();
        return dto.map(RoomMapper::fullToLimited);
    }

    // ------- helpers -------

    private static RoomType parseRoomType(String value) {
        if (value == null || value.isBlank()) return null;
        try {
            return RoomType.valueOf(value.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            // Bu hatayı burada bastırmak yerine fırlatıyorum ki invalid tip erken yakalansın.
            throw new IllegalArgumentException("Invalid roomType: " + value);
        }
    }

    private static String stringify(RoomType type) {
        return type == null ? null : type.name();
    }

    private static String trimOrNull(String s) {
        return s == null ? null : s.trim();
    }
}
