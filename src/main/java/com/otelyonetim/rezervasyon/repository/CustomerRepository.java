package com.otelyonetim.rezervasyon.repository;

import com.otelyonetim.rezervasyon.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // Email ile müşteri bulma (soft delete dahil)
    Optional<Customer> findByEmail(String email);

    // TCKN ile müşteri bulma (soft delete dahil)
    Optional<Customer> findByTckn(String tckn);

    // Phone ile müşteri bulma (soft delete dahil)
    Optional<Customer> findByPhone(String phone);

    // Email ile müşteri bulma (sadece silinmemişler)
    @Query("SELECT c FROM Customer c WHERE c.email = :email AND c.isDeleted = false")
    Optional<Customer> findByEmailAndNotDeleted(@Param("email") String email);

    // TCKN ile müşteri bulma (sadece silinmemişler)
    @Query("SELECT c FROM Customer c WHERE c.tckn = :tckn AND c.isDeleted = false")
    Optional<Customer> findByTcknAndNotDeleted(@Param("tckn") String tckn);

    // Tüm silinmemiş müşterileri getir
    @Query("SELECT c FROM Customer c WHERE c.isDeleted = false ORDER BY c.firstName, c.lastName")
    List<Customer> findAllActive();

    // İsim ve soyisime göre arama (silinmemişler)
    @Query("SELECT c FROM Customer c WHERE LOWER(c.firstName) LIKE LOWER(CONCAT('%', :name, '%')) AND c.isDeleted = false")
    List<Customer> findByFirstNameContainingIgnoreCase(@Param("name") String name);

    // Soyisime göre arama (silinmemişler)
    @Query("SELECT c FROM Customer c WHERE LOWER(c.lastName) LIKE LOWER(CONCAT('%', :surname, '%')) AND c.isDeleted = false")
    List<Customer> findByLastNameContainingIgnoreCase(@Param("surname") String surname);

    // ID ile müşteri bulma (sadece silinmemişler)
    @Query("SELECT c FROM Customer c WHERE c.id = :id AND c.isDeleted = false")
    Optional<Customer> findByIdAndNotDeleted(@Param("id") Long id);

    // Email'in varlığını kontrol et (silinmemişler)
    @Query("SELECT COUNT(c) > 0 FROM Customer c WHERE c.email = :email AND c.isDeleted = false")
    boolean existsByEmailAndNotDeleted(@Param("email") String email);

    // TCKN'nin varlığını kontrol et (silinmemişler)
    @Query("SELECT COUNT(c) > 0 FROM Customer c WHERE c.tckn = :tckn AND c.isDeleted = false")
    boolean existsByTcknAndNotDeleted(@Param("tckn") String tckn);
}