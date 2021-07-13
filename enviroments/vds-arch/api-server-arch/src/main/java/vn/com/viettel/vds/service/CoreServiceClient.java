package vn.com.viettel.vds.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import vn.com.viettel.vds.config.AsyncConfig;
import vn.com.viettel.vds.config.resttemplate.CoreServiceClientConfig;
import vn.com.viettel.vds.model.entity.CoreServiceResponse;

@Service
@Slf4j
public class CoreServiceClient {

    private final RestTemplate restTemplate;

    private final CoreServiceClientConfig exampleHttpClientConfig;

    @Autowired
    private AsyncConfig asyncConfig;

    public CoreServiceClient(@Qualifier("coreServiceRestTemplate") RestTemplate restTemplate, CoreServiceClientConfig exampleHttpClientConfig) {
        this.restTemplate = restTemplate;
        this.exampleHttpClientConfig = exampleHttpClientConfig;
    }

    public CoreServiceResponse exampleCallToClient(Long id) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(exampleHttpClientConfig.getEndpoint());
        log.info("Endpoint now is: " + exampleHttpClientConfig.getEndpoint() );
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Content-type", "application/json; charset=utf-8");

        log.info("asyncConfig : " + asyncConfig.getPoolSize());

        // add new headers here
        HttpEntity requestEntity = new HttpEntity<>(headers);
        //With default class
        ResponseEntity<CoreServiceResponse> r = restTemplate.exchange(builder.build().encode().toUri() + "/" + id, HttpMethod.GET, requestEntity,
                CoreServiceResponse.class);
        return r.getBody();
        //With generic class If
        //ResponseEntity<GeneralResponse> r = restTemplate.exchange(builder.build().encode().toUri() + "/" + id, HttpMethod.GET, requestEntity,
        //                GeneralResponse.class);
        //
    }
}