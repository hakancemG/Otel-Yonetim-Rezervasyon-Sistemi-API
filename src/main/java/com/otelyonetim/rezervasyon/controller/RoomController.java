package com.otelyonetim.rezervasyon.controller;

import com.otelyonetim.rezervasyon.dto.RoomDTO.RoomCreateDTO;
import com.otelyonetim.rezervasyon.dto.RoomDTO.RoomFullResponseDTO;
import com.otelyonetim.rezervasyon.dto.RoomDTO.RoomLimitedResponseDTO;
import com.otelyonetim.rezervasyon.dto.RoomDTO.RoomUpdateDTO;
import com.otelyonetim.rezervasyon.enums.RoomType;
import com.otelyonetim.rezervasyon.service.room.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PostMapping
    public ResponseEntity<RoomFullResponseDTO> createRoom(
            @Valid @RequestBody RoomCreateDTO roomCreateDTO) {
        RoomFullResponseDTO createdRoom = roomService.createRoom(roomCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRoom);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomFullResponseDTO> getRoomById(@PathVariable Long id) {
        RoomFullResponseDTO room = roomService.getRoomById(id);
        return ResponseEntity.ok(room);
    }

    @GetMapping
    public ResponseEntity<List<RoomFullResponseDTO>> getAllRooms() {
        List<RoomFullResponseDTO> rooms = roomService.getAllRooms();
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/limited")
    public ResponseEntity<List<RoomLimitedResponseDTO>> getAllRoomsLimited() {
        List<RoomLimitedResponseDTO> rooms = roomService.getAllRoomsLimited();
        return ResponseEntity.ok(rooms);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomFullResponseDTO> updateRoom(
            @PathVariable Long id,
            @Valid @RequestBody RoomUpdateDTO roomUpdateDTO) {
        RoomFullResponseDTO updatedRoom = roomService.updateRoom(id, roomUpdateDTO);
        return ResponseEntity.ok(updatedRoom);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/number/{roomNumber}")
    public ResponseEntity<RoomFullResponseDTO> getRoomByNumber(@PathVariable String roomNumber) {
        RoomFullResponseDTO room = roomService.getRoomByNumber(roomNumber);
        return ResponseEntity.ok(room);
    }

    @GetMapping("/type/{roomType}")
    public ResponseEntity<List<RoomFullResponseDTO>> getRoomsByType(@PathVariable RoomType roomType) {
        List<RoomFullResponseDTO> rooms = roomService.getRoomsByType(roomType);
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/capacity/{minCapacity}")
    public ResponseEntity<List<RoomFullResponseDTO>> getRoomsByCapacity(@PathVariable Integer minCapacity) {
        List<RoomFullResponseDTO> rooms = roomService.getRoomsByCapacity(minCapacity);
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/price-range")
    public ResponseEntity<List<RoomFullResponseDTO>> getRoomsByPriceRange(
            @RequestParam BigDecimal minPrice,
            @RequestParam BigDecimal maxPrice) {
        List<RoomFullResponseDTO> rooms = roomService.getRoomsByPriceRange(minPrice, maxPrice);
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<RoomFullResponseDTO>> getRoomsByTypeAndCapacity(
            @RequestParam RoomType roomType,
            @RequestParam Integer minCapacity) {
        List<RoomFullResponseDTO> rooms = roomService.getRoomsByTypeAndCapacity(roomType, minCapacity);
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/available")
    public ResponseEntity<List<RoomFullResponseDTO>> getAvailableRooms(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime checkInDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime checkOutDate) {
        List<RoomFullResponseDTO> rooms = roomService.getAvailableRooms(checkInDate, checkOutDate);
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/available/{roomType}")
    public ResponseEntity<List<RoomFullResponseDTO>> getAvailableRoomsByType(
            @PathVariable RoomType roomType,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime checkInDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime checkOutDate) {
        List<RoomFullResponseDTO> rooms = roomService.getAvailableRoomsByType(roomType, checkInDate, checkOutDate);
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/exists/{roomNumber}")
    public ResponseEntity<Boolean> existsByRoomNumber(@PathVariable String roomNumber) {
        boolean exists = roomService.existsByRoomNumber(roomNumber);
        return ResponseEntity.ok(exists);
    }
}