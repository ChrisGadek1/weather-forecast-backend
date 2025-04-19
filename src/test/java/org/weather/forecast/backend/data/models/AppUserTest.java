package org.weather.forecast.backend.data.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AppUserTest {

    private AppUser appUser;

    @BeforeEach
    public void setUp() {
        appUser = new AppUser("testUser", "password123", "ROLE_WEATHER_STATION");
    }

    @Test
    public void testAppUserCreation() {
        assertNotNull(appUser);
        assertEquals("testUser", appUser.getUsername());
        assertEquals("password123", appUser.getPassword());
        assertEquals("ROLE_WEATHER_STATION", appUser.getRole());
    }

    @Test
    public void testGetAuthorities() {
        Collection<? extends GrantedAuthority> authorities = appUser.getAuthorities();
        assertNotNull(authorities);
        assertEquals(1, authorities.size());
        assertTrue(authorities.contains(new SimpleGrantedAuthority("ROLE_WEATHER_STATION")));
    }

    @Test
    public void testAppUserDefaultConstructor() {
        AppUser defaultUser = new AppUser();
        assertNotNull(defaultUser);
        assertNull(defaultUser.getUsername());
        assertNull(defaultUser.getPassword());
        assertNull(defaultUser.getRole());
    }

    @Test
    public void testAppUserWeatherStation() {
        WeatherStation station = new WeatherStation("Station 1", List.of(), List.of());
        appUser.setWeatherStation(station);
        assertEquals(station, appUser.getWeatherStation());
    }

    @Test
    public void testAppUserIsAccountNonExpired() {
        assertTrue(appUser.isAccountNonExpired());
    }

    @Test
    public void testAppUserIsAccountNonLocked() {
        assertTrue(appUser.isAccountNonLocked());
    }

    @Test
    public void testAppUserIsCredentialsNonExpired() {
        assertTrue(appUser.isCredentialsNonExpired());
    }

    @Test
    public void testAppUserIsEnabled() {
        assertTrue(appUser.isEnabled());
    }
}

