package org.sdmxsource.sdmx.dataparser.engine.writer.csv;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class GroupKeyCreator {

    private final SortedSet<String> dimIds;

    public GroupKeyCreator(Collection<String> dimIds) {
        this.dimIds = new TreeSet<>(dimIds);
    }

    public String create(Map<String, String> dimensions) {
        final var sb = new StringBuilder();
        for (var dimId : dimIds) {
            if (sb.length() > 0) {
                sb.append(":");
            }
            final String dimensionValue = requireNonNull(dimensions.get(dimId), "Value for dimension '" + dimId + "' is missing");
            sb.append(dimId).append("=").append(dimensionValue);
        }
        return sb.toString();
    }

    public boolean matches(Map<String, String> dimensions) {
        return dimensions.keySet().containsAll(dimIds);
    }

    public boolean matches(Set<String> dimensions) {
        return dimensions.equals(dimIds);
    }
}