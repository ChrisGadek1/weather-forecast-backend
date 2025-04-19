package org.weather.forecast.backend.data.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WeatherStationTest {

    private WeatherStation weatherStation;

    @BeforeEach
    public void setUp() {
        weatherStation = new WeatherStation("Station 1", Arrays.asList("Sensor1", "Sensor2", "Sensor3"), null);
    }

    @Test
    public void testWeatherStationCreation() {
        assertNotNull(weatherStation);
        assertEquals("Station 1", weatherStation.getName());
        assertEquals(3, weatherStation.getSensorsList().size());
        assertTrue(weatherStation.getSensorsList().contains("Sensor1"));
        assertTrue(weatherStation.getSensorsList().contains("Sensor2"));
        assertTrue(weatherStation.getSensorsList().contains("Sensor3"));
    }

    @Test
    public void testWeatherStationDefaultConstructor() {
        WeatherStation defaultWeatherStation = new WeatherStation();
        assertNotNull(defaultWeatherStation);
        assertNull(defaultWeatherStation.getName());
        assertTrue(defaultWeatherStation.getSensorsList().isEmpty());
        assertNull(defaultWeatherStation.getMeasures());
        assertNull(defaultWeatherStation.getAppUser());
    }

    @Test
    public void testSetAppUser() {
        AppUser appUser = new AppUser("testUser", "password", "ROLE_WEATHER_STATION");
        weatherStation.setAppUser(appUser);
        assertEquals(appUser, weatherStation.getAppUser());
    }

    @Test
    public void testSetRawStationSensorList() {
        weatherStation.setRawStationSensorList("SensorA,SensorB,SensorC");
        assertEquals(3, weatherStation.getSensorsList().size());
        assertTrue(weatherStation.getSensorsList().contains("SensorA"));
        assertTrue(weatherStation.getSensorsList().contains("SensorB"));
        assertTrue(weatherStation.getSensorsList().contains("SensorC"));
    }
}

