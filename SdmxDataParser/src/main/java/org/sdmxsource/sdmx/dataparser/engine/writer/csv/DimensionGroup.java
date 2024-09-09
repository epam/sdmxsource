package org.sdmxsource.sdmx.dataparser.engine.writer.csv;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DimensionGroup {

    private Map<String, String> dimensions;
    private Map<String, List<String>> attributes;
    private boolean touched;

    public DimensionGroup(Map<String, String> dimensions, Map<String, List<String>> attributes, boolean touched) {
        this.touched = touched;
        this.attributes = attributes;
        this.dimensions = dimensions;
    }

    public DimensionGroup(Map<String, String> dimensions, Map<String, List<String>> attributes) {
        this(dimensions, attributes, false);
    }

    @Override
    public boolean equals(Object anotherObject) {
        if (anotherObject == null || this.getClass() != anotherObject.getClass()) {
            return false;
        } else {
            DimensionGroup other = (DimensionGroup) anotherObject;
            return this.dimensions.equals(other.getDimensions());
        }
    }

    @Override
    public int hashCode() {
        int hash = 0;
        for (Map.Entry<String, String> dim : dimensions.entrySet()) {
            hash += Objects.hashCode(dim.getKey()) ^ Objects.hashCode(dim.getValue());
        }
        return hash;
    }

    public Map<String, String> getDimensions() {
        return dimensions;
    }

    public void setDimensions(Map<String, String> dimensions) {
        this.dimensions = dimensions;
    }

    public Map<String, List<String>> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, List<String>> attributes) {
        this.attributes = attributes;
    }

    public boolean isTouched() {
        return touched;
    }

    public void setTouched(boolean touched) {
        this.touched = touched;
    }
}
