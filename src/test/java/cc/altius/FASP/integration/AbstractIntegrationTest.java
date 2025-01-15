package cc.altius.FASP.integration;

import cc.altius.FASP.jwt.JwtTokenUtil;
import cc.altius.FASP.model.Language;
import cc.altius.FASP.model.User;
import cc.altius.FASP.model.UserAcl;
import cc.altius.FASP.service.UserService;
import com.google.gson.Gson;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * AbstractIntegrationTest serves as the base class for integration tests in the application.
 * It provides common setup and teardown logic for tests, including Testcontainers for
 * database management and a preconfigured MockMvc instance for HTTP request simulation.
 *
 * This class uses:
 * <ul>
 *   <li>Spring Boot's testing framework (@SpringBootTest)</li>
 *   <li>Testcontainers for managing a MySQL database container</li>
 *   <li>DynamicPropertySource for dynamically configuring database connection properties</li>
 *   <li>MockMvc for simulating HTTP requests</li>
 * </ul>
 *
 * Tests extending this class automatically inherit the configured environment and utility methods.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Testcontainers
public abstract class AbstractIntegrationTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext context;

    @Autowired
    protected UserService userService;

    @Autowired
    protected JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.http.request.header}")
    protected String tokenHeader;
    
    /**
     * Starts the MySQL container and initializes the schema before running tests.
     */
    @BeforeAll
    public static void start() {
        SharedMySQLContainer.getInstance();
    }

    /**
     * Dynamically sets Spring datasource properties for the MySQL container.
     *
     * @param registry Dynamic property registry used to add properties.
     */
    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        SharedMySQLContainer mysql = SharedMySQLContainer.getInstance();
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
        registry.add("spring.datasource.driver-class-name", mysql::getDriverClassName);
    }

    @PostConstruct
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    /**
     * Converts an object to its JSON representation.
     *
     * @param obj Object to be converted.
     * @return JSON string representation of the object.
     */
    protected static String asJsonString(final Object obj) {
        return new Gson().toJson(obj);
    }

    protected User getUser() {
        User user = new User();
        user.setUserId(1);
        user.setUsername("Alexio");
        Language language = new Language();
        language.setLanguageId(1);
        user.setLanguage(language);
        user.setEmailId("alexiodanje@gmail.com");
        UserAcl acl = new UserAcl();
        acl.setRoleId("ROLE_APPLICATION_ADMIN");
        user.setUserAcls(new UserAcl[]{acl});
        return user;
    }
}
