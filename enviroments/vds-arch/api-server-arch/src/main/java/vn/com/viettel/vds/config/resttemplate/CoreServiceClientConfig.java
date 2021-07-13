package vn.com.viettel.vds.config.resttemplate;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import static vn.com.viettel.vds.util.RestTemplateGenericUtil.getSocketFactoryRegistry;

@Configuration
@Getter
@Slf4j
@RefreshScope
public class CoreServiceClientConfig {

    @Value("${rest.core-service.endpoint}")
    private String endpoint;

    @Value("${rest.core-service.proxy-host:#{null}}")
    private String proxyHost;

    @Value("${rest.core-service.proxy-port:#{null}}")
    private Integer proxyPort;

    @Bean
    @ConfigurationProperties(prefix = "rest.core-service.connection")
    public HttpComponentsClientHttpRequestFactory coreServiceHttpRequestFactory(PoolingHttpClientConnectionManager coreServiceHttpClientConnectionManager) {
        HttpClientBuilder builder  = HttpClients.custom()
                .setConnectionManager(coreServiceHttpClientConnectionManager)
                .setConnectionManagerShared(true);
        if (!StringUtils.isEmpty(proxyHost) && proxyPort != null) {
            builder.setProxy(new HttpHost(proxyHost, proxyPort));
        }
        CloseableHttpClient closeableHttpClient = builder.build();
        return new HttpComponentsClientHttpRequestFactory(closeableHttpClient);
    }

    @Bean
    public RestTemplate coreServiceRestTemplate(HttpComponentsClientHttpRequestFactory coreServiceHttpRequestFactory) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(coreServiceHttpRequestFactory);
        return restTemplate;
    }

    @Bean
    @ConfigurationProperties(prefix = "rest.core-service.connection-pool")
    public PoolingHttpClientConnectionManager coreServiceHttpClientConnectionManager() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        return new PoolingHttpClientConnectionManager(getSocketFactoryRegistry());
    }
}