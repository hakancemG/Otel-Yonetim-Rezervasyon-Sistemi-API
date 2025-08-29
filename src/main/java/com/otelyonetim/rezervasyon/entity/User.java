package com.otelyonetim.rezervasyon.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users", indexes = {
        @Index(name = "idx_user_email", columnList = "email"),
        @Index(name = "idx_user_username", columnList = "username"),
        @Index(name = "idx_user_active", columnList = "isDeleted")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 3, max = 20)
    @Column(nullable = false, unique = true)
    private String username;

    @NotBlank
    @Size(min = 6, max = 100)
    @Column(nullable = false)
    private String password;

    @Email
    @NotBlank
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private boolean isActive = true;

    // Role ilişkisi
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"),
            foreignKey = @ForeignKey(name = "fk_user_roles_user"),
            inverseForeignKey = @ForeignKey(name = "fk_user_roles_role")
    )
    private Set<Role> roles = new HashSet<>();

    // Rezervasyon ilişkisi (createdBy / updatedBy)
    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY)
    private Set<Reservation> createdReservations = new HashSet<>();

    @OneToMany(mappedBy = "updatedBy", fetch = FetchType.LAZY)
    private Set<Reservation> updatedReservations = new HashSet<>();
}
