package vn.com.viettel.vds.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Lazy
@Component
public class MetricsConfig {

    private final MeterRegistry meterRegistry;

    public MetricsConfig(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    // register metrics bean here
    @Bean
    ExampleServiceStatusProbe serviceStatusProbe() {
        return new ExampleServiceStatusProbe();
    }
}
