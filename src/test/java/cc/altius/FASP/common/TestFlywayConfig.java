package cc.altius.FASP.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.flywaydb.core.Flyway;

import javax.sql.DataSource;

@Configuration
@Profile("test")
public class TestFlywayConfig {

    @Bean
    public Flyway flyway(DataSource dataSource) {
        return Flyway.configure()
            .dataSource(dataSource)
            .load();
    }
}
