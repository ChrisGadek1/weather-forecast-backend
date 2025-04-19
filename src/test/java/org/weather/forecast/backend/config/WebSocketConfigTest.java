package org.weather.forecast.backend.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.StompWebSocketEndpointRegistration;

import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {"spring.config.location=classpath:/application-test.properties"})
@AutoConfigureMockMvc
class WebSocketConfigTest {

    private final WebSocketConfig config = new WebSocketConfig();

    @Test
    @DisplayName("registerStompEndpoints should register '/ws-weather' with allowed origins")
    void testRegisterStompEndpoints() {
        StompEndpointRegistry registry = mock(StompEndpointRegistry.class);
        StompWebSocketEndpointRegistration registration = mock(StompWebSocketEndpointRegistration.class);

        when(registry.addEndpoint("/ws-weather")).thenReturn(registration);
        when(registration.setAllowedOriginPatterns("*")).thenReturn(registration); // chaining

        config.registerStompEndpoints(registry);

        verify(registry).addEndpoint("/ws-weather");
        verify(registration).setAllowedOriginPatterns("*");
    }

    @Test
    @DisplayName("configureMessageBroker should enable simple broker on '/topic'")
    void testConfigureMessageBroker() {
        MessageBrokerRegistry registry = mock(MessageBrokerRegistry.class);

        // 💡 Don't stub if unnecessary!
        config.configureMessageBroker(registry);

        verify(registry).enableSimpleBroker("/topic");
    }
}
