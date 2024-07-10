package com.example.demo2.auth;

import com.example.demo2.entitites.User;
import com.example.demo2.repostories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Service
public class RoleService {

    @Autowired
    private RolesRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    public Roles saveRole(Roles role){
        return roleRepository.save(role);



    }
    public void addRoleToUser(UUID id, String rolename){
        Optional<User> userOpt = userRepository.findById(id);
        Roles role = roleRepository.findByRolename(rolename);

        if (userOpt.isPresent() && role != null) {
            User user = userOpt.get();
            user.getRoles().add(role);
            userRepository.save(user);
        } else {
            // Handle the case where user or role is not found
            throw new RuntimeException("User or Role not found");
        }}



    public List<Roles> getallRoles() {
        return roleRepository.findAll();

    }
}
