package io.tornimo.spring.autoconfigure;

import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.util.HierarchicalNameMapper;
import io.tornimo.TornimoConfig;
import io.tornimo.TornimoMeterRegistry;
import org.springframework.boot.actuate.autoconfigure.metrics.CompositeMeterRegistryAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.metrics.export.simple.SimpleMetricsExportAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureBefore({
        CompositeMeterRegistryAutoConfiguration.class,
        SimpleMetricsExportAutoConfiguration.class
})
@AutoConfigureAfter(MetricsAutoConfiguration.class)
@ConditionalOnProperty(
        prefix = "management.metrics.export.tornimo",
        name = "enabled",
        havingValue = "true",
        matchIfMissing = true)
@EnableConfigurationProperties(TornimoProperties.class)
public class TornimoMetricsExportAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public Clock clock() {
        return Clock.SYSTEM;
    }

    @Bean
    @ConditionalOnMissingBean
    public TornimoConfig tornimoConfig(TornimoProperties tornimoProperties) {
        return new TornimoPropertiesConfigAdapter(tornimoProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public TornimoMeterRegistry tornimoMeterRegisty(TornimoConfig config,
                                                    Clock clock,
                                                    HierarchicalNameMapper hierarchicalNameMapper) {
        return new TornimoMeterRegistry(config, clock, hierarchicalNameMapper);
    }

    @Bean
    @ConditionalOnMissingBean
    public HierarchicalNameMapper hierarchicalNameMapper(TornimoConfig config) {
        return (id, convention) -> append(config, "")
                .append(HierarchicalNameMapper.DEFAULT.toHierarchicalName(id, convention))
                .toString();
    }

    private StringBuilder append(TornimoConfig config, String instanceId) {
        StringBuilder builder = new StringBuilder();

        builder.append(config.token()).append('.');

        String app = config.app();
        if (!app.isEmpty()) {
            builder.append(app).append('.');
        }

        if (!instanceId.isEmpty()) {
            builder.append(instanceId).append('.');
        }

        return builder;
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(InstanceIdProvider.class)
    public HierarchicalNameMapper hierarchicalNameMapper(InstanceIdProvider instanceIdProvider,
                                                         TornimoConfig config) {
        return (id, convention) -> append(config, instanceIdProvider.getInstanceId())
                .append(HierarchicalNameMapper.DEFAULT.toHierarchicalName(id, convention))
                .toString();
    }
}
