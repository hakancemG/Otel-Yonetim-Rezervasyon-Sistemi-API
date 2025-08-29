package com.otelyonetim.rezervasyon.service.room;

import com.otelyonetim.rezervasyon.dto.RoomDTO.RoomCreateDTO;
import com.otelyonetim.rezervasyon.dto.RoomDTO.RoomFullResponseDTO;
import com.otelyonetim.rezervasyon.dto.RoomDTO.RoomLimitedResponseDTO;
import com.otelyonetim.rezervasyon.dto.RoomDTO.RoomUpdateDTO;
import com.otelyonetim.rezervasyon.entity.Room;
import com.otelyonetim.rezervasyon.enums.RoomType;
import com.otelyonetim.rezervasyon.exception.ResourceNotFoundException;
import com.otelyonetim.rezervasyon.mapper.RoomMapper;
import com.otelyonetim.rezervasyon.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    @Override
    @Transactional
    public RoomFullResponseDTO createRoom(RoomCreateDTO roomCreateDTO) {
        // Oda numarası unique kontrolü
        if (existsByRoomNumber(roomCreateDTO.getRoomNumber())) {
            throw new IllegalArgumentException("Bu oda numarası zaten kayıtlı: " + roomCreateDTO.getRoomNumber());
        }

        Room room = RoomMapper.toEntity(roomCreateDTO);
        Room savedRoom = roomRepository.save(room);
        return RoomMapper.toFullDTO(savedRoom);
    }

    @Override
    @Transactional(readOnly = true)
    public RoomFullResponseDTO getRoomById(Long id) {
        Room room = roomRepository.findByIdAndNotDeleted(id)
                .orElseThrow(() -> new ResourceNotFoundException("Oda bulunamadı. ID: " + id));
        return RoomMapper.toFullDTO(room);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoomFullResponseDTO> getAllRooms() {
        List<Room> rooms = roomRepository.findAllActive();
        return RoomMapper.toFullDTOList(rooms);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoomLimitedResponseDTO> getAllRoomsLimited() {
        List<Room> rooms = roomRepository.findAllActive();
        return RoomMapper.toLimitedDTOList(rooms);
    }

    @Override
    @Transactional
    public RoomFullResponseDTO updateRoom(Long id, RoomUpdateDTO roomUpdateDTO) {
        Room existingRoom = roomRepository.findByIdAndNotDeleted(id)
                .orElseThrow(() -> new ResourceNotFoundException("Oda bulunamadı. ID: " + id));

        // Oda numarası değişikliği kontrolü
        if (roomUpdateDTO.getRoomNumber() != null &&
                !existingRoom.getRoomNumber().equals(roomUpdateDTO.getRoomNumber()) &&
                existsByRoomNumber(roomUpdateDTO.getRoomNumber())) {
            throw new IllegalArgumentException("Bu oda numarası zaten kayıtlı: " + roomUpdateDTO.getRoomNumber());
        }

        RoomMapper.updateEntityFromDTO(roomUpdateDTO, existingRoom);
        Room updatedRoom = roomRepository.save(existingRoom);
        return RoomMapper.toFullDTO(updatedRoom);
    }

    @Override
    @Transactional
    public void deleteRoom(Long id) {
        Room room = roomRepository.findByIdAndNotDeleted(id)
                .orElseThrow(() -> new ResourceNotFoundException("Oda bulunamadı. ID: " + id));
        roomRepository.delete(room);
    }

    @Override
    @Transactional(readOnly = true)
    public RoomFullResponseDTO getRoomByNumber(String roomNumber) {
        Room room = roomRepository.findByRoomNumberAndNotDeleted(roomNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Oda bulunamadı. Oda No: " + roomNumber));
        return RoomMapper.toFullDTO(room);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoomFullResponseDTO> getRoomsByType(RoomType roomType) {
        List<Room> rooms = roomRepository.findByRoomType(roomType);
        return RoomMapper.toFullDTOList(rooms);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoomFullResponseDTO> getRoomsByCapacity(Integer minCapacity) {
        List<Room> rooms = roomRepository.findByCapacityGreaterThanEqual(minCapacity);
        return RoomMapper.toFullDTOList(rooms);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoomFullResponseDTO> getRoomsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        List<Room> rooms = roomRepository.findByPricePerNightBetween(minPrice, maxPrice);
        return RoomMapper.toFullDTOList(rooms);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoomFullResponseDTO> getRoomsByTypeAndCapacity(RoomType roomType, Integer minCapacity) {
        List<Room> rooms = roomRepository.findByRoomTypeAndCapacity(roomType, minCapacity);
        return RoomMapper.toFullDTOList(rooms);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoomFullResponseDTO> getAvailableRooms(LocalDateTime checkInDate, LocalDateTime checkOutDate) {
        List<Room> rooms = roomRepository.findAvailableRooms(checkInDate, checkOutDate);
        return RoomMapper.toFullDTOList(rooms);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoomFullResponseDTO> getAvailableRoomsByType(RoomType roomType, LocalDateTime checkInDate, LocalDateTime checkOutDate) {
        List<Room> rooms = roomRepository.findAvailableRoomsByType(roomType, checkInDate, checkOutDate);
        return RoomMapper.toFullDTOList(rooms);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByRoomNumber(String roomNumber) {
        return roomRepository.existsByRoomNumberAndNotDeleted(roomNumber);
    }
}