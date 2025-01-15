package cc.altius.FASP.integration;

import org.testcontainers.containers.MySQLContainer;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TestMySQLContainer extends MySQLContainer<TestMySQLContainer> {
    private static final String IMAGE_VERSION = "mysql:8.0";
    private static TestMySQLContainer container;
    private TestMySQLContainer() {
        super(IMAGE_VERSION);
    }
    public static TestMySQLContainer getInstance() {
        if (container == null) {
            container = new TestMySQLContainer()
                    .withDatabaseName("fasp")
                    .withUsername("root")
                    .withPassword("root")
                    .withUrlParam("useSSL", "false")
                    .withUrlParam("allowPublicKeyRetrieval", "true")
                    .withUrlParam("autoReconnect", "true")
                    .withExposedPorts(3306);
            container.start();
            initializeDatabase(container);
        }
        return container;
    }
    private static void initializeDatabase(MySQLContainer<?> mysql) {
        try (Connection connection = DriverManager.getConnection(
                mysql.getJdbcUrl(),
                mysql.getUsername(),
                mysql.getPassword())) {
            String schemaSql = Files.readString(Path.of("src/test/resources/testdata/sql/schema.sql"));
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
            throw new RuntimeException("Failed to initialize database schema", e);
        }
    }
}
