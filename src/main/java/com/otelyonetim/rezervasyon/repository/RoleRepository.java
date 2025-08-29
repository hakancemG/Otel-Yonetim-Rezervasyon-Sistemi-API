package com.otelyonetim.rezervasyon.repository;

import com.otelyonetim.rezervasyon.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    // Role ismine göre bul
    Optional<Role> findByName(String name);

    // Role ismine göre varlık kontrolü
    boolean existsByName(String name);

    // Belirli kullanıcı sayısına sahip rolleri getir
    @Query("SELECT r FROM Role r WHERE SIZE(r.users) >= :minUserCount ORDER BY r.name")
    List<Role> findByMinUserCount(@Param("minUserCount") int minUserCount);

    // Kullanıcı sayısına göre rolleri sırala
    @Query("SELECT r FROM Role r ORDER BY SIZE(r.users) DESC")
    List<Role> findAllOrderByUserCountDesc();

    // İsme göre arama (case-insensitive)
    @Query("SELECT r FROM Role r WHERE LOWER(r.name) LIKE LOWER(CONCAT('%', :name, '%')) ORDER BY r.name")
    List<Role> findByNameContainingIgnoreCase(@Param("name") String name);
}