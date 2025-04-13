package org.weather.forecast.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.weather.forecast.backend.data.dto.WeatherStationDTO;
import org.weather.forecast.backend.data.models.AppUser;
import org.weather.forecast.backend.data.models.Measure;
import org.weather.forecast.backend.data.models.WeatherStation;
import org.weather.forecast.backend.data.repositories.AppUserRepository;
import org.weather.forecast.backend.data.repositories.MeasureRepository;
import org.weather.forecast.backend.data.repositories.WeatherStationRepository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;

@RestController
@RequestMapping("/weather")
public class WeatherDataService {
    @Autowired
    private WeatherStationRepository weatherStationRepository;

    @Autowired
    private MeasureRepository measureRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    @GetMapping("/data")
    public String greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return "test "+ name;
    }

    @PostMapping("/station")
    public ResponseEntity<Object> createStation(@RequestBody WeatherStationDTO dto) {
        try {
            AppUser appUser = appUserRepository.findById(Math.toIntExact(dto.appUserId))
                    .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

            WeatherStation station = new WeatherStation(dto.name, Arrays.stream(dto.sensorsList.split(",")).toList(), dto.measures);
            station.setAppUser(appUser);

            weatherStationRepository.save(station);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/measure")
    public ResponseEntity<Object> createMeasure(@RequestBody Measure measure, Authentication authentication) {
        try {
            String appUserName = authentication.getName();
            Long appUserId = appUserRepository.findByUsername(appUserName).getId();
            WeatherStation weatherStation = weatherStationRepository.findByAppUserId(appUserId);
            measure.setWeatherStation(weatherStation);
            measure.setTimestamp(Timestamp.from(Instant.now()));
            measureRepository.save(measure);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
