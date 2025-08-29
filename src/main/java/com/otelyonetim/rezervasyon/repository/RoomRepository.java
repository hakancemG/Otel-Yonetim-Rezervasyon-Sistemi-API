package com.otelyonetim.rezervasyon.repository;

import com.otelyonetim.rezervasyon.entity.Room;
import com.otelyonetim.rezervasyon.enums.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    // Oda numarasına göre bul (silinmemiş)
    @Query("SELECT r FROM Room r WHERE r.roomNumber = :roomNumber AND r.isDeleted = false")
    Optional<Room> findByRoomNumberAndNotDeleted(@Param("roomNumber") String roomNumber);

    // Tüm aktif odaları getir (silinmemiş)
    @Query("SELECT r FROM Room r WHERE r.isDeleted = false ORDER BY r.roomNumber")
    List<Room> findAllActive();

    // Oda tipine göre filtrele (silinmemiş)
    @Query("SELECT r FROM Room r WHERE r.roomType = :roomType AND r.isDeleted = false ORDER BY r.roomNumber")
    List<Room> findByRoomType(@Param("roomType") RoomType roomType);

    // Kapasiteye göre filtrele (silinmemiş)
    @Query("SELECT r FROM Room r WHERE r.capacity >= :minCapacity AND r.isDeleted = false ORDER BY r.capacity")
    List<Room> findByCapacityGreaterThanEqual(@Param("minCapacity") Integer minCapacity);

    // Fiyat aralığına göre filtrele (silinmemiş)
    @Query("SELECT r FROM Room r WHERE r.pricePerNight BETWEEN :minPrice AND :maxPrice AND r.isDeleted = false ORDER BY r.pricePerNight")
    List<Room> findByPricePerNightBetween(@Param("minPrice") BigDecimal minPrice,
                                          @Param("maxPrice") BigDecimal maxPrice);

    // Oda tipi ve kapasiteye göre filtrele (silinmemiş)
    @Query("SELECT r FROM Room r WHERE r.roomType = :roomType AND r.capacity >= :minCapacity AND r.isDeleted = false ORDER BY r.pricePerNight")
    List<Room> findByRoomTypeAndCapacity(@Param("roomType") RoomType roomType,
                                         @Param("minCapacity") Integer minCapacity);

    // ID ile oda bul (silinmemiş)
    @Query("SELECT r FROM Room r WHERE r.id = :id AND r.isDeleted = false")
    Optional<Room> findByIdAndNotDeleted(@Param("id") Long id);

    // Oda numarası varlık kontrolü (silinmemiş)
    @Query("SELECT COUNT(r) > 0 FROM Room r WHERE r.roomNumber = :roomNumber AND r.isDeleted = false")
    boolean existsByRoomNumberAndNotDeleted(@Param("roomNumber") String roomNumber);

    // Müsait odaları getir (belirli tarihlerde çakışma olmayanlar)
    @Query("SELECT r FROM Room r WHERE r.isDeleted = false AND r.id NOT IN " +
            "(SELECT res.room.id FROM Reservation res WHERE res.isDeleted = false " +
            "AND res.status NOT IN ('CANCELLED', 'NOSHOW') " +
            "AND ((res.checkInDate < :checkOutDate AND res.checkOutDate > :checkInDate))) " +
            "ORDER BY r.roomNumber")
    List<Room> findAvailableRooms(@Param("checkInDate") java.time.LocalDateTime checkInDate,
                                  @Param("checkOutDate") java.time.LocalDateTime checkOutDate);



    // Oda tipine göre müsait odalar
    @Query("SELECT r FROM Room r WHERE r.isDeleted = false AND r.roomType = :roomType AND r.id NOT IN " +
            "(SELECT res.room.id FROM Reservation res WHERE res.isDeleted = false " +
            "AND res.status NOT IN ('CANCELLED', 'NOSHOW') " +
            "AND ((res.checkInDate < :checkOutDate AND res.checkOutDate > :checkInDate))) " +
            "ORDER BY r.pricePerNight")
    List<Room> findAvailableRoomsByType(@Param("roomType") RoomType roomType,
                                        @Param("checkInDate") java.time.LocalDateTime checkInDate,
                                        @Param("checkOutDate") java.time.LocalDateTime checkOutDate);
}