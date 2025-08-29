package com.otelyonetim.rezervasyon.dto.CustomerDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerCreateDTO {

    @NotBlank(message = "İsim alanı boş bırakılamaz!")
    @Size(min = 3, max = 20, message = "İsim, 3-20 karakter arasında olmalıdır!")
    private String firstName;

    @NotBlank(message = "Soyisim alanı boş bırakılamaz!")
    @Size(min = 2, max = 20, message = "Soyisim, 2-20 karakter arasında olmalıdır!")
    private String lastName;

    @NotBlank(message = "E-mail alanı boş bırakılamaz!")
    @Email(message = "Geçerli bir email giriniz!")
    private String email;

    @NotBlank(message = "Geçerli bir telefon numarası giriniz!")
    @Pattern(regexp = "^[0-9]{10,11}$", message = "Geçerli bir telefon numarası giriniz!")
    private String phone;

    @NotBlank(message = "TCKN boş bırakılamaz!")
    @Pattern(regexp = "^[0-9]{11}$", message = "TCKN 11 haneli olmalıdır!")
    private String tckn;
}
