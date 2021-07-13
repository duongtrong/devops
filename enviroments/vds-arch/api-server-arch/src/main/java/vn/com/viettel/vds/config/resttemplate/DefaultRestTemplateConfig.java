package vn.com.viettel.vds.config.resttemplate;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import static vn.com.viettel.vds.util.RestTemplateGenericUtil.getSocketFactoryRegistry;


@Configuration
@RefreshScope
public class DefaultRestTemplateConfig {

    @Bean
    @ConfigurationProperties(prefix = "rest.default.connection")
    public HttpComponentsClientHttpRequestFactory defaultHttpRequestFactory(PoolingHttpClientConnectionManager defaultHttpPoolingConnectionManager) {
        CloseableHttpClient closeableHttpClient = HttpClients.custom()
                .setConnectionManager(defaultHttpPoolingConnectionManager)
                .setConnectionManagerShared(true)
                .build();
        return new HttpComponentsClientHttpRequestFactory(closeableHttpClient);
    }

    @Bean
    public RestTemplate defaultRestTemplate(HttpComponentsClientHttpRequestFactory defaultHttpRequestFactory) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
            public boolean hasError(ClientHttpResponse response) throws IOException {
                HttpStatus.Series series = response.getStatusCode().series();
                return HttpStatus.Series.CLIENT_ERROR.equals(series) || HttpStatus.Series.SERVER_ERROR.equals(series);
            }
        });
        restTemplate.setRequestFactory(defaultHttpRequestFactory);
        return restTemplate;
    }

    @Bean
    @ConfigurationProperties(prefix = "rest.default.connection-pool")
    public PoolingHttpClientConnectionManager defaultHttpPoolingConnectionManager() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        return new PoolingHttpClientConnectionManager(getSocketFactoryRegistry());
    }
}