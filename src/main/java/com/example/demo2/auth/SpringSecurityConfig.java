package com.example.demo2.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SpringSecurityConfig  {
    private final MyAppAuthencationProvider authenticationProvider;



    public SpringSecurityConfig(MyAppAuthencationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;}

    @Bean
    SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.httpBasic(Customizer.withDefaults());
        http.authenticationProvider(authenticationProvider);
        http.authorizeHttpRequests(c -> c.anyRequest().permitAll());
        return http.build();
    }


}
