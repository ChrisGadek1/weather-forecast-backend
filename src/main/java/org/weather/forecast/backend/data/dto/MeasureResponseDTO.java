package org.weather.forecast.backend.data.dto;

import java.sql.Timestamp;

public class MeasureResponseDTO {
    private String measuredQuantityName;
    private Float value;
    private Timestamp timestamp;
    private String unit;
    private ShortWeatherStationDTO shortWeatherStationDTO;
    private Long id;

    public MeasureResponseDTO(String measuredQuantityName, Float value, Timestamp timestamp, String unit, String weatherStationName, Long weatherStationId, Long id) {
        this.measuredQuantityName = measuredQuantityName;
        this.value = value;
        this.timestamp = timestamp;
        this.unit = unit;
        this.shortWeatherStationDTO = new ShortWeatherStationDTO(weatherStationId, weatherStationName);
        this.id = id;
    }

    public String getMeasuredQuantityName() {
        return measuredQuantityName;
    }

    public Float getValue() {
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

    public Long getId() {
        return id;
    }
}
