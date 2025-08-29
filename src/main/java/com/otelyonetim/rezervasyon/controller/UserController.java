package com.otelyonetim.rezervasyon.controller;

import com.otelyonetim.rezervasyon.dto.UserDTO.UserCreateDTO;
import com.otelyonetim.rezervasyon.dto.UserDTO.UserFullResponseDTO;
import com.otelyonetim.rezervasyon.dto.UserDTO.UserLimitedResponseDTO;
import com.otelyonetim.rezervasyon.dto.UserDTO.UserUpdateDTO;
import com.otelyonetim.rezervasyon.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserFullResponseDTO> createUser(
            @Valid @RequestBody UserCreateDTO userCreateDTO) {
        UserFullResponseDTO createdUser = userService.createUser(userCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserFullResponseDTO> getUserById(@PathVariable Long id) {
        UserFullResponseDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserFullResponseDTO> getUserByUsername(@PathVariable String username) {
        UserFullResponseDTO user = userService.getUserByUsername(username);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserFullResponseDTO> getUserByEmail(@PathVariable String email) {
        UserFullResponseDTO user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<UserFullResponseDTO>> getAllUsers() {
        List<UserFullResponseDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/limited")
    public ResponseEntity<List<UserLimitedResponseDTO>> getAllUsersLimited() {
        List<UserLimitedResponseDTO> users = userService.getAllUsersLimited();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/active")
    public ResponseEntity<List<UserFullResponseDTO>> getActiveUsers() {
        List<UserFullResponseDTO> users = userService.getActiveUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserFullResponseDTO> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserUpdateDTO userUpdateDTO) {
        UserFullResponseDTO updatedUser = userService.updateUser(id, userUpdateDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<UserFullResponseDTO> deactivateUser(@PathVariable Long id) {
        UserFullResponseDTO user = userService.deactivateUser(id);
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<UserFullResponseDTO> activateUser(@PathVariable Long id) {
        UserFullResponseDTO user = userService.activateUser(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/role/{roleName}")
    public ResponseEntity<List<UserFullResponseDTO>> getUsersByRole(@PathVariable String roleName) {
        List<UserFullResponseDTO> users = userService.getUsersByRole(roleName);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/search/username/{username}")
    public ResponseEntity<List<UserFullResponseDTO>> searchUsersByUsername(@PathVariable String username) {
        List<UserFullResponseDTO> users = userService.searchUsersByUsername(username);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/search/email/{email}")
    public ResponseEntity<List<UserFullResponseDTO>> searchUsersByEmail(@PathVariable String email) {
        List<UserFullResponseDTO> users = userService.searchUsersByEmail(email);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/exists/username/{username}")
    public ResponseEntity<Boolean> existsByUsername(@PathVariable String username) {
        boolean exists = userService.existsByUsername(username);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/exists/email/{email}")
    public ResponseEntity<Boolean> existsByEmail(@PathVariable String email) {
        boolean exists = userService.existsByEmail(email);
        return ResponseEntity.ok(exists);
    }
}