package org.weather.forecast.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.weather.forecast.backend.data.models.AppUser;
import org.weather.forecast.backend.data.repositories.AppUserRepository;

@Service
public class UserDatabaseService implements UserDetailsService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = appUserRepository.findByUsername(username);
        if(user != null) {
            return user.toUserDetails();
        } else {
            throw new UsernameNotFoundException("username is empty");
        }
    }
}
