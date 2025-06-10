package org.weather.forecast.backend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.weather.forecast.backend.data.dto.*;
import org.weather.forecast.backend.data.models.*;
import org.weather.forecast.backend.data.repositories.*;
import org.weather.forecast.backend.services.SensorNotificationService;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.List.of;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {"spring.config.location=classpath:/application-test.properties"})
@AutoConfigureMockMvc
public class WeatherDataServiceTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private WeatherStationRepository stationRepo;

    @MockitoBean
    private MeasureRepository measureRepo;

    @MockitoBean
    private AppUserRepository userRepo;

    @MockitoBean
    private SensorNotificationService notificationService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("POST /weather/station - success")
    void createStation_Success() throws Exception {
        WeatherStationAppUserDTO userDTO = new WeatherStationAppUserDTO("stationUser", "password");
        WeatherStationDTO stationDTO = new WeatherStationDTO("TestStation", "temperature,humidity", userDTO);

        when(userRepo.findByUsername("stationUser")).thenReturn(null);
        when(stationRepo.findByName("TestStation")).thenReturn(null);

        mockMvc.perform(post("/weather/station")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(stationDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("GET /weather/station - returns list of stations")
    void getStations() throws Exception {
        WeatherStation station = new WeatherStation("Station1", List.of("temp"), emptyList());
        station.setId(1L);

        when(stationRepo.findAll()).thenReturn(List.of(station));

        mockMvc.perform(get("/weather/station"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("DELETE /weather/station/{id} - success")
    void deleteStation() throws Exception {
        AppUser user = new AppUser("stationUser", "pass", "ROLE_WEATHER_STATION");
        WeatherStation station = new WeatherStation("Station1", List.of("temp"), emptyList());
        station.setAppUser(user);

        when(stationRepo.findById(1)).thenReturn(Optional.of(station));

        mockMvc.perform(delete("/weather/station/1"))
                .andExpect(status().isOk());

        verify(measureRepo).deleteAll(any());
        verify(stationRepo).delete(station);
        verify(userRepo).delete(user);
    }

    @Test
    @WithMockUser(username = "stationUser", roles = {"WEATHER_STATION"})
    @DisplayName("POST /weather/measure - success")
    void createMeasure() throws Exception {
        AppUser user = new AppUser("stationUser", "pass", "ROLE_WEATHER_STATION");
        user.setId(1L);
        WeatherStation station = new WeatherStation("Station", List.of("temp"), emptyList());
        station.setId(2L);
        station.setAppUser(user);

        Measure measure = new Measure("temperature", 25.0f, "C");
        when(userRepo.findByUsername("stationUser")).thenReturn(user);
        when(stationRepo.findByAppUserId(1L)).thenReturn(station);

        mockMvc.perform(post("/weather/measure")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(measure)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("GET /weather/measure - range query")
    void getMeasures() throws Exception {
        MeasureResponseDTO dto = new MeasureResponseDTO("temp", 20.0f, Timestamp.from(Instant.now()), "C", "Station", 1L, 1L);
        when(measureRepo.findByTimestamps(any(), any())).thenReturn(List.of(dto));

        mockMvc.perform(get("/weather/measure")
                        .param("from", "2024-04-18 10:00:00")
                        .param("to", "2025-04-18 10:00:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].measuredQuantityName").value("temp"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("DELETE /weather/measure/{id} - success")
    void deleteMeasure() throws Exception {
        Measure measure = new Measure("temp", 25.0f, "C");
        when(measureRepo.findById(5)).thenReturn(Optional.of(measure));

        mockMvc.perform(delete("/weather/measure/5"))
                .andExpect(status().isOk());

        verify(measureRepo).delete(measure);
    }
}
