package com.otelyonetim.rezervasyon.repository;

import com.otelyonetim.rezervasyon.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Username ile kullanıcı bul
    Optional<User> findByUsername(String username);

    // Email ile kullanıcı bul
    Optional<User> findByEmail(String email);

    // Aktif kullanıcıları getir
    @Query("SELECT u FROM User u WHERE u.isActive = true ORDER BY u.username")
    List<User> findAllActive();

    // Belirli role sahip kullanıcıları getir
    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = :roleName AND u.isActive = true ORDER BY u.username")
    List<User> findByRoleName(@Param("roleName") String roleName);

    // Username'e göre arama (case-insensitive)
    @Query("SELECT u FROM User u WHERE LOWER(u.username) LIKE LOWER(CONCAT('%', :username, '%')) AND u.isActive = true ORDER BY u.username")
    List<User> findByUsernameContainingIgnoreCase(@Param("username") String username);

    // Email'e göre arama (case-insensitive)
    @Query("SELECT u FROM User u WHERE LOWER(u.email) LIKE LOWER(CONCAT('%', :email, '%')) AND u.isActive = true ORDER BY u.email")
    List<User> findByEmailContainingIgnoreCase(@Param("email") String email);

    // Username varlık kontrolü
    boolean existsByUsername(String username);

    // Email varlık kontrolü
    boolean existsByEmail(String email);

    // Username ve aktiflik durumuna göre bul
    @Query("SELECT u FROM User u WHERE u.username = :username AND u.isActive = true")
    Optional<User> findByUsernameAndActive(@Param("username") String username);

    // Email ve aktiflik durumuna göre bul
    @Query("SELECT u FROM User u WHERE u.email = :email AND u.isActive = true")
    Optional<User> findByEmailAndActive(@Param("email") String email);
}