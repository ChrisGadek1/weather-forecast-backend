package org.weather.forecast.backend.config;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.weather.forecast.backend.data.models.AppUser;
import org.weather.forecast.backend.data.repositories.AppUserRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Component
public class PostConstructionConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(PostConstructionConfiguration.class);

    @Autowired
    private Environment environment;

    @Autowired
    private AppUserRepository appUserRepository;

    @PostConstruct
    public void init() {
        AppUser foundAppUser = appUserRepository.findByRole("ROLE_ADMIN");
        if(foundAppUser == null) {
            String generatedPassword = this.generateRandomPassword(30);

            AppUser newAdmin = new AppUser("admin", new BCryptPasswordEncoder().encode(generatedPassword), "ROLE_ADMIN");
            appUserRepository.save(newAdmin);
            saveToFile(".admin_credentials", List.of("# ADMIN CREDENTIALS (DELETE THIS RIGHT AFTER CREATION)", "username: admin", "password: "+generatedPassword));
            logger.info("No admin user - created new");
        }
    }

    private String generateRandomPassword(int length) {
        Set<Character> forbiddenChars = Set.of('`', '@', '\'', '\"', '/', '\\', '$', '%');
        char[] randomChars = new char[length];

        for(int i = 0; i < length; i++) {
            while (true) {
                int randomInt = new Random().nextInt(93) + 33;
                if(forbiddenChars.contains((char) randomInt)) {
                    continue;
                }
                randomChars[i] = (char) randomInt;
                break;
            }

        }

        return String.valueOf(randomChars);
    }

    private void saveToFile(String path, List<String> content) {
        Path totalPath = Path.of(System.getProperty("user.dir") + "/" + path);

        if(!Files.exists(totalPath)) {
            try {
                Files.createFile(totalPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            Files.write(totalPath, content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
