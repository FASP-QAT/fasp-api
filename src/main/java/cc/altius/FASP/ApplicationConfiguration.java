/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

/**
 *
 * @author altius
 */
@Configuration
public class ApplicationConfiguration {

    @Bean
    public PropertiesFactoryBean versionProperties() {
        PropertiesFactoryBean bean = new PropertiesFactoryBean();
        bean.setLocation(new ClassPathResource("application.properties"));
        bean.setLocation(new ClassPathResource("fasp.properties"));
        bean.setLocation(new ClassPathResource("version.properties"));
        bean.setLocation(new FileSystemResource("/home/altius/QAT/scheduler.properties"));
        return bean;
    }
}
