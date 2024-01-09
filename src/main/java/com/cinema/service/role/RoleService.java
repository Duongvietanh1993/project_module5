package com.cinema.service.role;

import com.cinema.model.entity.Role;

public interface RoleService {
    Role findByRoleName(String roleName);
    Role findById(Long id);
}
