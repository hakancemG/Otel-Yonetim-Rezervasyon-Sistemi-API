package com.otelyonetim.rezervasyon.controller;

import com.otelyonetim.rezervasyon.dto.RoleDTO.RoleCreateDTO;
import com.otelyonetim.rezervasyon.dto.RoleDTO.RoleFullResponseDTO;
import com.otelyonetim.rezervasyon.dto.RoleDTO.RoleLimitedResponseDTO;
import com.otelyonetim.rezervasyon.dto.RoleDTO.RoleUpdateDTO;
import com.otelyonetim.rezervasyon.service.role.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    public ResponseEntity<RoleFullResponseDTO> createRole(
            @Valid @RequestBody RoleCreateDTO roleCreateDTO) {
        RoleFullResponseDTO createdRole = roleService.createRole(roleCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRole);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleFullResponseDTO> getRoleById(@PathVariable Long id) {
        RoleFullResponseDTO role = roleService.getRoleById(id);
        return ResponseEntity.ok(role);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<RoleFullResponseDTO> getRoleByName(@PathVariable String name) {
        RoleFullResponseDTO role = roleService.getRoleByName(name);
        return ResponseEntity.ok(role);
    }

    @GetMapping
    public ResponseEntity<List<RoleFullResponseDTO>> getAllRoles() {
        List<RoleFullResponseDTO> roles = roleService.getAllRoles();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/limited")
    public ResponseEntity<List<RoleLimitedResponseDTO>> getAllRolesLimited() {
        List<RoleLimitedResponseDTO> roles = roleService.getAllRolesLimited();
        return ResponseEntity.ok(roles);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleFullResponseDTO> updateRole(
            @PathVariable Long id,
            @Valid @RequestBody RoleUpdateDTO roleUpdateDTO) {
        RoleFullResponseDTO updatedRole = roleService.updateRole(id, roleUpdateDTO);
        return ResponseEntity.ok(updatedRole);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/min-users/{minUserCount}")
    public ResponseEntity<List<RoleFullResponseDTO>> getRolesByMinUserCount(
            @PathVariable int minUserCount) {
        List<RoleFullResponseDTO> roles = roleService.getRolesByMinUserCount(minUserCount);
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/ordered/user-count")
    public ResponseEntity<List<RoleFullResponseDTO>> getRolesOrderedByUserCount() {
        List<RoleFullResponseDTO> roles = roleService.getRolesOrderedByUserCount();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<List<RoleFullResponseDTO>> searchRolesByName(
            @PathVariable String name) {
        List<RoleFullResponseDTO> roles = roleService.searchRolesByName(name);
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/exists/{name}")
    public ResponseEntity<Boolean> existsByName(@PathVariable String name) {
        boolean exists = roleService.existsByName(name);
        return ResponseEntity.ok(exists);
    }
}