package vn.com.viettel.vds;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication(exclude = {
        RedisAutoConfiguration.class,
        CacheAutoConfiguration.class
})
@EnableCaching // comment it if want quickly disable cache
//@PropertySource(value = {"classpath:/appInfo.properties"}, ignoreResourceNotFound = true)
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
