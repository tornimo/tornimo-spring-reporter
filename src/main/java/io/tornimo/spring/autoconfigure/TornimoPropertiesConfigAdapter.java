

package io.tornimo.spring.autoconfigure;

import io.tornimo.TornimoConfig;
import org.springframework.boot.actuate.autoconfigure.metrics.export.properties.PropertiesConfigAdapter;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

class TornimoPropertiesConfigAdapter extends PropertiesConfigAdapter<TornimoProperties>
        implements TornimoConfig {

    TornimoPropertiesConfigAdapter(TornimoProperties properties) {
        super(properties);
    }

    @Override
    public String get(String k) {
        return null;
    }

    @Override
    public boolean enabled() {
        return get(TornimoProperties::isEnabled, TornimoConfig.super::enabled);
    }

    @Override
    public Duration step() {
        return get(TornimoProperties::getStep, TornimoConfig.super::step);
    }

    @Override
    public TimeUnit rateUnits() {
        return get(TornimoProperties::getRateUnits, TornimoConfig.super::rateUnits);
    }

    @Override
    public TimeUnit durationUnits() {
        return get(TornimoProperties::getDurationUnits, TornimoConfig.super::durationUnits);
    }

    @Override
    public String host() {
        return get(TornimoProperties::getHost, TornimoConfig.super::host);
    }

    @Override
    public int port() {
        return get(TornimoProperties::getPort, TornimoConfig.super::port);
    }

    @Override
    public String[] tagsAsPrefix() {
        return get(TornimoProperties::getTagsAsPrefix,
                TornimoConfig.super::tagsAsPrefix);
    }

    @Override
    public String token() {
        return get(TornimoProperties::getToken, TornimoConfig.super::token);
    }

    @Override
    public String app() {
        return get(TornimoProperties::getApp, TornimoConfig.super::app);
    }
}
