package com.otelyonetim.rezervasyon.service.customer;

import com.otelyonetim.rezervasyon.dto.CustomerDTO.CustomerCreateDTO;
import com.otelyonetim.rezervasyon.dto.CustomerDTO.CustomerFullResponseDTO;
import com.otelyonetim.rezervasyon.dto.CustomerDTO.CustomerLimitedResponseDTO;
import com.otelyonetim.rezervasyon.dto.CustomerDTO.CustomerUpdateDTO;

import java.util.List;

public interface CustomerService {

    CustomerFullResponseDTO createCustomer(CustomerCreateDTO customerCreateDTO);

    CustomerFullResponseDTO getCustomerById(Long id);

    List<CustomerFullResponseDTO> getAllCustomers();

    List<CustomerLimitedResponseDTO> getAllCustomersLimited();

    CustomerFullResponseDTO updateCustomer(Long id, CustomerUpdateDTO customerUpdateDTO);

    void deleteCustomer(Long id);

    CustomerFullResponseDTO getCustomerByEmail(String email);

    CustomerFullResponseDTO getCustomerByTckn(String tckn);

    List<CustomerFullResponseDTO> searchCustomersByName(String name);

    List<CustomerFullResponseDTO> searchCustomersByLastName(String lastName);

    boolean existsByEmail(String email);

    boolean existsByTckn(String tckn);
}