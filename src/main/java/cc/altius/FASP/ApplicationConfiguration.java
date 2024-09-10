/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP;

import java.io.IOException;
import java.util.Properties;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
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

    public ApplicationConfiguration() throws IOException {
        PropertySourcesPlaceholderConfigurer pspc = new PropertySourcesPlaceholderConfigurer();
        PropertiesFactoryBean bean = new PropertiesFactoryBean();
        bean.setLocation(new ClassPathResource("application.properties"));
        bean.afterPropertiesSet();
        Properties props = bean.getObject();
        QAT_HOME = props.getProperty("qat.homeFolder");
    }

    @Bean(name = "scheduler")
    public PropertiesFactoryBean schedulerProperties() {
        PropertiesFactoryBean bean = new PropertiesFactoryBean();
        bean.setLocation(new FileSystemResource(QAT_HOME + "/properties/scheduler.properties"));
        return bean;
    }

    @Bean(name = "credentials")
    public PropertiesFactoryBean credentialsProperties() {
        PropertiesFactoryBean bean = new PropertiesFactoryBean();
        bean.setLocation(new FileSystemResource(QAT_HOME + "/properties/credentials.properties"));
        return bean;
    }

    @Bean
    public PropertySourcesPlaceholderConfigurer properties() {
        PropertySourcesPlaceholderConfigurer pspc = new PropertySourcesPlaceholderConfigurer();
        Resource[] resources = new Resource[]{
            new ClassPathResource("application.properties"),
            new FileSystemResource(QAT_HOME + "/properties/qat.properties"),
            new ClassPathResource("version.properties")};
        pspc.setLocations(resources);
        pspc.setIgnoreUnresolvablePlaceholders(true);
        return pspc;
    }

}
