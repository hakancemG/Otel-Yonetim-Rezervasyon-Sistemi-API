package com.otelyonetim.rezervasyon.service.user;

import com.otelyonetim.rezervasyon.dto.UserDTO.UserCreateDTO;
import com.otelyonetim.rezervasyon.dto.UserDTO.UserFullResponseDTO;
import com.otelyonetim.rezervasyon.dto.UserDTO.UserLimitedResponseDTO;
import com.otelyonetim.rezervasyon.dto.UserDTO.UserUpdateDTO;
import com.otelyonetim.rezervasyon.entity.Role;
import com.otelyonetim.rezervasyon.entity.User;
import com.otelyonetim.rezervasyon.exception.ResourceNotFoundException;
import com.otelyonetim.rezervasyon.mapper.UserMapper;
import com.otelyonetim.rezervasyon.repository.RoleRepository;
import com.otelyonetim.rezervasyon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public UserFullResponseDTO createUser(UserCreateDTO userCreateDTO) {
        // Username unique kontrolü
        if (existsByUsername(userCreateDTO.getUsername())) {
            throw new IllegalArgumentException("Bu kullanıcı adı zaten kayıtlı: " + userCreateDTO.getUsername());
        }

        // Email unique kontrolü
        if (existsByEmail(userCreateDTO.getEmail())) {
            throw new IllegalArgumentException("Bu email adresi zaten kayıtlı: " + userCreateDTO.getEmail());
        }

        // Role ID'lerini kontrol et ve Role entity'lerini getir
        Set<Role> roles = validateAndGetRoles(userCreateDTO.getRoleIds());

        User user = UserMapper.toEntity(userCreateDTO);
        user.setRoles(roles);
        user.setActive(true);

        User savedUser = userRepository.save(user);
        return UserMapper.toFullDTO(savedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public UserFullResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Kullanıcı bulunamadı. ID: " + id));
        return UserMapper.toFullDTO(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserFullResponseDTO getUserByUsername(String username) {
        User user = userRepository.findByUsernameAndActive(username)
                .orElseThrow(() -> new ResourceNotFoundException("Kullanıcı bulunamadı. Username: " + username));
        return UserMapper.toFullDTO(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserFullResponseDTO getUserByEmail(String email) {
        User user = userRepository.findByEmailAndActive(email)
                .orElseThrow(() -> new ResourceNotFoundException("Kullanıcı bulunamadı. Email: " + email));
        return UserMapper.toFullDTO(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserFullResponseDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return UserMapper.toFullDTOList(users);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserLimitedResponseDTO> getAllUsersLimited() {
        List<User> users = userRepository.findAll();
        return UserMapper.toLimitedDTOList(users);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserFullResponseDTO> getActiveUsers() {
        List<User> users = userRepository.findAllActive();
        return UserMapper.toFullDTOList(users);
    }

    @Override
    @Transactional
    public UserFullResponseDTO updateUser(Long id, UserUpdateDTO userUpdateDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Kullanıcı bulunamadı. ID: " + id));

        // Username değişikliği kontrolü
        if (userUpdateDTO.getUsername() != null &&
                !existingUser.getUsername().equals(userUpdateDTO.getUsername()) &&
                existsByUsername(userUpdateDTO.getUsername())) {
            throw new IllegalArgumentException("Bu kullanıcı adı zaten kayıtlı: " + userUpdateDTO.getUsername());
        }

        // Email değişikliği kontrolü
        if (userUpdateDTO.getEmail() != null &&
                !existingUser.getEmail().equals(userUpdateDTO.getEmail()) &&
                existsByEmail(userUpdateDTO.getEmail())) {
            throw new IllegalArgumentException("Bu email adresi zaten kayıtlı: " + userUpdateDTO.getEmail());
        }

        // Role ID'lerini kontrol et ve Role entity'lerini getir
        Set<Role> roles = null;
        if (userUpdateDTO.getRoleIds() != null) {
            roles = validateAndGetRoles(userUpdateDTO.getRoleIds());
        }

        UserMapper.updateEntityFromDTO(userUpdateDTO, existingUser);

        // Rolleri güncelle
        if (roles != null) {
            existingUser.setRoles(roles);
        }

        User updatedUser = userRepository.save(existingUser);
        return UserMapper.toFullDTO(updatedUser);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Kullanıcı bulunamadı. ID: " + id));
        userRepository.delete(user);
    }

    @Override
    @Transactional
    public UserFullResponseDTO deactivateUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Kullanıcı bulunamadı. ID: " + id));

        user.setActive(false);
        User deactivatedUser = userRepository.save(user);
        return UserMapper.toFullDTO(deactivatedUser);
    }

    @Override
    @Transactional
    public UserFullResponseDTO activateUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Kullanıcı bulunamadı. ID: " + id));

        user.setActive(true);
        User activatedUser = userRepository.save(user);
        return UserMapper.toFullDTO(activatedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserFullResponseDTO> getUsersByRole(String roleName) {
        List<User> users = userRepository.findByRoleName(roleName);
        return UserMapper.toFullDTOList(users);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserFullResponseDTO> searchUsersByUsername(String username) {
        List<User> users = userRepository.findByUsernameContainingIgnoreCase(username);
        return UserMapper.toFullDTOList(users);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserFullResponseDTO> searchUsersByEmail(String email) {
        List<User> users = userRepository.findByEmailContainingIgnoreCase(email);
        return UserMapper.toFullDTOList(users);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    private Set<Role> validateAndGetRoles(Set<Long> roleIds) {
        if (roleIds == null || roleIds.isEmpty()) {
            return Set.of();
        }

        // Tüm role ID'lerinin geçerli olup olmadığını kontrol et
        List<Role> roles = roleRepository.findAllById(roleIds);
        if (roles.size() != roleIds.size()) {
            throw new IllegalArgumentException("Geçersiz role ID'leri bulundu");
        }

        return Set.copyOf(roles);
    }
}