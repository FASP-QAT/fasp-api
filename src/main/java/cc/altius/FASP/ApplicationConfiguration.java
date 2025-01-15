package cc.altius.FASP;

import java.io.IOException;
import java.util.Properties;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

/**
 * This class is used to configure the application properties.
 *
 * The main application properties are loaded from the following sources:
 * 1. application.properties (default) OR application-test.properties (test)
 * 2. version.properties (all environments; contains the version of the application)
 *
 * The property qat.homeFolder is loaded from the following sources:
 * 1. QAT_HOMEFOLDER environment variable (in non-test environments)
 * 2. either of the application properties files
 *
 * The property qat.homeFolder is used to load the following properties files:
 * 1. qat.properties (qatHome/properties/qat.properties)
 * 2. scheduler.properties (qatHome/properties/scheduler.properties)
 * 3. credentials.properties (qatHome/properties/credentials.properties)
 *
 * Properties can be autowired into other beans as needed using the bean names
 * "properties", "scheduler", or "credentials". E.g. to inject a scheduler
 * properties into a bean, use:
 * ```
 * private @Value("#{scheduler['schedulerActive']}")
 * String SCHEDULER_ACTIVE;
 * ```
 *
 * Any properties can be overridden by setting the appropriate environment
 * variable. E.g. to override the spring.datasource.url property, set the
 * `SPRING_DATASOURCE_URL` environment variable.
 */
@Configuration
public class ApplicationConfiguration {

    private String QAT_HOME;

    @Bean(name = "qatHome")
    public String qatHome(Environment environment) throws IOException {
        if (QAT_HOME == null) {
            Properties props = new Properties();
            String activeProfile = environment.getActiveProfiles().length > 0 ? environment.getActiveProfiles()[0] : "default";
            if (activeProfile.equals("test")) {
                // In test, only use test properties file
                props.load(ApplicationConfiguration.class.getClassLoader().getResourceAsStream("application-test.properties"));
                QAT_HOME = props.getProperty("qat.homeFolder");
            } else {
                // In other environments, first check for an environment variable
                QAT_HOME = System.getenv("QAT_HOMEFOLDER");
                if (QAT_HOME == null) {
                    // If not found, use the properties file
                    props.load(ApplicationConfiguration.class.getClassLoader().getResourceAsStream("application.properties"));
                    QAT_HOME = props.getProperty("qat.homeFolder");
                }
            }
        }
        return QAT_HOME;
    }

    @Bean(name = "scheduler")
    public PropertiesFactoryBean schedulerProperties(@Autowired String qatHome) {
        PropertiesFactoryBean bean = new PropertiesFactoryBean();
        bean.setLocation(new FileSystemResource(qatHome + "/properties/scheduler.properties"));
        bean.setLocalOverride(true);
        return bean;
    }

    @Bean(name = "credentials")
    public PropertiesFactoryBean credentialsProperties(@Autowired String qatHome) {
        PropertiesFactoryBean bean = new PropertiesFactoryBean();
        bean.setLocation(new FileSystemResource(qatHome + "/properties/credentials.properties"));
        bean.setLocalOverride(true);
        return bean;
    }

    @Bean
    public PropertySourcesPlaceholderConfigurer properties(Environment environment, @Autowired @Qualifier("qatHome") String qatHome) {
        String activeProfile = environment.getActiveProfiles().length > 0 ? environment.getActiveProfiles()[0] : "default";
        String propertiesFile = activeProfile.equals("test") ? "application-test.properties" : "application.properties";
        PropertySourcesPlaceholderConfigurer pspc = new PropertySourcesPlaceholderConfigurer();
        Resource[] resources = new Resource[] {
            new ClassPathResource(propertiesFile),
            new FileSystemResource(qatHome + "/properties/qat.properties"),
            new ClassPathResource("version.properties")};
        pspc.setLocations(resources);
        pspc.setIgnoreUnresolvablePlaceholders(true);
        return pspc;
    }
}
