package com.example.demo2.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface RolesRepository extends JpaRepository<Roles, UUID> {
    Roles findByRolename(String rolename);
}
