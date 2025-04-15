package org.weather.forecast.backend.data.dto;

import java.sql.Timestamp;

public class MeasureResponseDTO {
    private String measuredQuantityName;
    private Double value;
    private Timestamp timestamp;
    private String unit;
    private ShortWeatherStationDTO shortWeatherStationDTO;

    public MeasureResponseDTO(String measuredQuantityName, Double value, Timestamp timestamp, String unit, String weatherStationName, Long weatherStationId) {
        this.measuredQuantityName = measuredQuantityName;
        this.value = value;
        this.timestamp = timestamp;
        this.unit = unit;
        this.shortWeatherStationDTO = new ShortWeatherStationDTO(weatherStationId, weatherStationName);
    }

    public String getMeasuredQuantityName() {
        return measuredQuantityName;
    }

    public Double getValue() {
        return value;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public String getUnit() {
        return unit;
    }

    public ShortWeatherStationDTO getShortWeatherStationDTO() {
        return shortWeatherStationDTO;
    }
}
