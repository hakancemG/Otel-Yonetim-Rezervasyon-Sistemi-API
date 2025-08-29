package com.otelyonetim.rezervasyon.service.user;

import com.otelyonetim.rezervasyon.dto.UserDTO.UserCreateDTO;
import com.otelyonetim.rezervasyon.dto.UserDTO.UserFullResponseDTO;
import com.otelyonetim.rezervasyon.dto.UserDTO.UserLimitedResponseDTO;
import com.otelyonetim.rezervasyon.dto.UserDTO.UserUpdateDTO;

import java.util.List;

public interface UserService {

    UserFullResponseDTO createUser(UserCreateDTO userCreateDTO);

    UserFullResponseDTO getUserById(Long id);

    UserFullResponseDTO getUserByUsername(String username);

    UserFullResponseDTO getUserByEmail(String email);

    List<UserFullResponseDTO> getAllUsers();

    List<UserLimitedResponseDTO> getAllUsersLimited();

    List<UserFullResponseDTO> getActiveUsers();

    UserFullResponseDTO updateUser(Long id, UserUpdateDTO userUpdateDTO);

    void deleteUser(Long id);

    UserFullResponseDTO deactivateUser(Long id);

    UserFullResponseDTO activateUser(Long id);

    List<UserFullResponseDTO> getUsersByRole(String roleName);

    List<UserFullResponseDTO> searchUsersByUsername(String username);

    List<UserFullResponseDTO> searchUsersByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}