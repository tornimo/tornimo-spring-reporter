
package io.tornimo;

import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.config.NamingConvention;
import io.micrometer.core.lang.Nullable;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class TornimoNamingConvention implements NamingConvention {
    private static final Pattern blacklistedChars = Pattern.compile("[{}(),=\\[\\]/]");
    private final NamingConvention delegate;

    public TornimoNamingConvention() {
        this(NamingConvention.camelCase);
    }

    public TornimoNamingConvention(NamingConvention delegate) {
        this.delegate = delegate;
    }

    @Override
    public String name(String name, Meter.Type type, @Nullable String baseUnit) {
        return sanitize(this.delegate.name(normalize(name), type, baseUnit));
    }

    @Override
    public String tagKey(String key) {
        return sanitize(this.delegate.tagKey(normalize(key)));
    }

    @Override
    public String tagValue(String value) {
        return sanitize(this.delegate.tagValue(normalize(value)));
    }

    private String normalize(String name) {
        return Normalizer.normalize(name, Normalizer.Form.NFKD);
    }

    private String sanitize(String delegated) {
        return blacklistedChars.matcher(delegated).replaceAll("_");
    }
}
