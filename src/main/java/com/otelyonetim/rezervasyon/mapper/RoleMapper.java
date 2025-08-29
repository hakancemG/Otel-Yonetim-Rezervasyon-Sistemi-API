package com.otelyonetim.rezervasyon.mapper;

import com.otelyonetim.rezervasyon.dto.RoleDTO.*;
import com.otelyonetim.rezervasyon.entity.Role;
import com.otelyonetim.rezervasyon.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public final class RoleMapper {

    private RoleMapper() {
        throw new UnsupportedOperationException("Utility class");
    }

    // CreateDTO -> Entity
    public static Role toEntity(RoleCreateDTO dto) {
        if (dto == null) return null;
        return Role.builder()
                .name(dto.getName())
                .build();
    }

    // UpdateDTO -> Entity (sadece name değişiyor)
    public static void updateEntityFromDTO(RoleUpdateDTO dto, Role existingRole) {
        if (dto == null || existingRole == null) return;
        if (dto.getName() != null) {
            existingRole.setName(dto.getName());
        }
    }

    // Entity -> FullResponseDTO
    public static RoleFullResponseDTO toFullDTO(Role role) {
        if (role == null) return null;
        return RoleFullResponseDTO.builder()
                .id(role.getId())
                .name(role.getName())
                .userNames(extractUserNames(role.getUsers()))
                .build();
    }

    // Entity -> LimitedResponseDTO
    public static RoleLimitedResponseDTO toLimitedDTO(Role role) {
        if (role == null) return null;
        return RoleLimitedResponseDTO.builder()
                .name(role.getName())
                .build();
    }

    // List<Entity> -> List<FullDTO>
    public static List<RoleFullResponseDTO> toFullDTOList(List<Role> roles) {
        if (roles == null) return List.of();
        return roles.stream()
                .map(RoleMapper::toFullDTO)
                .collect(Collectors.toList());
    }

    // List<Entity> -> List<LimitedDTO>
    public static List<RoleLimitedResponseDTO> toLimitedDTOList(List<Role> roles) {
        if (roles == null) return List.of();
        return roles.stream()
                .map(RoleMapper::toLimitedDTO)
                .collect(Collectors.toList());
    }

    // Optional<Entity> -> Optional<FullDTO>
    public static Optional<RoleFullResponseDTO> toFullDTO(Optional<Role> role) {
        if (role == null) return Optional.empty();
        return role.map(RoleMapper::toFullDTO);
    }

    // Optional<Entity> -> Optional<LimitedDTO>
    public static Optional<RoleLimitedResponseDTO> toLimitedDTO(Optional<Role> role) {
        if (role == null) return Optional.empty();
        return role.map(RoleMapper::toLimitedDTO);
    }

    // UpdateDTO -> mevcut Entity üzerinde güncelleme
    public static void updateEntity(Role role, RoleUpdateDTO dto) {
        if (role == null || dto == null) return;
        if (dto.getName() != null) {
            role.setName(dto.getName());
        }
    }

    // Yardımcı metot: User setinden username'leri çıkartır
    private static Set<String> extractUserNames(Set<User> users) {
        if (users == null) return Set.of();
        return users.stream()
                .map(User::getUsername)
                .collect(Collectors.toSet());
    }

}
