package cc.altius.FASP;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@OpenAPIDefinition(
            info = @Info(
                    title = "Quantification and Analytics Tool",
                    description = "API's to access the QAT Server",
                    license = @License(name = "Apache 2.0", url = "https://foo.bar"),
                    contact = @Contact(url = "https://www.quantificationanalytics.org", name = "FASP team", email = "HSS_FASP_HQ@ghsc-psm.org")
            ), servers = @Server(url = "https://www.quantificationanalytics.org", description = "Production server for QAT")
    )
@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = {"cc.altius.FASP"})
public class WebApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

}
