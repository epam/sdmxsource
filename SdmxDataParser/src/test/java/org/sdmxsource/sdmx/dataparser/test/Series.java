package org.sdmxsource.sdmx.dataparser.test;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.sdmxsource.sdmx.api.model.data.Observation;

import java.util.List;
import java.util.Map;


public class Series {
    private String seriesId;
    private Map<String, String> attributes;
    private Map<String, String> dimensions;
    private List<Observation> observations;

    public Series(String seriesId, Map<String, String> attributes, Map<String, String> dimensions, List<Observation> observations) {
        this.seriesId = seriesId;
        this.attributes = attributes;
        this.dimensions = dimensions;
        this.observations = observations;
    }

    public String getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(String seriesId) {
        this.seriesId = seriesId;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public Map<String, String> getDimensions() {
        return dimensions;
    }

    public void setDimensions(Map<String, String> dimensions) {
        this.dimensions = dimensions;
    }

    public List<Observation> getObservations() {
        return observations;
    }

    public void setObservations(List<Observation> observations) {
        this.observations = observations;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("seriesId", seriesId)
                .append("attributes", attributes)
                .append("dimensions", dimensions)
                .append("observations", observations)
                .toString();
    }
}
