package com.example.demo2.token;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;


    void saveConfirmationToken(ConfirmationToken confirmationToken){

        confirmationTokenRepository.save(confirmationToken);
        System.out.println(confirmationToken.toString());
    }

}
