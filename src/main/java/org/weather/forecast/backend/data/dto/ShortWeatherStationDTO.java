package org.weather.forecast.backend.data.dto;

public class ShortWeatherStationDTO {
    private Long id;
    private String name;
    private String sensorList;

    public ShortWeatherStationDTO(Long id, String name, String sensorList) {
        this.id = id;
        this.name = name;
        this.sensorList = sensorList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSensorList() {
        return sensorList;
    }

    public void setSensorList(String sensorList) {
        this.sensorList = sensorList;
    }
}
