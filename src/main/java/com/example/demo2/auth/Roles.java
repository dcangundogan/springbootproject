package com.example.demo2.auth;

import com.example.demo2.User;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name="roles")
public class Roles {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(nullable = false)
    private UUID id;

    @Column(nullable = false,unique = true)
    private String rolename;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();

}
