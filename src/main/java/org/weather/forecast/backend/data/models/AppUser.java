package org.weather.forecast.backend.data.models;

import jakarta.persistence.*;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "users")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String role;

    public String getRole() {
        return role;
    }

    public String getUsername() {
        return username;
    }

    public UserDetails toUserDetails() {
        return User
                .builder()
                .username(this.username)
                .password(this.password)
                .roles(this.role)
                .build();
    }

    public AppUser() {}

    public AppUser(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
