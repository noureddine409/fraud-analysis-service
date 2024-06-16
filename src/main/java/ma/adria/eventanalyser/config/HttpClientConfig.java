package ma.adria.eventanalyser.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.time.Duration;

@Configuration
public class HttpClientConfig {

    @Value("${http.client.connection-timeout}")
    private int connectionTimeout;
    @Value("${http.client.read-timeout}")
    private int readTimeout;

    /**
     * Configures and provides a customized RestTemplate bean.
     *
     * @return RestTemplate instance with custom configurations
     */
    @Bean
    public RestTemplate restTemplate() {
        // Create a simple ClientHttpRequestFactory
        ClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();

        // Optionally, wrap it with BufferingClientHttpRequestFactory for request/response logging
        requestFactory = new BufferingClientHttpRequestFactory(requestFactory);

        // Create RestTemplate instance
        RestTemplate restTemplate = new RestTemplate(requestFactory);

        // Configure URI builder factory to avoid encoding issues
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory());

        // Customize connection and read timeouts
        restTemplate.setRequestFactory(requestFactoryWithTimeouts());

        return restTemplate;
    }

    /**
     * Configures a ClientHttpRequestFactory with timeouts.
     *
     * @return ClientHttpRequestFactory instance with configured timeouts
     */
    private ClientHttpRequestFactory requestFactoryWithTimeouts() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();

        // Set connection timeout
        requestFactory.setConnectTimeout((int) Duration.ofSeconds(connectionTimeout).toMillis());

        // Set read timeout
        requestFactory.setReadTimeout((int) Duration.ofSeconds(readTimeout).toMillis());

        return requestFactory;
    }
}
