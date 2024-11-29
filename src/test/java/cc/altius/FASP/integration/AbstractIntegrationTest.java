package cc.altius.FASP.integration;

import cc.altius.FASP.exception.AccessControlFailedException;
import cc.altius.FASP.jwt.JwtTokenUtil;
import cc.altius.FASP.model.*;
import cc.altius.FASP.service.UserService;
import com.google.gson.Gson;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @PostConstruct
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }
    @Container
    private static final MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0")
            .withDatabaseName("fasp")
            .withUsername("root")
            .withPassword("root")
            //This is the better way of loading the script but it has to be clean without comments inside and version specific
            //.withInitScript("schema.sql")
            .waitingFor(Wait.forListeningPort());

    @BeforeAll
    public static void start() {
        mysql.start();
        try (Connection connection = DriverManager.getConnection(
                mysql.getJdbcUrl(),
                mysql.getUsername(),
                mysql.getPassword())) {
            String schemaSql = Files.readString(Path.of("src/main/resources/db/changelog/schema.sql"));
            try (Statement statement = connection.createStatement()) {
                for (String sql : schemaSql.split(";")) {
                    if (!sql.trim().isEmpty()) {
                        statement.execute(sql.trim());
                    }
                }
            }
            try (Statement statement = connection.createStatement()) {
                String setSqlMode = "SET GLOBAL sql_mode=(SELECT REPLACE(@@sql_mode, 'ONLY_FULL_GROUP_BY', ''))";
                statement.execute(setSqlMode);
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }

    }

    @AfterAll
    public static void stop() {
        mysql.stop();
    }

    @DynamicPropertySource
    static void dynamicProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
        registry.add("spring.datasource.driver-class-name", mysql::getDriverClassName);

    }

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
