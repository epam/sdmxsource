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
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.AnnotationBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataStructureBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataflowBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DimensionBean;
import org.sdmxsource.sdmx.api.model.data.KeyValue;
import org.sdmxsource.sdmx.api.model.data.Keyable;
import org.sdmxsource.sdmx.util.date.DateUtil;
import org.sdmxsource.util.ObjectUtil;

import java.util.*;


/**
 * The type Keyable.
 */
public class KeyableImpl implements Keyable {
    private DataStructureBean dataStructure;
    private DataflowBean dataflowBean;
    private List<KeyValue> attributes = new ArrayList<KeyValue>();
    private List<KeyValue> key = new ArrayList<KeyValue>();
    private Map<String, KeyValue> attributeMap = new HashMap<String, KeyValue>();
    private Map<String, String> keyMap = new HashMap<String, String>();
    private List<AnnotationBean> annotations = new ArrayList<AnnotationBean>();
    private boolean series;
    private String groupName;
    private TIME_FORMAT timeFormat;
    private String uniqueId; //Generated on equals/hashcode
    private String shotCode;


    private boolean isTimeSeries = true;
    private String crossSectionConcept;
    private String obsTime;
    private Date date;

    /**
     * Instantiates a new Keyable.
     *
     * @param dataflowBean        the dataflow bean
     * @param dataStructure       the data structure
     * @param key                 the key
     * @param attributes          the attributes
     * @param timeFormat          the time format
     * @param crossSectionConcept the cross section concept
     * @param obsTime             the obs time
     * @param annotationBeans     the annotation beans
     */
    public KeyableImpl(DataflowBean dataflowBean,
                       DataStructureBean dataStructure,
                       List<KeyValue> key,
                       List<KeyValue> attributes,
                       TIME_FORMAT timeFormat,
                       String crossSectionConcept,
                       String obsTime, AnnotationBean... annotationBeans) {
        this(dataflowBean, dataStructure, key, attributes, null, timeFormat, annotationBeans);
        this.isTimeSeries = false;
        this.crossSectionConcept = crossSectionConcept;
        this.obsTime = obsTime;
        if (obsTime == null) {
            throw new SdmxSemmanticException("Cross sectional dataset missing time value for key : " + this);
        }
    }

    /**
     * Instantiates a new Keyable.
     *
     * @param dataflowBean    the dataflow bean
     * @param dataStructure   the data structure
     * @param key             the key
     * @param attributes      the attributes
     * @param timeFormat      the time format
     * @param annotationBeans the annotation beans
     */
    public KeyableImpl(DataflowBean dataflowBean,
                       DataStructureBean dataStructure,
                       List<KeyValue> key,
                       List<KeyValue> attributes,
                       TIME_FORMAT timeFormat, AnnotationBean... annotationBeans) {
        this(dataflowBean, dataStructure, key, attributes, null, timeFormat, annotationBeans);
    }

    /**
     * Instantiates a new Keyable.
     *
     * @param dataflowBean    the dataflow bean
     * @param dataStructure   the data structure
     * @param key             the key
     * @param attributes      the attributes
     * @param groupName       the group name
     * @param annotationBeans the annotation beans
     */
    public KeyableImpl(DataflowBean dataflowBean,
                       DataStructureBean dataStructure,
                       List<KeyValue> key,
                       List<KeyValue> attributes,
                       String groupName, AnnotationBean... annotationBeans) {
        this(dataflowBean, dataStructure, key, attributes, groupName, null, annotationBeans);
    }

    /**
     * Instantiates a new Keyable.
     *
     * @param dataflowBean    the dataflow bean
     * @param dataStructure   the data structure
     * @param key             the key
     * @param attributes      the attributes
     * @param groupName       the group name
     * @param timeFormat      the time format
     * @param annotationBeans the annotation beans
     */
    public KeyableImpl(DataflowBean dataflowBean,
                       DataStructureBean dataStructure,
                       List<KeyValue> key,
                       List<KeyValue> attributes,
                       String groupName,
                       TIME_FORMAT timeFormat, AnnotationBean... annotationBeans) {
        this.dataStructure = dataStructure;
        this.dataflowBean = dataflowBean;
        if (dataStructure == null) {
            throw new IllegalArgumentException("Data Structure can not be null");
        }
        if (ObjectUtil.validString(groupName)) {
            this.series = false;
        } else {
            this.series = true;
        }
        if (attributes != null) {
            this.attributes = new ArrayList<KeyValue>(attributes);
            for (KeyValue currentKv : attributes) {
                attributeMap.put(currentKv.getConcept(), currentKv);
            }
        }
        if (key != null) {
            this.key = new ArrayList<KeyValue>(key);
            for (KeyValue currentKv : key) {
                keyMap.put(currentKv.getConcept(), currentKv.getCode());
            }
        }
        if (annotationBeans != null) {
            for (AnnotationBean currentAnnotation : annotationBeans) {
                this.annotations.add(currentAnnotation);
            }
        }
        this.groupName = groupName;
        this.timeFormat = timeFormat;
    }

