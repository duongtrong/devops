package vn.com.viettel.vds.cache;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "app.cache.redis")
@Data
public class RedisCacheConfigurationProperties {
    private long timeoutSeconds = 60;
    private int port = 6379;
    private String host = "localhost";
    private List<String> nodes = new ArrayList<>();
    // Mapping of cacheNames to expire-after-write timeout in seconds
    private Map<String, Long> cacheExpirations = new HashMap<>();
}
