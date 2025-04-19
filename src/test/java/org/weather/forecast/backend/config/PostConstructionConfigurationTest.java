package org.weather.forecast.backend.config;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.core.env.Environment;
import org.weather.forecast.backend.data.models.AppUser;
import org.weather.forecast.backend.data.repositories.AppUserRepository;

import static org.mockito.Mockito.*;

public class PostConstructionConfigurationTest {
    @Mock
    private Environment environment;

    @Mock
    private AppUserRepository appUserRepository;

    @InjectMocks
    @Spy
    private PostConstructionConfiguration postConstructionConfiguration;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldNotCreateAdminUserIfExists() {
        // Given
        AppUser existingAdmin = new AppUser("admin", "hashedPassword", "ROLE_ADMIN");
        when(appUserRepository.findByRole("ROLE_ADMIN")).thenReturn(existingAdmin);

        // When
        postConstructionConfiguration.init();

        // Then
        verify(appUserRepository, never()).save(any());
    }

    @Test
    void shouldCreateAdminUserIfNotExists() {
        // Given
        when(appUserRepository.findByRole("ROLE_ADMIN")).thenReturn(null);

        // We stub saveToFile to prevent actual file writing
        doNothing().when(postConstructionConfiguration).saveToFile(anyString(), any());

        ArgumentCaptor<AppUser> userCaptor = ArgumentCaptor.forClass(AppUser.class);

        // When
        postConstructionConfiguration.init();

        // Then
        verify(appUserRepository).save(userCaptor.capture());
        AppUser savedUser = userCaptor.getValue();
        assert savedUser.getUsername().equals("admin");
        assert savedUser.getRole().equals("ROLE_ADMIN");

        verify(postConstructionConfiguration).saveToFile(eq(".admin_credentials"), any());
    }

    @Test
    void generatedPasswordShouldAvoidForbiddenCharacters() {
        String password = postConstructionConfiguration.generateRandomPassword(100);
        String forbidden = "`@'\"/\\$%";
        for (char c : forbidden.toCharArray()) {
            assert !password.contains(String.valueOf(c));
        }
    }
}
