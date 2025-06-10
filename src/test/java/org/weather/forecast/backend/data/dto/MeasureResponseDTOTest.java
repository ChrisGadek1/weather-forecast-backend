package org.weather.forecast.backend.data.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;
import java.time.Instant;

public class MeasureResponseDTOTest {

    @Test
    void testMeasureResponseDTOConstructorAndGetters() {
        String measuredQuantityName = "Temperature";
        Float value = 23.5f;
        Timestamp timestamp = Timestamp.from(Instant.now());
        String unit = "Celsius";
        String weatherStationName = "Station1";
        Long weatherStationId = 1L;

        MeasureResponseDTO measureResponseDTO = new MeasureResponseDTO(
                measuredQuantityName, value, timestamp, unit, weatherStationName, weatherStationId, 1L
        );

        assertEquals(measuredQuantityName, measureResponseDTO.getMeasuredQuantityName());
        assertEquals(value, measureResponseDTO.getValue());
        assertEquals(timestamp, measureResponseDTO.getTimestamp());
        assertEquals(unit, measureResponseDTO.getUnit());
        assertNotNull(measureResponseDTO.getShortWeatherStationDTO());

        ShortWeatherStationDTO shortWeatherStationDTO = measureResponseDTO.getShortWeatherStationDTO();
        assertEquals(weatherStationId, shortWeatherStationDTO.getId());
        assertEquals(weatherStationName, shortWeatherStationDTO.getName());
    }

    @Test
    void testMeasureResponseDTOWithNullValues() {
        MeasureResponseDTO measureResponseDTO = new MeasureResponseDTO(
                null, null, null, null, null, null, null
        );

        assertNull(measureResponseDTO.getMeasuredQuantityName());
        assertNull(measureResponseDTO.getValue());
        assertNull(measureResponseDTO.getTimestamp());
        assertNull(measureResponseDTO.getUnit());
        assertNotNull(measureResponseDTO.getShortWeatherStationDTO());
        assertNull(measureResponseDTO.getShortWeatherStationDTO().getId());
        assertNull(measureResponseDTO.getShortWeatherStationDTO().getName());
    }
}

