package org.weather.forecast.backend.data.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ShortWeatherStationDTOTest {

    @Test
    void testShortWeatherStationDTOConstructorAndGetters() {
        Long id = 1L;
        String name = "Station1";

        ShortWeatherStationDTO shortWeatherStationDTO = new ShortWeatherStationDTO(id, name);

        assertEquals(id, shortWeatherStationDTO.getId());
        assertEquals(name, shortWeatherStationDTO.getName());
    }

    @Test
    void testShortWeatherStationDTOSetters() {
        Long id = 1L;
        String name = "Station1";

        ShortWeatherStationDTO shortWeatherStationDTO = new ShortWeatherStationDTO(id, name);

        Long newId = 2L;
        String newName = "Station2";
        shortWeatherStationDTO.setId(newId);
        shortWeatherStationDTO.setName(newName);

        assertEquals(newId, shortWeatherStationDTO.getId());
        assertEquals(newName, shortWeatherStationDTO.getName());
    }

    @Test
    void testShortWeatherStationDTOWithNullValues() {
        ShortWeatherStationDTO shortWeatherStationDTO = new ShortWeatherStationDTO(null, null);

        assertNull(shortWeatherStationDTO.getId());
        assertNull(shortWeatherStationDTO.getName());
    }
}

