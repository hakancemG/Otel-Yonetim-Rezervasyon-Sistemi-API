package com.otelyonetim.rezervasyon.service.role;

import com.otelyonetim.rezervasyon.dto.RoleDTO.RoleCreateDTO;
import com.otelyonetim.rezervasyon.dto.RoleDTO.RoleFullResponseDTO;
import com.otelyonetim.rezervasyon.dto.RoleDTO.RoleLimitedResponseDTO;
import com.otelyonetim.rezervasyon.dto.RoleDTO.RoleUpdateDTO;

import java.util.List;

public interface RoleService {

    RoleFullResponseDTO createRole(RoleCreateDTO roleCreateDTO);

    RoleFullResponseDTO getRoleById(Long id);

    RoleFullResponseDTO getRoleByName(String name);

    List<RoleFullResponseDTO> getAllRoles();

    List<RoleLimitedResponseDTO> getAllRolesLimited();

    RoleFullResponseDTO updateRole(Long id, RoleUpdateDTO roleUpdateDTO);

    void deleteRole(Long id);

    List<RoleFullResponseDTO> getRolesByMinUserCount(int minUserCount);

    List<RoleFullResponseDTO> getRolesOrderedByUserCount();

    List<RoleFullResponseDTO> searchRolesByName(String name);

    boolean existsByName(String name);
}