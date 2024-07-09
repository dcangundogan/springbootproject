package com.example.demo2;
import jakarta.persistence.*;
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType;

import java.util.Date;
import java.util.UUID;


@Entity
@Table(name="tbl_usr")
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;
    //can

    @Column(nullable = false)
    private String identity_number;

    @Column(nullable = false)
    private Date birth_date;

    @Column(nullable = false)
    private float salary;

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setIdentity_number(String identity_number) {
        this.identity_number = identity_number;
    }

    public void setBirth_date(Date birth_date) {
        this.birth_date = birth_date;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getIdentity_number() {
        return identity_number;
    }

    public Date getBirth_date() {
        return birth_date;
    }

    public float getSalary() {
        return salary;
    }
}

