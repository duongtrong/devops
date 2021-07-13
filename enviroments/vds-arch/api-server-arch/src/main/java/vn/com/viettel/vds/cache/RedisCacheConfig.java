package vn.com.viettel.vds.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
@EnableConfigurationProperties(RedisCacheConfigurationProperties.class)
@ConditionalOnProperty(value = "app.cache.redis.enable", havingValue = "true")
public class RedisCacheConfig {

    private static RedisCacheConfiguration createCacheConfiguration(long timeoutInSeconds) {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(timeoutInSeconds));
    }

    @Bean
    public LettuceConnectionFactory redisConnectionFactory(RedisCacheConfigurationProperties properties) {
        log.info("Redis (/Lettuce) configuration enabled. With cache timeout " + properties.getTimeoutSeconds() + " seconds.");
        if (properties.getNodes().size() > 0) {
            log.info("Redis Cluster: {}", properties.getNodes());
            RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration(properties.getNodes());
            return new LettuceConnectionFactory(redisClusterConfiguration);
        } else {
            log.info("Redis Standalone: {}:{}", properties.getHost(), properties.getPort());
            RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
            redisStandaloneConfiguration.setHostName(properties.getHost());
            redisStandaloneConfiguration.setPort(properties.getPort());
            return new LettuceConnectionFactory(redisStandaloneConfiguration);
        }
    }

    @Bean
    public RedisTemplate<Byte[], Byte[]> redisTemplate(RedisConnectionFactory cf) {
        RedisTemplate<Byte[], Byte[]> redisTemplate = new RedisTemplate<Byte[], Byte[]>();
        redisTemplate.setConnectionFactory(cf);
        return redisTemplate;
    }

    @Bean
    public RedisCacheConfiguration cacheConfiguration(RedisCacheConfigurationProperties properties) {
        return createCacheConfiguration(properties.getTimeoutSeconds());
    }

    @Bean
    public CacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory, RedisCacheConfigurationProperties properties) {
        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();

        for (Map.Entry<String, Long> cacheNameAndTimeout : properties.getCacheExpirations().entrySet()) {
            cacheConfigurations.put(cacheNameAndTimeout.getKey(), createCacheConfiguration(cacheNameAndTimeout.getValue()));
        }

        return RedisCacheManager
                .builder(redisConnectionFactory)
                .cacheDefaults(cacheConfiguration(properties))
                .withInitialCacheConfigurations(cacheConfigurations).build();
    }
}
