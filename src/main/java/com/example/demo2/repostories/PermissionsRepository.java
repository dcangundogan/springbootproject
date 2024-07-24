package com.example.demo2.repostories;

import com.example.demo2.entitites.Permissions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;
@Repository
public interface PermissionsRepository extends JpaRepository<Permissions, UUID> {
    Permissions findByName(String name);



}
