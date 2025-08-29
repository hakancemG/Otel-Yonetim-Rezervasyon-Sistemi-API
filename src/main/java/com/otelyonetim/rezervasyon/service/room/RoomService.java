package com.otelyonetim.rezervasyon.service.room;

import com.otelyonetim.rezervasyon.dto.RoomDTO.RoomCreateDTO;
import com.otelyonetim.rezervasyon.dto.RoomDTO.RoomFullResponseDTO;
import com.otelyonetim.rezervasyon.dto.RoomDTO.RoomLimitedResponseDTO;
import com.otelyonetim.rezervasyon.dto.RoomDTO.RoomUpdateDTO;
import com.otelyonetim.rezervasyon.enums.RoomType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface RoomService {

    RoomFullResponseDTO createRoom(RoomCreateDTO roomCreateDTO);

    RoomFullResponseDTO getRoomById(Long id);

    List<RoomFullResponseDTO> getAllRooms();

    List<RoomLimitedResponseDTO> getAllRoomsLimited();

    RoomFullResponseDTO updateRoom(Long id, RoomUpdateDTO roomUpdateDTO);

    void deleteRoom(Long id);

    RoomFullResponseDTO getRoomByNumber(String roomNumber);

    List<RoomFullResponseDTO> getRoomsByType(RoomType roomType);

    List<RoomFullResponseDTO> getRoomsByCapacity(Integer minCapacity);

    List<RoomFullResponseDTO> getRoomsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice);

    List<RoomFullResponseDTO> getRoomsByTypeAndCapacity(RoomType roomType, Integer minCapacity);

    List<RoomFullResponseDTO> getAvailableRooms(LocalDateTime checkInDate, LocalDateTime checkOutDate);

    List<RoomFullResponseDTO> getAvailableRoomsByType(RoomType roomType, LocalDateTime checkInDate, LocalDateTime checkOutDate);

    boolean existsByRoomNumber(String roomNumber);
}