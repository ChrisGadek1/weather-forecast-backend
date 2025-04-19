package org.weather.forecast.backend.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.weather.forecast.backend.data.models.AppUser;
import org.weather.forecast.backend.data.repositories.AppUserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserDatabaseServiceTest {

    private AppUserRepository appUserRepository;
    private UserDatabaseService userDatabaseService;

    @BeforeEach
    void setUp() {
        appUserRepository = mock(AppUserRepository.class);
        userDatabaseService = new UserDatabaseService();

        // Inject mock using reflection since @Autowired is used
        try {
            var field = UserDatabaseService.class.getDeclaredField("appUserRepository");
            field.setAccessible(true);
            field.set(userDatabaseService, appUserRepository);
        } catch (Exception e) {
            throw new RuntimeException("Failed to inject mock repository", e);
        }
    }

    @Test
    void loadUserByUsername_ReturnsUser_WhenUserExists() {
        // Given
        String username = "testUser";
        AppUser mockUser = new AppUser(username, "password", "ROLE_USER");
        when(appUserRepository.findByUsername(username)).thenReturn(mockUser);

        // When
        UserDetails userDetails = userDatabaseService.loadUserByUsername(username);

        // Then
        assertNotNull(userDetails);
        assertEquals(username, userDetails.getUsername());
        verify(appUserRepository, times(1)).findByUsername(username);
    }

    @Test
    void loadUserByUsername_ThrowsException_WhenUserNotFound() {
        // Given
        String username = "nonexistentUser";
        when(appUserRepository.findByUsername(username)).thenReturn(null);

        // When / Then
        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () ->
                userDatabaseService.loadUserByUsername(username));
        assertEquals("username is empty", exception.getMessage());
        verify(appUserRepository, times(1)).findByUsername(username);
    }
}

