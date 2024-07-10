package com.example.demo2.repostories;

import com.example.demo2.entitites.Permissions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PermissionsRepository extends JpaRepository<Permissions, UUID> {
    Permissions findByName(String name);

}
