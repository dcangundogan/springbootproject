    package com.example.demo2.entitites;

    import com.example.demo2.auth.Roles;
    import jakarta.persistence.*;

    import java.util.Set;
    import java.util.UUID;

    @Entity
    @Table(name = "permissions")
    public class Permissions {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(nullable = false)
        private UUID id;

        @Column(nullable = false,unique = true)
        private  String name;





        public UUID getId() {
            return id;
        }

        public void setId(UUID id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }


    }
