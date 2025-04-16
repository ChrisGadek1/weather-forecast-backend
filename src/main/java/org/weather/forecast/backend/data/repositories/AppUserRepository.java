package org.weather.forecast.backend.data.repositories;

import org.springframework.data.repository.CrudRepository;
import org.weather.forecast.backend.data.models.AppUser;

import java.util.List;

public interface AppUserRepository extends CrudRepository<AppUser, Integer> {
    AppUser findByUsername(String username);

    AppUser findByRole(String role);
}