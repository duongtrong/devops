package vn.com.viettel.vds.metrics;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.binder.MeterBinder;

// The following metric probe would output
//
// # HELP service_status Example about service status
// # TYPE service_status gauge
// service_status{service="sample",} 1.0

public class ExampleServiceStatusProbe implements MeterBinder {

    private final String name;
    private final String description;
    private final Iterable<Tag> tags;
    private static final double UP = 1.0;
    private static final double DOWN = 0.0;

    public ExampleServiceStatusProbe() {
        name = "service_status";
        description = "Example about service status";
        tags = Tags.of(Tag.of("service", "sample"));
    }

    public double value() {
        // make metrics logic here
        return UP;
    }

    @Override
    public void bindTo(MeterRegistry meterRegistry) {
        Gauge.builder(name, this, value -> value.value())
                .description(description)
                .tags(tags)
                .baseUnit("status")
                .register(meterRegistry);
    }
}
