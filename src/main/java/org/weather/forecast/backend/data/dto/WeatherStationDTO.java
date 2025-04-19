package org.weather.forecast.backend.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WeatherStationDTO {
    public String name;

    @JsonProperty("sensors_list")
    public String sensorsList;

    @JsonProperty("app_user")
    public WeatherStationAppUserDTO weatherStationAppUserDTO;

    public WeatherStationDTO(String name, String sensorsList, WeatherStationAppUserDTO weatherStationAppUserDTO) {
        this.name = name;
        this.sensorsList = sensorsList;
        this.weatherStationAppUserDTO = weatherStationAppUserDTO;
    }

    public WeatherStationDTO() {
    }
}
