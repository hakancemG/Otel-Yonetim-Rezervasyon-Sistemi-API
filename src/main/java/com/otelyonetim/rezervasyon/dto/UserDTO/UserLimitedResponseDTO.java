package com.otelyonetim.rezervasyon.dto.UserDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLimitedResponseDTO {
    private String username;
    private String email;
    private Boolean isActive;
}
