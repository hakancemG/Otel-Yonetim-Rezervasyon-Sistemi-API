package com.otelyonetim.rezervasyon.dto.RoleDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleFullResponseDTO {
    private Long id;
    private String name;
    private Set<String> userNames; // Role sahip kullanıcılar.
}
