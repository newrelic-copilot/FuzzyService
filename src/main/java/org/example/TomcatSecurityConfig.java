package org.example;

import org.apache.coyote.ProtocolHandler;
import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TomcatSecurityConfig {

    private static final int MAX_HTTP_HEADER_SIZE = 8 * 1024;
    private static final int MAX_HTTP_HEADER_COUNT = 100;

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> tomcatHeaderLimitsCustomizer() {
        return factory -> factory.addConnectorCustomizers(connector -> {
            connector.setProperty("maxHttpRequestHeaderSize", String.valueOf(MAX_HTTP_HEADER_SIZE));
            connector.setProperty("maxHttpHeaderSize", String.valueOf(MAX_HTTP_HEADER_SIZE));
            connector.setProperty("maxHeaderCount", String.valueOf(MAX_HTTP_HEADER_COUNT));

            ProtocolHandler protocolHandler = connector.getProtocolHandler();
            if (protocolHandler instanceof AbstractHttp11Protocol<?>) {
                AbstractHttp11Protocol<?> protocol = (AbstractHttp11Protocol<?>) protocolHandler;
                protocol.setMaxHttpRequestHeaderSize(MAX_HTTP_HEADER_SIZE);
                protocol.setMaxHeaderCount(MAX_HTTP_HEADER_COUNT);
            }
        });
    }
}
