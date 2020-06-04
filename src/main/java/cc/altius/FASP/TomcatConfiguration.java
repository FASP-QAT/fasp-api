/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.ajp.AbstractAjpProtocol;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;

/**
 *
 * @author akil
 */
@Component
public class TomcatConfiguration implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {

    private static final String PROTOCOL = "AJP/1.3";

    @Value("${tomcat.ajp.port}") //Defined on application.properties
    private int ajpPort;

    @Override
    public void customize(TomcatServletWebServerFactory factory) {
        Connector ajpConnector = new Connector(PROTOCOL);
        ajpConnector.setPort(ajpPort);
        ajpConnector.setSecure(false);
        ((AbstractAjpProtocol) ajpConnector.getProtocolHandler()).setSecretRequired(false);
        factory.addAdditionalTomcatConnectors(ajpConnector);
    }
}
