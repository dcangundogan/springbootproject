package com.example.demo2.token;

import com.example.demo2.entitites.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ConfirmationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    private String confirmationtoken;

    private LocalDate  createdDate;

    @OneToOne(targetEntity = User.class,fetch = FetchType.EAGER)
    @JoinColumn(nullable = false,name="user_id")
    private User user;

    ConfirmationToken(User user){
        this.user=user;
        this.createdDate=LocalDate.now();
        this.confirmationtoken=UUID.randomUUID().toString();
    }
}
