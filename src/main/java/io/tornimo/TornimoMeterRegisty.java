package io.tornimo;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.graphite.Graphite;
import com.codahale.metrics.graphite.GraphiteReporter;
import com.codahale.metrics.graphite.GraphiteSender;
import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.dropwizard.DropwizardClock;
import io.micrometer.core.instrument.dropwizard.DropwizardMeterRegistry;
import io.micrometer.core.instrument.util.HierarchicalNameMapper;
import io.micrometer.core.lang.Nullable;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

public class TornimoMeterRegisty extends DropwizardMeterRegistry {

    private final TornimoConfig config;
    private final GraphiteReporter reporter;

    public TornimoMeterRegisty(TornimoConfig config,
                               Clock clock) {
        this(config, clock, new TornimoHierarchicalNameMapper(config.tagsAsPrefix()));
    }

    public TornimoMeterRegisty(TornimoConfig config,
                               Clock clock,
                               HierarchicalNameMapper nameMapper) {
        this(config, clock, nameMapper, new MetricRegistry());
    }

    public TornimoMeterRegisty(TornimoConfig config,
                               Clock clock,
                               HierarchicalNameMapper nameMapper,
                               MetricRegistry metricRegistry) {
        this(config, clock, nameMapper, metricRegistry, defaultGraphiteReporter(config, clock, metricRegistry));
    }

    public TornimoMeterRegisty(TornimoConfig config,
                               Clock clock,
                               HierarchicalNameMapper nameMapper,
                               MetricRegistry metricRegistry,
                               GraphiteReporter reporter) {
        super(config, metricRegistry, nameMapper, clock);

        this.config = config;
        config().namingConvention(new TornimoNamingConvention());
        this.reporter = reporter;

        start();
    }

    private static GraphiteReporter defaultGraphiteReporter(TornimoConfig config, Clock clock, MetricRegistry metricRegistry) {
        return GraphiteReporter.forRegistry(metricRegistry)
                .withClock(new DropwizardClock(clock))
                .convertRatesTo(config.rateUnits())
                .convertDurationsTo(config.durationUnits())
                .build(getGraphiteSender(config));
    }

    private static GraphiteSender getGraphiteSender(TornimoConfig config) {
        return new Graphite(new InetSocketAddress(config.host(), config.port()));
    }

    public void stop() {
        if (config.enabled()) {
            reporter.stop();
        }
    }

    public void start() {
        if (config.enabled()) {
            reporter.start(config.step().getSeconds(), TimeUnit.SECONDS);
        }
    }

    @Override
    public void close() {
        if (config.enabled()) {
            reporter.report();
        }
        stop();
        if (config.enabled()) {
            reporter.close();
        }
        super.close();
    }

    @Override
    @Nullable
    protected Double nullGaugeValue() {
        return null;
    }
}