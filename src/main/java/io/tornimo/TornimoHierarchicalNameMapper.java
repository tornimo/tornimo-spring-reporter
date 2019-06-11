
package io.tornimo;

import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.config.NamingConvention;
import io.micrometer.core.instrument.util.HierarchicalNameMapper;

import java.util.Arrays;
import java.util.List;

public class TornimoHierarchicalNameMapper implements HierarchicalNameMapper {

    private final List<String> tagsAsPrefix;

    public TornimoHierarchicalNameMapper(String... tagsAsPrefix) {
        this.tagsAsPrefix = Arrays.asList(tagsAsPrefix);
    }

    @Override
    public String toHierarchicalName(Meter.Id id, NamingConvention convention) {
        StringBuilder hierarchicalName = new StringBuilder();
        for (String tagKey : tagsAsPrefix) {
            String tagValue = id.getTag(tagKey);
            if (tagValue != null) {
                hierarchicalName.append(convention.tagValue(tagValue)).append(".");
            }
        }
        hierarchicalName.append(id.getConventionName(convention));
        for (Tag tag : id.getTagsAsIterable()) {
            if (!tagsAsPrefix.contains(tag.getKey())) {
                hierarchicalName.append('.').append(sanitize(convention.tagKey(tag.getKey())))
                        .append('.').append(sanitize(convention.tagValue(tag.getValue())));
            }
        }
        return hierarchicalName.toString();
    }

    private String sanitize(String value) {
        return value.replace(" ", "_");
    }
}

