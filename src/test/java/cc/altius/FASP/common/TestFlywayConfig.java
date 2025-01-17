package cc.altius.FASP.integration;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.flywaydb.core.Flyway;

import javax.sql.DataSource;

@Configuration
@Profile("test")
public class TestFlywayConfig {

    private static final Logger log = LoggerFactory.getLogger(TestFlywayConfig.class);

    @Bean(initMethod = "migrate")
    public Flyway flyway(DataSource dataSource) {
        return Flyway.configure()
            .dataSource(dataSource)
            .baselineOnMigrate(true)
            .baselineVersion("0")
            .locations("classpath:db/migration", "classpath:db/testdata")
            .cleanDisabled(false)
            .load();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void migrateAndSeed(ApplicationReadyEvent event) {
        log.info("Starting Flyway migration for tests...");
        try {
            // migrate
            Flyway flyway = event.getApplicationContext().getBean(Flyway.class);
            flyway.migrate();
            log.info("Flyway migration completed successfully");
        } catch (Exception e) {
            log.error("Flyway migration failed", e);
            throw e;
        }
    }
}
