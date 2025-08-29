package com.otelyonetim.rezervasyon.dto.UserDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateDTO {
    @Size(min = 3, max = 20)
    private String username;

    @Size(min = 6, max = 100)
    private String password;

    @Email
    private String email;

    private Boolean isActive;

    private Set<Long> roleIds;
}