    /**
     * Builds a Keyable from a short code such as A:B:C.  The short code can contain missing entries A::B::X
     *
     * @param dataflowBean (optional)
     * @param dsd          (required)
     * @param shortCode    (required)
     * @return keyable keyable
     */
    public static Keyable buildFromShortCode(DataflowBean dataflowBean, DataStructureBean dsd, String shortCode) {
        if (!ObjectUtil.validString(shortCode)) {
            throw new SdmxException("KeyableImpl.buildFromShortCode - shortCode required");
        }
        String[] split = shortCode.split(":");
        List<DimensionBean> dimensions = dsd.getDimensionList().getDimensions();
        List<KeyValue> key = new ArrayList<KeyValue>();
        for (int i = 0; i < dimensions.size(); i++) {
            String dimId = dimensions.get(i).getId();
            if (split.length > i && split[i].length() > 0) {
                key.add(new KeyValueImpl(split[i], dimId));
            }
        }
        return new KeyableImpl(dataflowBean, dsd, key, null, null, null, null);
    }

    @Override
    public List<AnnotationBean> getAnnotations() {
        return new ArrayList<AnnotationBean>(annotations);
    }

    @Override
    public DataStructureBean getDataStructure() {
        return dataStructure;
    }

    @Override
    public DataflowBean getDataflow() {
        return dataflowBean;
    }

    @Override
    public String getShortCode() {
        if (shotCode == null) {
            generateUniqueId();
        }
        return shotCode;
    }

    @Override
    public KeyValue getAttribute(String concept) {
        return attributeMap.get(concept);
    }


    @Override
    public boolean isTimeSeries() {
        return isTimeSeries;
    }

    @Override
    public String getCrossSectionConcept() {
        return crossSectionConcept;
    }

    @Override
    public String getObsTime() {
        return obsTime;
    }


    @Override
    public String getKeyValue(String dimensionId) {
        return keyMap.get(dimensionId);
    }

    @Override
    public Date getObsAsTimeDate() {
        if (isTimeSeries) {
            return null;
        }
        if (date == null) {
            date = DateUtil.formatDate(obsTime, true);
        }
        return new Date(date.getTime());
    }

    @Override
    public List<KeyValue> getKey() {
        return new ArrayList<KeyValue>(key);
    }

    @Override
    public List<KeyValue> getAttributes() {
        return new ArrayList<KeyValue>(attributes);
    }

    @Override
    public boolean isSeries() {
        return series;
    }

    @Override
    public TIME_FORMAT getTimeFormat() {
        return timeFormat;
    }


    @Override
    public String getGroupName() {
        return groupName;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj instanceof KeyableImpl) {
            if (uniqueId == null) {
                generateUniqueId();
            }
            KeyableImpl that = (KeyableImpl) obj;
            if (that.uniqueId == null) {
                that.generateUniqueId();
            }
            return this.uniqueId.equals(that.uniqueId);
        }
        return false;
    }

    @Override
    public int hashCode() {
        if (uniqueId == null) {
            generateUniqueId();
        }
        return uniqueId.hashCode();
    }

    private void generateUniqueId() {
        shotCode = "";
        String concat = "";
        StringBuilder sb = new StringBuilder();
        for (KeyValue kv : getKey()) {
            shotCode += concat + kv.getCode();
            concat = ":";
            sb.append(kv.getConcept() + concat + kv.getCode());
        }

        if (!isTimeSeries()) {
            shotCode += concat + this.getObsTime();
            sb.append(DimensionBean.TIME_DIMENSION_FIXED_ID + concat + this.getObsTime());
        }

        for (KeyValue kv : getAttributes()) {
            sb.append(kv.getConcept() + concat + kv.getCode());
        }
        sb.append(series);
        if (groupName != null) {
            sb.append(groupName);
        }
        uniqueId = sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (series) {
            sb.append("series ");
        } else {
            sb.append("group " + groupName + " ");
        }
        String concat = "";
        for (KeyValue kv : key) {
            sb.append(concat);
            sb.append(kv.getConcept());
            sb.append(":");
            sb.append(kv.getCode());
            concat = ",";
        }
        if (!isTimeSeries) {
            sb.append(concat);
            sb.append(getObsTime());
        }
        return sb.toString();
    }


}
