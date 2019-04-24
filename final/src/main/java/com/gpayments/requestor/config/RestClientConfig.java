package com.gpayments.requestor.config;

import javax.net.ssl.SSLContext;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestClientConfig {
    private final String CERTIFICATE_PASSWORD = "123456";

    @Bean
    public RestTemplate restTemplate() throws Exception {
        SSLContext sslContext = SSLContextBuilder
                .create()
                .loadKeyMaterial(ResourceUtils.getURL("classpath:certs/client_certificate.p12"), CERTIFICATE_PASSWORD.toCharArray(), CERTIFICATE_PASSWORD.toCharArray())
                .loadTrustMaterial(ResourceUtils.getURL("classpath:certs/cacerts.jks"), CERTIFICATE_PASSWORD.toCharArray())
                .build();

        CloseableHttpClient client = HttpClients.custom()
                .setSSLContext(sslContext)
                .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                .build();
        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory(client);

        return new RestTemplate(httpRequestFactory);
    }
}

