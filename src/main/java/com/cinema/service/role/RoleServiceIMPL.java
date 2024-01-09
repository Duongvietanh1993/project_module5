package com.cinema.service.role;

import com.cinema.model.entity.Role;
import com.cinema.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceIMPL implements RoleService{
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public Role findByRoleName(String roleName) {
        return roleRepository.findRoleByName(roleName);
    }

    @Override
    public Role findById(Long id) {
        return roleRepository.findRoleById(id);
    }
}
