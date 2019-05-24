package com.gpayments.requestor.config;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import javax.net.ssl.SSLContext;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestClientConfig {

  private static final String KEYSTORE_PASSWORD = "123456";
  private static final String CA_CERTS_FILE_NAME = "certs/cacerts.jks";
  private static final String CLIENT_CERTS_FILE_NAME = "certs/client_certificate.p12";

  @Bean
  public RestTemplate restTemplate()
      throws IOException, UnrecoverableKeyException, CertificateException, NoSuchAlgorithmException,
      KeyStoreException, KeyManagementException {
    SSLContext sslContext =
        SSLContextBuilder.create()
            .loadKeyMaterial(
                new ClassPathResource(CLIENT_CERTS_FILE_NAME).getURL(),
                KEYSTORE_PASSWORD.toCharArray(),
                KEYSTORE_PASSWORD.toCharArray())
            .loadTrustMaterial(
                new ClassPathResource(CA_CERTS_FILE_NAME).getURL(), KEYSTORE_PASSWORD.toCharArray())
            .build();

    CloseableHttpClient client =
        HttpClients.custom()
            .setSSLContext(sslContext)
            .build();
    HttpComponentsClientHttpRequestFactory httpRequestFactory =
        new HttpComponentsClientHttpRequestFactory(client);

    return new RestTemplate(httpRequestFactory);
  }
}

