package com.otelyonetim.rezervasyon.dto.CustomerDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerLimitedResponseDTO {
    private String firstName;
    private String lastName;
    private String email;
}
