package com.example.demo2.token;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;


    public void saveConfirmationToken(ConfirmationToken confirmationToken){

        confirmationTokenRepository.save(confirmationToken);
        System.out.println(confirmationToken.toString() + "User will sign up or login with this token");
        //TODO when projects finishes delete the sout line
    }
    public void deleteConfirmationToken(Long id){
        confirmationTokenRepository.deleteById(id);



    }

}
