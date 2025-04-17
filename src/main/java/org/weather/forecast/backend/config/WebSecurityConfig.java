package org.weather.forecast.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.weather.forecast.backend.services.UserDatabaseService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private UserDatabaseService userDatabaseService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((requests) -> requests
                        // Public access
                        .requestMatchers(HttpMethod.GET, "/weather/measure").permitAll()
                        .requestMatchers(HttpMethod.GET, "/weather/station").permitAll()
                        .requestMatchers(HttpMethod.GET, "/ws-weather").permitAll()

                        // Access restricted to role WEATHER_STATION
                        .requestMatchers(HttpMethod.POST, "/weather/measure").hasRole("WEATHER_STATION")

                        // All other endpoints restricted to role ADMIN
                        .anyRequest().hasRole("ADMIN")
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder) throws Exception {
        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.userDetailsService(userDatabaseService).passwordEncoder(passwordEncoder);
        return builder.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

