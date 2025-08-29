package com.otelyonetim.rezervasyon.controller;

import com.otelyonetim.rezervasyon.dto.CustomerDTO.CustomerCreateDTO;
import com.otelyonetim.rezervasyon.dto.CustomerDTO.CustomerFullResponseDTO;
import com.otelyonetim.rezervasyon.dto.CustomerDTO.CustomerLimitedResponseDTO;
import com.otelyonetim.rezervasyon.dto.CustomerDTO.CustomerUpdateDTO;
import com.otelyonetim.rezervasyon.service.customer.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerFullResponseDTO> createCustomer(
            @Valid @RequestBody CustomerCreateDTO customerCreateDTO) {
        CustomerFullResponseDTO createdCustomer = customerService.createCustomer(customerCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomer);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerFullResponseDTO> getCustomerById(@PathVariable Long id) {
        CustomerFullResponseDTO customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }

    @GetMapping
    public ResponseEntity<List<CustomerFullResponseDTO>> getAllCustomers() {
        List<CustomerFullResponseDTO> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/limited")
    public ResponseEntity<List<CustomerLimitedResponseDTO>> getAllCustomersLimited() {
        List<CustomerLimitedResponseDTO> customers = customerService.getAllCustomersLimited();
        return ResponseEntity.ok(customers);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerFullResponseDTO> updateCustomer(
            @PathVariable Long id,
            @Valid @RequestBody CustomerUpdateDTO customerUpdateDTO) {
        CustomerFullResponseDTO updatedCustomer = customerService.updateCustomer(id, customerUpdateDTO);
        return ResponseEntity.ok(updatedCustomer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<CustomerFullResponseDTO> getCustomerByEmail(@PathVariable String email) {
        CustomerFullResponseDTO customer = customerService.getCustomerByEmail(email);
        return ResponseEntity.ok(customer);
    }

    @GetMapping("/tckn/{tckn}")
    public ResponseEntity<CustomerFullResponseDTO> getCustomerByTckn(@PathVariable String tckn) {
        CustomerFullResponseDTO customer = customerService.getCustomerByTckn(tckn);
        return ResponseEntity.ok(customer);
    }

    @GetMapping("/search/name/{name}")
    public ResponseEntity<List<CustomerFullResponseDTO>> searchCustomersByName(@PathVariable String name) {
        List<CustomerFullResponseDTO> customers = customerService.searchCustomersByName(name);
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/search/lastname/{lastName}")
    public ResponseEntity<List<CustomerFullResponseDTO>> searchCustomersByLastName(@PathVariable String lastName) {
        List<CustomerFullResponseDTO> customers = customerService.searchCustomersByLastName(lastName);
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/exists/email/{email}")
    public ResponseEntity<Boolean> existsByEmail(@PathVariable String email) {
        boolean exists = customerService.existsByEmail(email);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/exists/tckn/{tckn}")
    public ResponseEntity<Boolean> existsByTckn(@PathVariable String tckn) {
        boolean exists = customerService.existsByTckn(tckn);
        return ResponseEntity.ok(exists);
    }
}