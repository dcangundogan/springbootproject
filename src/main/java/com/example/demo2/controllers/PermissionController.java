package com.example.demo2.controllers;

import com.example.demo2.entitites.Permissions;
import com.example.demo2.repostories.PermissionsRepository;
import com.example.demo2.services.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/api/perms")
public class PermissionController {
    @Autowired
    PermissionsRepository permissionsRepository;
    @Autowired
    PermissionService permissionsService;


    @PostMapping
    public Permissions createPermission(@RequestBody Permissions permissions){
        return permissionsService.savePermission(permissions);

    }
    @GetMapping
    public List<Permissions> getAllPermissions(){
        return permissionsService.getAllPermission();
    }
    @PutMapping("/{id}/perm/{name}")

    public void addPermissionToRole(@PathVariable UUID id,@PathVariable String name){
        permissionsService.addPermissionToRole(id,name);

    }



}
