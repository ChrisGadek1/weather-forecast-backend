package org.weather.forecast.backend.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.weather.forecast.backend.services.UserDatabaseService;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {"spring.config.location=classpath:/application-test.properties"})
@AutoConfigureMockMvc
class WebSecurityConfigTest {

    @LocalServerPort
    private int port;

    private WebSocketStompClient stompClient;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserDatabaseService userDatabaseService = mock(UserDatabaseService.class);

    @MockitoBean
    private PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);

    @BeforeEach
    void setup() {
        stompClient = new WebSocketStompClient(new StandardWebSocketClient());
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
    }

    @Test
    @DisplayName("GET /weather/measure is publicly accessible")
    void testWeatherMeasurePublicGet() throws Exception {
        mockMvc.perform(get("/weather/measure"))
                .andExpect(status().isOk()); // if controller exists
    }

    @Test
    @DisplayName("GET /weather/station is publicly accessible")
    void testWeatherStationPublicGet() throws Exception {
        mockMvc.perform(get("/weather/station"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("'/ws-weather' is publicly accessible")
    void connectToWebSocketEndpoint() throws Exception {
        StompSession session = stompClient
                .connectAsync("ws://localhost:" + port + "/ws-weather", new StompSessionHandlerAdapter() {})
                .get(1, TimeUnit.SECONDS);

        assertNotNull(session);
        assertTrue(session.isConnected());
    }

    @Test
    @DisplayName("POST /weather/measure requires WEATHER_STATION role")
    void testWeatherMeasurePostProtected() throws Exception {
        mockMvc.perform(post("/weather/measure"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("GET /admin-only requires ADMIN role")
    void testAdminOnlyRequiresAdminRole() throws Exception {
        mockMvc.perform(get("/admin-only"))
                .andExpect(status().isUnauthorized());
    }
}
