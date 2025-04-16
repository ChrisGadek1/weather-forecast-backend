package org.weather.forecast.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.weather.forecast.backend.data.dto.MeasureResponseDTO;
import org.weather.forecast.backend.data.dto.ShortWeatherStationDTO;
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
import java.util.LinkedList;
import java.util.List;

import static java.sql.Timestamp.from;

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

            AppUser appUser = new AppUser(dto.weatherStationAppUserDTO.getUsername(), new BCryptPasswordEncoder().encode(dto.weatherStationAppUserDTO.getPassword()), "ROLE_WEATHER_STATION");
            if(appUserRepository.findByUsername(appUser.getUsername()) != null || weatherStationRepository.findByName(dto.name) != null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            appUserRepository.save(appUser);
            WeatherStation station = new WeatherStation(dto.name, Arrays.stream(dto.sensorsList.split(",")).toList(), new LinkedList<>());
            station.setAppUser(appUser);

            weatherStationRepository.save(station);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/station")
    public ResponseEntity<List<ShortWeatherStationDTO>> getStations() {
        List<ShortWeatherStationDTO> result = new LinkedList<>();
        weatherStationRepository.findAll().iterator().forEachRemaining(station -> {
            ShortWeatherStationDTO shortWeatherStationDTO = new ShortWeatherStationDTO(station.getId(), station.getName());
            result.add(shortWeatherStationDTO);
        });

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/measure")
    public ResponseEntity<Object> createMeasure(@RequestBody Measure measure, Authentication authentication) {
        try {
            String appUserName = authentication.getName();
            Long appUserId = appUserRepository.findByUsername(appUserName).getId();
            WeatherStation weatherStation = weatherStationRepository.findByAppUserId(appUserId);
            measure.setWeatherStation(weatherStation);
            measure.setTimestamp(from(Instant.now()));
            measureRepository.save(measure);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/measure")
    public ResponseEntity<List<MeasureResponseDTO>> getMeasures(@RequestParam(value = "from", defaultValue = "") String from, @RequestParam(value = "to", defaultValue = "") String to, @RequestParam(value = "station_id", defaultValue = "") String stationId) {
        Timestamp fromTimestamp, toTimestamp;
        Long stationIdConv = null;

        try {
            if(from.isEmpty()) {
                fromTimestamp = Timestamp.from(Instant.EPOCH);
            }
            else {
                fromTimestamp = Timestamp.valueOf(from);
            }

            if(to.isEmpty()) {
                toTimestamp = Timestamp.from(Instant.now());
            }
            else {
                toTimestamp = Timestamp.valueOf(to);
            }

            if (fromTimestamp.after(toTimestamp)) {
                throw new IllegalArgumentException();
            }

            if(!stationId.isEmpty()) {
                stationIdConv = Long.parseLong(stationId);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<MeasureResponseDTO> result;

        if(stationIdConv != null) {
            result = measureRepository.findByTimestampsAndStationId(fromTimestamp, toTimestamp, stationIdConv);
        }
        else {
            result = measureRepository.findByTimestamps(fromTimestamp, toTimestamp);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);

    }
}
