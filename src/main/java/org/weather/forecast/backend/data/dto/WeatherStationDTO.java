package org.weather.forecast.backend.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WeatherStationDTO {
    public String name;

    @JsonProperty("sensors_list")
    public String sensorsList;

    @JsonProperty("app_user")
    public WeatherStationAppUserDTO weatherStationAppUserDTO;
}
