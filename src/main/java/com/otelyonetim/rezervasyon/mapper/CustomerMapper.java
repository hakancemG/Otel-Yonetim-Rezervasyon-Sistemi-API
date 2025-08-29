package com.otelyonetim.rezervasyon.mapper;

import com.otelyonetim.rezervasyon.dto.CustomerDTO.CustomerCreateDTO;
import com.otelyonetim.rezervasyon.dto.CustomerDTO.CustomerFullResponseDTO;
import com.otelyonetim.rezervasyon.dto.CustomerDTO.CustomerLimitedResponseDTO;
import com.otelyonetim.rezervasyon.dto.CustomerDTO.CustomerUpdateDTO;
import com.otelyonetim.rezervasyon.entity.Customer;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public final class CustomerMapper {

    // Bu mapper sadece static metotlardan oluştuğu (yani utility class diye) için instance almamalı diye ekledim.
    private CustomerMapper() {
        throw new UnsupportedOperationException("Utility class");
    }

    // CreateDTO -> Entity
    // Yeni müşteri oluştururken kullanılan DTO’yu (CustomerCreateDTO) Customer entity’sine çevirir.
    public static Customer toEntity(CustomerCreateDTO dto){
        if(dto==null) return null;
        return Customer.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .tckn(dto.getTckn())
                .build();
    }

    // Entity -> FullResponseDTO
    /* Veritabanından gelen Customer entity’sini, tüm alanları içeren (id, createdAt, updatedAt dahil)
    * CustomerFullResponseDTO'ya çevirir.*/
    public static CustomerFullResponseDTO toFullDTO(Customer customer){
        if (customer == null) return null;
        return CustomerFullResponseDTO.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .tckn(customer.getTckn())
                .createdAt(customer.getCreatedAt())
                .updatedAt(customer.getUpdatedAt())
                .build();
    }

    // Entity -> LimitedResponseDTO
    // Customer entity’sini sınırlı bilgilerle (firstName, lastName, email) döner.
    public static CustomerLimitedResponseDTO toLimitedDTO(Customer customer){
        if (customer == null) return null;
        return CustomerLimitedResponseDTO.builder()
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .build();
    }

    // UpdateDTO -> Entity
    // Bir müşteriyi güncellerken sadece gelen DTO’da bulunan null olmayan alanları Customer entity’sine yazar.
    public static void updateEntityFromDTO(CustomerUpdateDTO dto, Customer customer){
        if(dto == null || customer == null) return;
        if(dto.getFirstName() != null) customer.setFirstName(dto.getFirstName());
        if(dto.getLastName() != null) customer.setLastName(dto.getLastName());
        if(dto.getEmail() != null) customer.setEmail(dto.getEmail());
        if(dto.getPhone() != null) customer.setPhone(dto.getPhone());
        if(dto.getTckn() != null) customer.setTckn(dto.getTckn());
    }

    // Entity -> UpdateDTO (tersi)
    public static CustomerUpdateDTO toUpdateDTO(Customer customer){
        if (customer == null) return null;
        return CustomerUpdateDTO.builder()
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .tckn(customer.getTckn())
                .build();
    }

    // List<Customer> -> List<CustomerFullResponseDTO>
    // List<Customer> listesini List<CustomerFullResponseDTO>'ya dönüştürür.
    public static List<CustomerFullResponseDTO> toFullDTOList(List<Customer> customers){
        if(customers == null) return List.of();
        return customers.stream()
                .map(CustomerMapper::toFullDTO)
                .collect(Collectors.toList());
    }

    // List<Customer> -> List<CustomerLimitedResponseDTO>
    // List<Customer> listesini List<CustomerLimitedResponseDTO>'ya dönüştürür.
    public static List<CustomerLimitedResponseDTO> toLimitedDTOList(List<Customer> customers){
        if(customers == null) return List.of();
        return customers.stream()
                .map(CustomerMapper::toLimitedDTO)
                .collect(Collectors.toList());
    }

    // CustomerFullResponseDTO -> CustomerLimitedResponseDTO
    // Full DTO’dan Limited DTO’ya dönüşüm yapar..
    public static CustomerLimitedResponseDTO fullToLimited(CustomerFullResponseDTO dto){
        if(dto == null) return null;
        return CustomerLimitedResponseDTO.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .build();
    }

    // Optional<CustomerFullResponseDTO> -> Optional<CustomerLimitedResponseDTO>
    public static Optional<CustomerLimitedResponseDTO> fullToLimited(Optional<CustomerFullResponseDTO> dto){
        if(dto == null) return Optional.empty();
        return dto.map(CustomerMapper::fullToLimited);
    }

}
