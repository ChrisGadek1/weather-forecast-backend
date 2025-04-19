package org.weather.forecast.backend.data.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.weather.forecast.backend.data.dto.MeasureResponseDTO;
import org.weather.forecast.backend.data.models.Measure;
import org.weather.forecast.backend.data.models.WeatherStation;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {"spring.config.location=classpath:/application-test.properties"})
@AutoConfigureMockMvc
public class MeasureRepositoryTest {

    @Autowired
    private MeasureRepository measureRepository;

    @Autowired
    private WeatherStationRepository weatherStationRepository;

    private WeatherStation weatherStation;

    @BeforeEach
    public void setUp() {
        // Clean the repository before each test to ensure data consistency
        measureRepository.deleteAll();
        weatherStationRepository.deleteAll();

        // Setup the WeatherStation for the test
        weatherStation = new WeatherStation("Test Station", List.of("sensor1", "sensor2"), null);
        weatherStationRepository.save(weatherStation);
    }

    @Test
    public void testFindByTimestampsAndStationId() {
        // Create and save test measures
        Measure measure1 = new Measure("Temperature", 25.5f, "Celsius");
        measure1.setWeatherStation(weatherStation);
        measure1.setTimestamp(Timestamp.from(Instant.now().minusSeconds(3600))); // 1 hour ago
        measureRepository.save(measure1);

        Measure measure2 = new Measure("Humidity", 60.0f, "%");
        measure2.setWeatherStation(weatherStation);
        measure2.setTimestamp(Timestamp.from(Instant.now().minusSeconds(1800))); // 30 minutes ago
        measureRepository.save(measure2);

        // Define time range for query (last 45 minutes)
        Timestamp from = Timestamp.from(Instant.now().minusSeconds(2700)); // 45 minutes ago
        Timestamp to = Timestamp.from(Instant.now());

        // Call repository method to retrieve the data
        List<MeasureResponseDTO> result = measureRepository.findByTimestampsAndStationId(from, to, weatherStation.getId());

        // Verify that we get the correct result (only the second measure should be in range)
        assertEquals(1, result.size());
        assertEquals("Humidity", result.get(0).getMeasuredQuantityName());
        assertEquals(60.0f, result.get(0).getValue());
        assertEquals("%", result.get(0).getUnit());
    }

    @Test
    public void testFindByTimestamps() {
        // Create and save test measures
        Measure measure1 = new Measure("Temperature", 25.5f, "Celsius");
        measure1.setWeatherStation(weatherStation);
        measure1.setTimestamp(Timestamp.from(Instant.now().minusSeconds(3600))); // 1 hour ago
        measureRepository.save(measure1);

        Measure measure2 = new Measure("Humidity", 60.0f, "%");
        measure2.setWeatherStation(weatherStation);
        measure2.setTimestamp(Timestamp.from(Instant.now().minusSeconds(1800))); // 30 minutes ago
        measureRepository.save(measure2);

        // Define time range for query (last 45 minutes)
        Timestamp from = Timestamp.from(Instant.now().minusSeconds(2700)); // 45 minutes ago
        Timestamp to = Timestamp.from(Instant.now());

        // Call repository method to retrieve the data
        List<MeasureResponseDTO> result = measureRepository.findByTimestamps(from, to);

        // Verify that we get the correct result (only the second measure should be in range)
        assertEquals(1, result.size());
        assertEquals("Humidity", result.get(0).getMeasuredQuantityName());
        assertEquals(60.0f, result.get(0).getValue());
        assertEquals("%", result.get(0).getUnit());
    }
}
