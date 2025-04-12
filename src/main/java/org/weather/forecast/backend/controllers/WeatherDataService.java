package org.weather.forecast.backend.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weather")
public class WeatherDataService {
    @GetMapping("/data")
    public String greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return "test "+ name;
    }
}
