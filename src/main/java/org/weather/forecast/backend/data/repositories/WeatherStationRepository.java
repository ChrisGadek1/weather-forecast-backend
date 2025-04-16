package org.weather.forecast.backend.data.repositories;

import org.springframework.data.repository.CrudRepository;
import org.weather.forecast.backend.data.models.AppUser;
import org.weather.forecast.backend.data.models.WeatherStation;

public interface WeatherStationRepository extends CrudRepository<WeatherStation, Integer> {
    WeatherStation findByAppUserId(Long AppUserId);

    WeatherStation findByName(String name);
}
