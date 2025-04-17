package org.weather.forecast.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.weather.forecast.backend.data.dto.MeasureResponseDTO;

@Service
public class SensorNotificationService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void notifyNewMeasurement(MeasureResponseDTO message) {
        messagingTemplate.convertAndSend("/topic/measurements", message);
    }
}
