package com.itacademy.web_rental_car.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests((authorize) ->
                authorize
                        .requestMatchers("/mainPage/**").permitAll()
                        .requestMatchers("/stylesMainPage.css").permitAll()
                        .requestMatchers("/carDetails/**").permitAll()
                        .requestMatchers("/stylesCarDetails.css").permitAll()
                        .requestMatchers("/showAllCars/**").permitAll()
                        .requestMatchers("/userPage/**").hasAnyAuthority("ROLE_USER")
                        .requestMatchers("/adminPage/**").hasAnyAuthority("ROLE_ADMIN")
                        .requestMatchers("/stylesAdminPage.css").permitAll()
                        .requestMatchers("/stylesUserPage.css").permitAll()
                        .requestMatchers("/stylesPlacingOrder.css").permitAll()
                        .anyRequest().permitAll()

        ).formLogin(
                form -> form
                        .loginPage("/mainPage")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/mainPage")
                        .permitAll()

        );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
