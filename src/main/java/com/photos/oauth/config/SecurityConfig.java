package com.photos.oauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{

        httpSecurity.authorizeHttpRequests(request ->
            request.requestMatchers("/photos").authenticated()
                    .anyRequest().permitAll()
        )
                .formLogin(Customizer.withDefaults())
                .oauth2Login(oauth->{
                    oauth.successHandler((request, response, authentication) -> {

                        System.out.println("Oauth login success");
                        System.out.println(authentication.toString());
                        response.sendRedirect("/");
                    })
                                    .failureHandler((request, response, exception) -> {

                                        System.out.println("Login Error");
                                        response.sendRedirect("/");
                                        exception.printStackTrace();

                                    });

                })
        ;

        return httpSecurity.build();
    }
}
