package com.example.demo2.controllers;


import com.example.demo2.services.RoleService;
import com.example.demo2.repostories.RolesRepository;
import com.example.demo2.entitites.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/api/roles")
public class RoleController {
    @Autowired
    private RolesRepository roleRepository;
    @Autowired
    private RoleService roleService;

    @PostMapping
    public Roles createRole(@RequestBody Roles role){
        return roleService.saveRole(role);
    }
    @GetMapping
    public List<Roles> getAllRoles(){
        return roleService.getallRoles();

    }
    @PutMapping("/{id}/role/{rolename}")
    public void addRoleToUser(@PathVariable UUID id, @PathVariable String rolename){
        roleService.addRoleToUser(id, rolename);
    }



}
