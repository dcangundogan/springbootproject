package com.example.demo2.services;

import com.example.demo2.auth.Roles;
import com.example.demo2.auth.RolesRepository;
import com.example.demo2.entitites.Permissions;
import com.example.demo2.entitites.User;
import com.example.demo2.repostories.PermissionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class PermissionService {
    @Autowired
    private RolesRepository roleRepository;
    @Autowired
    private PermissionsRepository permissionsRepository;


        public Permissions savePermission(Permissions permission){
            return permissionsRepository.save(permission);



        }
    public void addPermissionToRole(UUID id, String name){
        Optional<Roles> roleOPt = roleRepository.findById(id);
        Permissions permission = permissionsRepository.findByName(name);

        if (roleOPt.isPresent() && permission != null) {
            Roles roles  = roleOPt.get();
            roles.getPermissions().add(permission);
            roleRepository.save(roles);
        } else {
            // Handle the case where user or role is not found
            throw new RuntimeException("Permission or Role not found");
        }}



    public List<Permissions> getAllPermission() {
        return permissionsRepository.findAll();

    }


    }

