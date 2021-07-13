package vn.com.viettel.vds.cache;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@ConfigurationProperties(prefix = "app.cache")
@Data
public class LocalCacheConfigurationProperties {
    private String cacheNames = new String();
    @NestedConfigurationProperty
    private CaffeineCacheConfig caffeine;

    @Data
    static public class CaffeineCacheConfig {
        private String spec = new String();
    }
}
