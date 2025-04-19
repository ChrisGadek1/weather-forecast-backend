package org.weather.forecast.backend.data.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WeatherStationDTOTest {

    @Test
    void testWeatherStationDTOConstructorAndGetters() {
        String name = "Station A";
        String sensorsList = "sensor1,sensor2,sensor3";
        WeatherStationAppUserDTO appUserDTO = new WeatherStationAppUserDTO("user1", "password123");

        WeatherStationDTO stationDTO = new WeatherStationDTO(name, sensorsList, appUserDTO);

        assertEquals(name, stationDTO.name);
        assertEquals(sensorsList, stationDTO.sensorsList);
        assertEquals(appUserDTO, stationDTO.weatherStationAppUserDTO);
    }

    @Test
    void testWeatherStationDTODefaultConstructor() {
        WeatherStationDTO stationDTO = new WeatherStationDTO();

        assertNull(stationDTO.name);
        assertNull(stationDTO.sensorsList);
        assertNull(stationDTO.weatherStationAppUserDTO);
    }

    @Test
    void testWeatherStationDTOWithNullValues() {
        WeatherStationDTO stationDTO = new WeatherStationDTO(null, null, null);

        assertNull(stationDTO.name);
        assertNull(stationDTO.sensorsList);
        assertNull(stationDTO.weatherStationAppUserDTO);
    }

    @Test
    void testWeatherStationDTOJsonSerialization() throws Exception {
        String name = "Station A";
        String sensorsList = "sensor1,sensor2,sensor3";
        WeatherStationAppUserDTO appUserDTO = new WeatherStationAppUserDTO("user1", "password123");

        WeatherStationDTO stationDTO = new WeatherStationDTO(name, sensorsList, appUserDTO);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(stationDTO);

        assertTrue(json.contains("\"name\":\"Station A\""));
        assertTrue(json.contains("\"sensors_list\":\"sensor1,sensor2,sensor3\""));
        assertTrue(json.contains("\"app_user\":{\"username\":\"user1\",\"password\":\"password123\"}"));
    }

    @Test
    void testWeatherStationDTOJsonDeserialization() throws Exception {
        String json = "{\"name\":\"Station A\",\"sensors_list\":\"sensor1,sensor2,sensor3\",\"app_user\":{\"username\":\"user1\",\"password\":\"password123\"}}";

        ObjectMapper objectMapper = new ObjectMapper();
        WeatherStationDTO stationDTO = objectMapper.readValue(json, WeatherStationDTO.class);

        assertEquals("Station A", stationDTO.name);
        assertEquals("sensor1,sensor2,sensor3", stationDTO.sensorsList);
        assertEquals("user1", stationDTO.weatherStationAppUserDTO.getUsername());
        assertEquals("password123", stationDTO.weatherStationAppUserDTO.getPassword());
    }
}

