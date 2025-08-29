package com.otelyonetim.rezervasyon.dto.UserDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserFullResponseDTO {
    private Long id;
    private String username;
    private String email;
    private Boolean isActive;
    private Set<String> roles; // Role isimleri
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
