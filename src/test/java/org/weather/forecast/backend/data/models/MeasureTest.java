package org.weather.forecast.backend.data.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MeasureTest {

    private Measure measure;

    @BeforeEach
    public void setUp() {
        measure = new Measure("Temperature", 25.5f, "Celsius");
    }

    @Test
    public void testMeasureCreation() {
        assertNotNull(measure);
        assertEquals("Temperature", measure.getMeasuredQuantityName());
        assertEquals(25.5f, measure.getValue());
        assertEquals("Celsius", measure.getUnit());
        assertNotNull(measure.getTimestamp()); // timestamp should be set to current time
    }

    @Test
    public void testMeasureDefaultConstructor() {
        Measure defaultMeasure = new Measure();
        assertNotNull(defaultMeasure);
        assertNull(defaultMeasure.getMeasuredQuantityName());
        assertNull(defaultMeasure.getValue());
        assertNull(defaultMeasure.getUnit());
        assertNull(defaultMeasure.getTimestamp());
    }

    @Test
    public void testSetWeatherStation() {
        WeatherStation weatherStation = new WeatherStation("Station 1", List.of(), List.of());
        measure.setWeatherStation(weatherStation);
        assertEquals(weatherStation, measure.getWeatherStation());
    }

    @Test
    public void testSetTimestamp() {
        Timestamp customTimestamp = Timestamp.from(Instant.parse("2025-04-19T00:00:00Z"));
        measure.setTimestamp(customTimestamp);
        assertEquals(customTimestamp, measure.getTimestamp());
    }
}

