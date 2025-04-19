package org.weather.forecast.backend.data.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WeatherStationAppUserDTOTest {

    @Test
    void testWeatherStationAppUserDTOConstructorAndGetters() {
        String username = "user1";
        String password = "password123";

        WeatherStationAppUserDTO appUserDTO = new WeatherStationAppUserDTO(username, password);

        assertEquals(username, appUserDTO.getUsername());
        assertEquals(password, appUserDTO.getPassword());
    }

    @Test
    void testWeatherStationAppUserDTOWithNullValues() {
        WeatherStationAppUserDTO appUserDTO = new WeatherStationAppUserDTO(null, null);

        assertNull(appUserDTO.getUsername());
        assertNull(appUserDTO.getPassword());
    }

    @Test
    void testWeatherStationAppUserDTOSetters() {
        String username = "user1";
        String password = "password123";

        WeatherStationAppUserDTO appUserDTO = new WeatherStationAppUserDTO(username, password);

        String newUsername = "user2";
        String newPassword = "newPassword456";

        WeatherStationAppUserDTO updatedAppUserDTO = new WeatherStationAppUserDTO(newUsername, newPassword);

        assertEquals(newUsername, updatedAppUserDTO.getUsername());
        assertEquals(newPassword, updatedAppUserDTO.getPassword());
    }
}
