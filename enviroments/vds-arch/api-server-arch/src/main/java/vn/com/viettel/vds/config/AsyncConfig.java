package vn.com.viettel.vds.config;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
@Slf4j
@Getter
public class AsyncConfig {

    @Value("${executor.corePoolSize:2}")
    private String poolSize;
    @Value("${executor.maxPoolSize:4}")
    private String setMaxPoolSize;
    @Bean("threadPoolTaskExecutor")
    public TaskExecutor getAsyncExecutor() {
        log.info("corePoolSize : " + poolSize + ", MaxPoolSize : "  + setMaxPoolSize);
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(Integer.parseInt(poolSize));
        executor.setMaxPoolSize(Integer.parseInt(setMaxPoolSize));
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setThreadNamePrefix("Async-");
        return executor;
    }
}
