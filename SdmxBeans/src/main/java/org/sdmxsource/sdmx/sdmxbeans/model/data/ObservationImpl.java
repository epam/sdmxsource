/*******************************************************************************
 * Copyright (c) 2013 Metadata Technology Ltd.
 *
 * All rights reserved. This program and the accompanying materials are made 
 * available under the terms of the GNU Lesser General Public License v 3.0 
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl.html
 *
 * This file is part of the SDMX Component Library.
 *
 * The SDMX Component Library is free software: you can redistribute it and/or 
 * modify it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * The SDMX Component Library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
 * General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with The SDMX Component Library If not, see 
 * http://www.gnu.org/licenses/lgpl.
 *
 * Contributors:
 * Metadata Technology - initial API and implementation
 ******************************************************************************/
package org.sdmxsource.sdmx.sdmxbeans.model.data;

import org.sdmxsource.sdmx.api.constants.TIME_FORMAT;
import org.sdmxsource.sdmx.api.model.beans.base.AnnotationBean;
import org.sdmxsource.sdmx.api.model.data.KeyValue;
import org.sdmxsource.sdmx.api.model.data.Keyable;
import org.sdmxsource.sdmx.api.model.data.Observation;
import org.sdmxsource.sdmx.util.date.DateUtil;
import org.sdmxsource.util.ObjectUtil;

import java.util.*;


/**
 * The type Observation.
 */
public class ObservationImpl implements Observation {
    private Keyable seriesKey;

    private String obsValue;
    private String obsTime;
    private List<KeyValue> attributes = new ArrayList<KeyValue>();
    private Date date;
    private TIME_FORMAT timeFormat;
    private boolean isCrossSection;
    private KeyValue crossSectionValue;
    private Map<String, KeyValue> attributeMap = new HashMap<String, KeyValue>();
    private List<AnnotationBean> annotations = new ArrayList<AnnotationBean>();

    /**
     * Instantiates a new Observation.
     *
     * @param seriesKey         the series key
     * @param obsTime           the obs time
     * @param obsValue          the obs value
     * @param attributes        the attributes
     * @param crossSectionValue the cross section value
     * @param annotationBeans   the annotation beans
     */
    public ObservationImpl(Keyable seriesKey, String obsTime, String obsValue, List<KeyValue> attributes, KeyValue crossSectionValue, AnnotationBean... annotationBeans) {
        this.seriesKey = seriesKey;
        this.obsValue = obsValue;
        this.obsTime = obsTime;

        if (seriesKey == null) {
            throw new IllegalArgumentException("Series Key can not be null");
        }

        if (attributes != null) {
            this.attributes = new ArrayList<KeyValue>(attributes);
            for (KeyValue currentKv : attributes) {
                attributeMap.put(currentKv.getConcept(), currentKv);
            }
        }
        if (annotationBeans != null) {
            for (AnnotationBean currentAnnotation : annotationBeans) {
                this.annotations.add(currentAnnotation);
            }
        }
        this.crossSectionValue = crossSectionValue;
        this.isCrossSection = true;
    }

    /**
     * Instantiates a new Observation.
     *
     * @param seriesKey       the series key
     * @param obsTime         the obs time
     * @param obsValue        the obs value
     * @param attributes      the attributes
     * @param annotationBeans the annotation beans
     */
    public ObservationImpl(Keyable seriesKey, String obsTime, String obsValue, List<KeyValue> attributes, AnnotationBean... annotationBeans) {
        this.obsValue = obsValue;
        this.seriesKey = seriesKey;

        if (seriesKey == null) {
            throw new IllegalArgumentException("Series Key can not be null");
        }
        if (!ObjectUtil.validString(obsTime)) {
            if (seriesKey.isTimeSeries()) {
                throw new IllegalArgumentException("Observation for Key '" + seriesKey + "' does not specify the observation time");
            }
            throw new IllegalArgumentException("Observation for Key '" + seriesKey + "' does not specify the observation concept: " + seriesKey.getCrossSectionConcept());
        }
        this.obsTime = obsTime;
        if (attributes != null) {
            this.attributes = new ArrayList<KeyValue>(attributes);
            for (KeyValue currentKv : attributes) {
                attributeMap.put(currentKv.getConcept(), currentKv);
            }
        }
        if (annotationBeans != null) {
            for (AnnotationBean currentAnnotation : annotationBeans) {
                this.annotations.add(currentAnnotation);
            }
        }
        this.isCrossSection = false;
    }

    @Override
    public List<AnnotationBean> getAnnotations() {
        return new ArrayList<AnnotationBean>(annotations);
    }

    @Override
    public Keyable getSeriesKey() {
        return seriesKey;
    }

    @Override
    public String getObservationValue() {
        return obsValue;
    }

    @Override
    public KeyValue getAttribute(String concept) {
        return attributeMap.get(concept);
    }

    @Override
    public String getObsTime() {
        return obsTime;
    }

    @Override
    public boolean isCrossSection() {
        return isCrossSection;
    }

    @Override
    public KeyValue getCrossSectionalValue() {
        return crossSectionValue;
    }

    @Override
    public Date getObsAsTimeDate() {
        if (obsTime == null) {
            return null;
        }
        if (date == null) {
            date = DateUtil.formatDate(obsTime, true);
        }
        return new Date(date.getTime());
    }


    @Override
    public TIME_FORMAT getObsTimeFormat() {
        if (obsTime == null) {
            return null;
        }
        if (timeFormat == null) {
            timeFormat = DateUtil.getTimeFormatOfDate(obsTime);
        }
        return timeFormat;
    }

    @Override
    public List<KeyValue> getAttributes() {
        return new ArrayList<KeyValue>(attributes);
    }


    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj.toString().equals(this.toString());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String concat = "";
        for (KeyValue kv : attributes) {
            sb.append(concat + kv.getConcept() + ":" + kv.getCode());
            concat = ",";
        }
        if (isCrossSection) {
            return "Obs " + crossSectionValue.getConcept() + ":" + crossSectionValue.getCode() + " = " + obsValue + " - Attributes : " + sb.toString();
        }
        return "Obs " + obsTime + " = " + obsValue + " - Attributes : " + sb.toString();
    }

    @Override
    public int compareTo(Observation o) {
        if (this.obsTime == null) {
            if (o.getObsTime() == null) {
                return 0;
            }
            return -1;
        }
        if (o.getObsTime() == null) {
            return 1;
        }
        if (this.obsTime.length() == o.getObsTime().length()) {
            return this.obsTime.compareTo(o.getObsTime());
        }
        return this.getObsAsTimeDate().compareTo(o.getObsAsTimeDate());
    }
}
