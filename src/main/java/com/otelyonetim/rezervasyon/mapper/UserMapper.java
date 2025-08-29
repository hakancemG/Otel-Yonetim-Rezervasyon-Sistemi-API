package com.otelyonetim.rezervasyon.mapper;

import com.otelyonetim.rezervasyon.dto.UserDTO.*;
import com.otelyonetim.rezervasyon.entity.Role;
import com.otelyonetim.rezervasyon.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public final class UserMapper {

    private UserMapper() {
        throw new UnsupportedOperationException("Utility class");
    }

    // CreateDTO -> Entity
    public static User toEntity(UserCreateDTO dto) {
        if (dto == null) return null;

        return User.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .email(dto.getEmail())
                .roles(mapRoleIdsToRoles(dto.getRoleIds()))
                .isActive(true) // yeni kullanıcı varsayılan aktif
                .build();
    }

    // UpdateDTO -> mevcut Entity güncellemesi
    public static void updateEntityFromDTO(UserUpdateDTO dto, User existingUser) {
        if (dto == null || existingUser == null) return;

        if (dto.getUsername() != null) existingUser.setUsername(dto.getUsername());
        if (dto.getPassword() != null) existingUser.setPassword(dto.getPassword());
        if (dto.getEmail() != null) existingUser.setEmail(dto.getEmail());
        if (dto.getIsActive() != null) existingUser.setActive(dto.getIsActive());
        if (dto.getRoleIds() != null) existingUser.setRoles(mapRoleIdsToRoles(dto.getRoleIds()));
    }

    // Entity -> FullResponseDTO
    public static UserFullResponseDTO toFullDTO(User user) {
        if (user == null) return null;

        return UserFullResponseDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .isActive(user.isActive())
                .roles(extractRoleNames(user.getRoles()))
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .deletedAt(user.getDeletedAt())
                .build();
    }

    // Entity -> LimitedResponseDTO
    public static UserLimitedResponseDTO toLimitedDTO(User user) {
        if (user == null) return null;

        return UserLimitedResponseDTO.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .isActive(user.isActive())
                .build();
    }

    // List<Entity> -> List<FullDTO>
    public static List<UserFullResponseDTO> toFullDTOList(List<User> users) {
        if (users == null) return List.of();
        return users.stream()
                .map(UserMapper::toFullDTO)
                .collect(Collectors.toList());
    }

    // List<Entity> -> List<LimitedDTO>
    public static List<UserLimitedResponseDTO> toLimitedDTOList(List<User> users) {
        if (users == null) return List.of();
        return users.stream()
                .map(UserMapper::toLimitedDTO)
                .collect(Collectors.toList());
    }

    // Optional<Entity> -> Optional<FullDTO>
    public static Optional<UserFullResponseDTO> toFullDTO(Optional<User> user) {
        if (user == null) return Optional.empty();
        return user.map(UserMapper::toFullDTO);
    }

    // Optional<Entity> -> Optional<LimitedDTO>
    public static Optional<UserLimitedResponseDTO> toLimitedDTO(Optional<User> user) {
        if (user == null) return Optional.empty();
        return user.map(UserMapper::toLimitedDTO);
    }

    // UpdateDTO -> mevcut Entity üzerinde güncelleme *
    public static void updateEntity(User user, UserUpdateDTO dto, Set<Role> roles) {
        if (user == null || dto == null) return;
        if (dto.getPassword() != null) {
            user.setPassword(dto.getPassword()); // hash Service katmanında
        }
        if (dto.getUsername() != null && roles != null) {
            user.setRoles(roles);
        }
    }

    // Yardımcı metot: Role Id'leri -> Role Entity seti
    private static Set<Role> mapRoleIdsToRoles(Set<Long> roleIds) {
        if (roleIds == null) return Set.of();
        return roleIds.stream()
                .map(id -> Role.builder().id(id).build())
                .collect(Collectors.toSet());
    }

    // Yardımcı metot: Role Entity seti -> Role isimleri
    private static Set<String> extractRoleNames(Set<Role> roles) {
        if (roles == null) return Set.of();
        return roles.stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
    }
}
