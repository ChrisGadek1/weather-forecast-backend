package org.weather.forecast.backend.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.weather.forecast.backend.data.dto.MeasureResponseDTO;
import org.weather.forecast.backend.data.dto.ShortWeatherStationDTO;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class SensorNotificationServiceTest {

    private SimpMessagingTemplate messagingTemplate;
    private SensorNotificationService sensorNotificationService;

    @BeforeEach
    public void setup() {
        messagingTemplate = mock(SimpMessagingTemplate.class);
        sensorNotificationService = new SensorNotificationService();
        injectMessagingTemplate(sensorNotificationService, messagingTemplate);
    }

    @Test
    public void testNotifyNewMeasurement() {
        // Given
        ShortWeatherStationDTO station = new ShortWeatherStationDTO(1L, "Test Station");
        MeasureResponseDTO message = new MeasureResponseDTO("Temperature", 22.5f, new Timestamp(System.currentTimeMillis()), "Celsius", station.getName(), station.getId());

        // When
        sensorNotificationService.notifyNewMeasurement(message);

        // Then
        ArgumentCaptor<String> destinationCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<MeasureResponseDTO> messageCaptor = ArgumentCaptor.forClass(MeasureResponseDTO.class);

        verify(messagingTemplate, times(1)).convertAndSend(destinationCaptor.capture(), messageCaptor.capture());

        assertEquals("/topic/measurements", destinationCaptor.getValue());
        assertEquals(message, messageCaptor.getValue());
    }

    private void injectMessagingTemplate(SensorNotificationService service, SimpMessagingTemplate mock) {
        try {
            var field = SensorNotificationService.class.getDeclaredField("messagingTemplate");
            field.setAccessible(true);
            field.set(service, mock);
        } catch (Exception e) {
            throw new RuntimeException("Failed to inject mock SimpMessagingTemplate", e);
        }
    }
}

