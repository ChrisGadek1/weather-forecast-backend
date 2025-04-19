package org.weather.forecast.backend.data.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.weather.forecast.backend.data.models.AppUser;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {"spring.config.location=classpath:/application-test.properties"})
@AutoConfigureMockMvc
public class AppUserRepositoryTest {

    @Autowired
    private AppUserRepository appUserRepository;

    private AppUser appUser;

    @BeforeEach
    public void setUp() {
        appUserRepository.deleteAll();
        appUser = new AppUser("testUser", "testPassword", "ROLE_USER");
        appUserRepository.save(appUser);
    }

    @Test
    public void testFindByUsername() {
        // Find user by username
        AppUser foundUser = appUserRepository.findByUsername("testUser");

        assertNotNull(foundUser);
        assertEquals("testUser", foundUser.getUsername());
        assertEquals("testPassword", foundUser.getPassword());
        assertEquals("ROLE_USER", foundUser.getRole());
    }

    @Test
    public void testFindByRole() {
        // Find user by role
        AppUser foundUser = appUserRepository.findByRole("ROLE_USER");

        assertNotNull(foundUser);
        assertEquals("testUser", foundUser.getUsername());
        assertEquals("testPassword", foundUser.getPassword());
        assertEquals("ROLE_USER", foundUser.getRole());
    }

    @Test
    public void testFindByUsernameNotFound() {
        // Try finding a user that does not exist
        AppUser foundUser = appUserRepository.findByUsername("nonExistentUser");

        assertNull(foundUser);
    }

    @Test
    public void testSaveAppUser() {
        // Create a new AppUser and save it
        AppUser newUser = new AppUser("newUser", "newPassword", "ROLE_ADMIN");
        AppUser savedUser = appUserRepository.save(newUser);

        assertNotNull(savedUser);
        assertNotNull(savedUser.getId()); // ID should be generated
        assertEquals("newUser", savedUser.getUsername());
        assertEquals("newPassword", savedUser.getPassword());
        assertEquals("ROLE_ADMIN", savedUser.getRole());
    }

    @Test
    public void testDeleteAppUser() {
        // Delete the appUser created in the setup
        appUserRepository.delete(appUser);

        // Verify that the appUser is deleted
        AppUser deletedUser = appUserRepository.findByUsername("testUser");
        assertNull(deletedUser);
    }

    @Test
    public void testFindAll() {
        // Create another user to ensure the test works with multiple users
        AppUser secondUser = new AppUser("secondUser", "secondPassword", "ROLE_USER");
        appUserRepository.save(secondUser);

        Iterable<AppUser> users = appUserRepository.findAll();
        assertNotNull(users);
        assertTrue(users.spliterator().getExactSizeIfKnown() > 0);
    }
}
