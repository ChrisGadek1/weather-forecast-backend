package org.weather.forecast.backend.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.weather.forecast.backend.data.models.Measure;

import java.util.List;

public class WeatherStationDTO {
    public String name;

    @JsonProperty("sensors_list")
    public String sensorsList;

    public List<Measure> measures;

    @JsonProperty("app_user_id")
    public Long appUserId;
}
