package com.otelyonetim.rezervasyon.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="customers", indexes = {
        @Index(name = "idx_customer_mail", columnList = "email"),
        @Index(name = "idx_customer_tckn", columnList = "tckn"),
        @Index(name = "idx_customer_phone", columnList = "phone"),
        @Index(name = "idx_customer_active", columnList = "is_deleted")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE customers SET is_deleted = true, deleted_at = NOW() WHERE id = ?")
@Where(clause = "is_deleted = false")
public class Customer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "İsim alanı boş bırakılamaz!")
    @Size(min=3, max=20, message = "İsim, 3-20 karakter arasında olmalıdır!")
    private String firstName;

    @NotBlank(message = "Soyisim alanı boş bırakılamaz!")
    @Size(min=2, max=20, message = "Soyisim, 2-20 karakter arasında olmalıdır!")
    private String lastName;

    @Email(message = "Geçerli bir email giriniz!")
    @NotBlank(message = "E-mail alanı boş bırakılamaz!")
    @Column(unique = true, nullable = false)
    private String email;

    @Pattern(regexp = "^[0-9]{10,11}$", message = "Geçerli bir telefon numarası giriniz!")
    @Column(unique = true)
    private String phone;

    @Pattern(regexp = "^[0-9]{11}$", message = "TCKN 11 haneli olmalıdır!")
    @Column(unique = true, nullable = false)
    private String tckn;

    // RELATION İÇİN.
    @OneToMany(
            mappedBy = "customer",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE}
    )
    private List<Reservation>reservations =new ArrayList<>();
}
