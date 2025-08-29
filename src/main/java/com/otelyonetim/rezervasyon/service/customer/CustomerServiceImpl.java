package com.otelyonetim.rezervasyon.service.customer;

import com.otelyonetim.rezervasyon.dto.CustomerDTO.CustomerCreateDTO;
import com.otelyonetim.rezervasyon.dto.CustomerDTO.CustomerFullResponseDTO;
import com.otelyonetim.rezervasyon.dto.CustomerDTO.CustomerLimitedResponseDTO;
import com.otelyonetim.rezervasyon.dto.CustomerDTO.CustomerUpdateDTO;
import com.otelyonetim.rezervasyon.entity.Customer;
import com.otelyonetim.rezervasyon.exception.ResourceNotFoundException;
import com.otelyonetim.rezervasyon.mapper.CustomerMapper;
import com.otelyonetim.rezervasyon.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    @Transactional
    public CustomerFullResponseDTO createCustomer(CustomerCreateDTO customerCreateDTO) {
        // Unique constraint kontrolleri
        if (existsByEmail(customerCreateDTO.getEmail())) {
            throw new IllegalArgumentException("Bu email adresi zaten kayıtlı: " + customerCreateDTO.getEmail());
        }

        if (existsByTckn(customerCreateDTO.getTckn())) {
            throw new IllegalArgumentException("Bu TCKN zaten kayıtlı: " + customerCreateDTO.getTckn());
        }

        Customer customer = CustomerMapper.toEntity(customerCreateDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return CustomerMapper.toFullDTO(savedCustomer);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerFullResponseDTO getCustomerById(Long id) {
        Customer customer = customerRepository.findByIdAndNotDeleted(id)
                .orElseThrow(() -> new ResourceNotFoundException("Müşteri bulunamadı. ID: " + id));
        return CustomerMapper.toFullDTO(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerFullResponseDTO> getAllCustomers() {
        List<Customer> customers = customerRepository.findAllActive();
        return CustomerMapper.toFullDTOList(customers);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerLimitedResponseDTO> getAllCustomersLimited() {
        List<Customer> customers = customerRepository.findAllActive();
        return CustomerMapper.toLimitedDTOList(customers);
    }

    @Override
    @Transactional
    public CustomerFullResponseDTO updateCustomer(Long id, CustomerUpdateDTO customerUpdateDTO) {
        Customer existingCustomer = customerRepository.findByIdAndNotDeleted(id)
                .orElseThrow(() -> new ResourceNotFoundException("Müşteri bulunamadı. ID: " + id));

        // Email değişikliği kontrolü
        if (customerUpdateDTO.getEmail() != null &&
                !existingCustomer.getEmail().equals(customerUpdateDTO.getEmail()) &&
                existsByEmail(customerUpdateDTO.getEmail())) {
            throw new IllegalArgumentException("Bu email adresi zaten kayıtlı: " + customerUpdateDTO.getEmail());
        }

        // TCKN değişikliği kontrolü
        if (customerUpdateDTO.getTckn() != null &&
                !existingCustomer.getTckn().equals(customerUpdateDTO.getTckn()) &&
                existsByTckn(customerUpdateDTO.getTckn())) {
            throw new IllegalArgumentException("Bu TCKN zaten kayıtlı: " + customerUpdateDTO.getTckn());
        }

        CustomerMapper.updateEntityFromDTO(customerUpdateDTO, existingCustomer);
        Customer updatedCustomer = customerRepository.save(existingCustomer);
        return CustomerMapper.toFullDTO(updatedCustomer);
    }

    @Override
    @Transactional
    public void deleteCustomer(Long id) {
        Customer customer = customerRepository.findByIdAndNotDeleted(id)
                .orElseThrow(() -> new ResourceNotFoundException("Müşteri bulunamadı. ID: " + id));
        customerRepository.delete(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerFullResponseDTO getCustomerByEmail(String email) {
        Customer customer = customerRepository.findByEmailAndNotDeleted(email)
                .orElseThrow(() -> new ResourceNotFoundException("Müşteri bulunamadı. Email: " + email));
        return CustomerMapper.toFullDTO(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerFullResponseDTO getCustomerByTckn(String tckn) {
        Customer customer = customerRepository.findByTcknAndNotDeleted(tckn)
                .orElseThrow(() -> new ResourceNotFoundException("Müşteri bulunamadı. TCKN: " + tckn));
        return CustomerMapper.toFullDTO(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerFullResponseDTO> searchCustomersByName(String name) {
        List<Customer> customers = customerRepository.findByFirstNameContainingIgnoreCase(name);
        return CustomerMapper.toFullDTOList(customers);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerFullResponseDTO> searchCustomersByLastName(String lastName) {
        List<Customer> customers = customerRepository.findByLastNameContainingIgnoreCase(lastName);
        return CustomerMapper.toFullDTOList(customers);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return customerRepository.existsByEmailAndNotDeleted(email);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByTckn(String tckn) {
        return customerRepository.existsByTcknAndNotDeleted(tckn);
    }
}