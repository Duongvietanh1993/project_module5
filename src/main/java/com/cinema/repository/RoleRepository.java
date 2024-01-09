package com.cinema.repository;

import com.cinema.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findRoleByName(String roleName);
    Role findRoleById(Long id);
}
