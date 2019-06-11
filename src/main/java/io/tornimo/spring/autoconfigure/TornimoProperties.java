package io.tornimo.spring.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@ConfigurationProperties(prefix = "management.metrics.export.tornimo")
public class TornimoProperties {

    /**
     * Whether exporting of metrics to Graphite is enabled.
     */
    private boolean enabled = true;

    /**
     * Step size (i.e. reporting frequency) to use.
     */
    private Duration step = Duration.ofMinutes(1);

    /**
     * Base time unit used to report rates.
     */
    private TimeUnit rateUnits = TimeUnit.SECONDS;

    /**
     * Base time unit used to report durations.
     */
    private TimeUnit durationUnits = TimeUnit.MILLISECONDS;

    /**
     * Host of the Graphite server to receive exported metrics.
     */
    private String host = "localhost";

    /**
     * Port of the Graphite server to receive exported metrics.
     */
    private Integer port = 2003;

    /**
     * For the default naming convention, turn the specified tag keys into part of the
     * metric prefix.
     */
    private String[] tagsAsPrefix = new String[0];

    private String token = "";

    private String app = "";

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Duration getStep() {
        return this.step;
    }

    public void setStep(Duration step) {
        this.step = step;
    }

    public TimeUnit getRateUnits() {
        return this.rateUnits;
    }

    public void setRateUnits(TimeUnit rateUnits) {
        this.rateUnits = rateUnits;
    }

    public TimeUnit getDurationUnits() {
        return this.durationUnits;
    }

    public void setDurationUnits(TimeUnit durationUnits) {
        this.durationUnits = durationUnits;
    }

    public String getHost() {
        return this.host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return this.port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String[] getTagsAsPrefix() {
        return this.tagsAsPrefix;
    }

    public void setTagsAsPrefix(String[] tagsAsPrefix) {
        this.tagsAsPrefix = tagsAsPrefix;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }
}
