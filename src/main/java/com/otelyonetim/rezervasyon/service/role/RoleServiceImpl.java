package com.otelyonetim.rezervasyon.service.role;

import com.otelyonetim.rezervasyon.dto.RoleDTO.RoleCreateDTO;
import com.otelyonetim.rezervasyon.dto.RoleDTO.RoleFullResponseDTO;
import com.otelyonetim.rezervasyon.dto.RoleDTO.RoleLimitedResponseDTO;
import com.otelyonetim.rezervasyon.dto.RoleDTO.RoleUpdateDTO;
import com.otelyonetim.rezervasyon.entity.Role;
import com.otelyonetim.rezervasyon.exception.ResourceNotFoundException;
import com.otelyonetim.rezervasyon.mapper.RoleMapper;
import com.otelyonetim.rezervasyon.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public RoleFullResponseDTO createRole(RoleCreateDTO roleCreateDTO) {
        // Role ismi unique kontrolü
        if (existsByName(roleCreateDTO.getName())) {
            throw new IllegalArgumentException("Bu role ismi zaten kayıtlı: " + roleCreateDTO.getName());
        }

        Role role = RoleMapper.toEntity(roleCreateDTO);
        Role savedRole = roleRepository.save(role);
        return RoleMapper.toFullDTO(savedRole);
    }

    @Override
    @Transactional(readOnly = true)
    public RoleFullResponseDTO getRoleById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role bulunamadı. ID: " + id));
        return RoleMapper.toFullDTO(role);
    }

    @Override
    @Transactional(readOnly = true)
    public RoleFullResponseDTO getRoleByName(String name) {
        Role role = roleRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Role bulunamadı. Name: " + name));
        return RoleMapper.toFullDTO(role);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleFullResponseDTO> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return RoleMapper.toFullDTOList(roles);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleLimitedResponseDTO> getAllRolesLimited() {
        List<Role> roles = roleRepository.findAll();
        return RoleMapper.toLimitedDTOList(roles);
    }

    @Override
    @Transactional
    public RoleFullResponseDTO updateRole(Long id, RoleUpdateDTO roleUpdateDTO) {
        Role existingRole = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role bulunamadı. ID: " + id));

        // Role ismi değişikliği kontrolü
        if (roleUpdateDTO.getName() != null &&
                !existingRole.getName().equals(roleUpdateDTO.getName()) &&
                existsByName(roleUpdateDTO.getName())) {
            throw new IllegalArgumentException("Bu role ismi zaten kayıtlı: " + roleUpdateDTO.getName());
        }

        RoleMapper.updateEntityFromDTO(roleUpdateDTO, existingRole);
        Role updatedRole = roleRepository.save(existingRole);
        return RoleMapper.toFullDTO(updatedRole);
    }

    @Override
    @Transactional
    public void deleteRole(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role bulunamadı. ID: " + id));

        // Role silinmeden önce kullanıcı ilişkilerini kontrol et
        if (!role.getUsers().isEmpty()) {
            throw new IllegalStateException("Bu role atanmış kullanıcılar var. Önce kullanıcıları güncelleyin.");
        }

        roleRepository.delete(role);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleFullResponseDTO> getRolesByMinUserCount(int minUserCount) {
        List<Role> roles = roleRepository.findByMinUserCount(minUserCount);
        return RoleMapper.toFullDTOList(roles);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleFullResponseDTO> getRolesOrderedByUserCount() {
        List<Role> roles = roleRepository.findAllOrderByUserCountDesc();
        return RoleMapper.toFullDTOList(roles);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleFullResponseDTO> searchRolesByName(String name) {
        List<Role> roles = roleRepository.findByNameContainingIgnoreCase(name);
        return RoleMapper.toFullDTOList(roles);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        return roleRepository.existsByName(name);
    }
}