package org.weather.forecast.backend.data.dto;

public class WeatherStationAppUserDTO {
    private String username;

    private String password;

    public WeatherStationAppUserDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public WeatherStationAppUserDTO() {
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
