
package io.tornimo;

import io.micrometer.core.instrument.dropwizard.DropwizardConfig;
import io.micrometer.core.lang.Nullable;

import java.util.concurrent.TimeUnit;

public interface TornimoConfig extends DropwizardConfig {

    @Nullable
    String get(String key);

    default String prefix() {
        return "tornimo";
    }

    default String[] tagsAsPrefix() {
        return new String[0];
    }

    default TimeUnit rateUnits() {
        String v = get(prefix() + ".rateUnits");
        return v == null ? TimeUnit.SECONDS : TimeUnit.valueOf(v.toUpperCase());
    }

    default TimeUnit durationUnits() {
        String v = get(prefix() + ".durationUnits");
        return v == null ? TimeUnit.MILLISECONDS : TimeUnit.valueOf(v.toUpperCase());
    }

    default String host() {
        String v = get(prefix() + ".host");
        return (v == null) ? "localhost" : v;
    }

    default int port() {
        String v = get(prefix() + ".port");
        return (v == null) ? 2003 : Integer.parseInt(v);
    }

    default boolean enabled() {
        String v = get(prefix() + ".enabled");
        return v == null || Boolean.valueOf(v);
    }

    default String token() {
        return get(prefix() + ".token");
    }

    default String app() {
        String v = get(prefix() + ".app");
        return v == null ? "" : v;
    }
}
