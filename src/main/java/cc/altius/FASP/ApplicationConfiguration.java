/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP;

import java.io.IOException;
import java.util.Properties;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
/**
 *
 * @author altius
 */
@Configuration
public class ApplicationConfiguration {

    private String QAT_HOME;

    @Bean
    public String qatHome(Environment environment) throws IOException {
        if (QAT_HOME == null) {
            String propertiesFile = getPropertiesFile(environment);
            Properties props = new Properties();
            props.load(ApplicationConfiguration.class.getClassLoader().getResourceAsStream(propertiesFile));
            QAT_HOME = props.getProperty("qat.homeFolder");
        }
        return QAT_HOME;
    }

    @Bean(name = "scheduler")
    public PropertiesFactoryBean schedulerProperties(@Autowired String qatHome) {
        PropertiesFactoryBean bean = new PropertiesFactoryBean();
        bean.setLocation(new FileSystemResource(qatHome + "/properties/scheduler.properties"));
        return bean;
    }

    @Bean(name = "credentials")
    public PropertiesFactoryBean credentialsProperties(@Autowired String qatHome) {
        PropertiesFactoryBean bean = new PropertiesFactoryBean();
        bean.setLocation(new FileSystemResource(qatHome + "/properties/credentials.properties"));
        return bean;
    }

    @Bean
    public PropertySourcesPlaceholderConfigurer properties(Environment environment, @Autowired String qatHome) {
        PropertySourcesPlaceholderConfigurer pspc = new PropertySourcesPlaceholderConfigurer();
        Resource[] resources = new Resource[] {
            new ClassPathResource(getPropertiesFile(environment)),
            new FileSystemResource(qatHome + "/properties/qat.properties"),
            new ClassPathResource("version.properties")};
        pspc.setLocations(resources);
        pspc.setIgnoreUnresolvablePlaceholders(true);
        return pspc;
    }

    private String getPropertiesFile(Environment environment) {
        String activeProfile = environment.getActiveProfiles().length > 0 ? 
            environment.getActiveProfiles()[0] : "default";
        return activeProfile.equals("test") ? "application-test.properties" : "application.properties";
    }
}
