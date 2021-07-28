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
package org.sdmxsource.sdmx.dataparser.model;

import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataStructureBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DimensionBean;
import org.sdmxsource.sdmx.api.model.beans.registry.ConstrainedDataKeyBean;
import org.sdmxsource.sdmx.api.model.beans.registry.ConstraintDataKeySetBean;
import org.sdmxsource.sdmx.api.model.beans.registry.ContentConstraintBean;
import org.sdmxsource.sdmx.api.model.beans.registry.KeyValues;
import org.sdmxsource.sdmx.api.model.data.KeyValue;
import org.sdmxsource.sdmx.api.model.data.Keyable;
import org.sdmxsource.sdmx.api.model.data.Observation;
import org.sdmxsource.util.ObjectUtil;

import java.util.*;

/**
 * Builds a picture of what is valid, and what is invalid with regards to ContentConstraintBean
 */
public class ContentConstraintModel {
    private ContentConstraintBean constraint;
    private DataStructureBean dsd;

    private Date dateFrom;
    private Date dateTo;
    private Map<String, Set<String>> filterConceptValues;
    private Map<String, Set<String>> onlyAllowConceptValues;
    private Map<String, DimensionValues> filterDimensionValueMap;
    private Map<String, DimensionValues> onlyAllowDimensionValueMap;

    private boolean acceptNothing;


    /**
     * Instantiates a new Content constraint model.
     *
     * @param constraintBean    the constraint bean
     * @param dataStructureBean the data structure bean
     */
    public ContentConstraintModel(ContentConstraintBean constraintBean, DataStructureBean dataStructureBean) {
        this.constraint = constraintBean;
        this.dsd = dataStructureBean;
        createFilterMaps();
    }

    /**
     * Is valid observation.
     *
     * @param obs the obs
     */
    public void isValidObservation(Observation obs) {
        if (acceptNothing) {
            throw new SdmxSemmanticException("No Observations are allowed for this dataset");
        }
        verifyKeyValue(obs.getSeriesKey(), obs.getAttributes(), false);

        if (dateFrom != null && dateFrom.getTime() > obs.getObsAsTimeDate().getTime()) {
            throw new SdmxSemmanticException("Observation Time '" + obs.getObsTime() + "' is before the expected Reporting Period start date of : " + constraint.getReferencePeriod().getStartTime().getDateInSdmxFormat());
        }
        if (dateTo != null && dateTo.getTime() < obs.getObsAsTimeDate().getTime()) {
            throw new SdmxSemmanticException("Observation Time '" + obs.getObsTime() + "' is after the expected Reporting Period end date of : " + constraint.getReferencePeriod().getEndTime().getDateInSdmxFormat());
        }
    }

    /**
     * Is valid key.
     *
     * @param key the key
     */
    public void isValidKey(Keyable key) {
        if (acceptNothing) {
            throw new SdmxSemmanticException("No Series are allowed for this dataset");
        }
        //2. Check if each individual key value is okay to write
        verifyKeyValue(key, key.getKey(), true);
        verifyKeyValue(key, key.getAttributes(), false);

        //3. Check against allowed series (wildcarded)
        if (onlyAllowDimensionValueMap != null) {
            Set<SeriesKey> validSeries = null;
            for (KeyValue kv : key.getKey()) {
                DimensionValues dimValues = onlyAllowDimensionValueMap.get(kv.getConcept());
                Set<SeriesKey> validSeriesForDimension = dimValues.getValidSeriesForValue(kv.getCode());
                if (validSeries == null) {
                    validSeries = validSeriesForDimension;
                } else {
                    validSeries.retainAll(validSeriesForDimension);
                }
                if (!ObjectUtil.validCollection(validSeries)) {
                    break;
                }
            }
            if (validSeries.size() == 0) {
                throw new SdmxSemmanticException("The key " + key + " is not allowed");
            }
        }

        //4. Check against disallowed series (wildcarded)
        if (filterDimensionValueMap != null) {
            Set<SeriesKey> invlaidSeries = null;
            for (KeyValue kv : key.getKey()) {
                DimensionValues dimValues = filterDimensionValueMap.get(kv.getConcept());
                Set<SeriesKey> validSeriesForDimension = dimValues.getValidSeriesForValue(kv.getCode());
                if (invlaidSeries == null) {
                    invlaidSeries = validSeriesForDimension;
                } else {
                    invlaidSeries.retainAll(validSeriesForDimension);
                }
                if (!ObjectUtil.validCollection(invlaidSeries)) {
                    break;
                }
            }
            if (invlaidSeries.size() > 0) {
                throw new SdmxSemmanticException("The key " + key + " is not allowed");
            }
        }
    }

    /**
     * Returns true if the KeyValue(s) are included (or not excluded) by the ContentConstraint.
     *
     * @param keyValues
     * @return
     */
    private void verifyKeyValue(Keyable key, Collection<KeyValue> keyValues, boolean isSeries) {
        if (filterConceptValues != null || onlyAllowConceptValues != null) {
            for (KeyValue kv : keyValues) {
                verifyKeyValue(key, kv, isSeries);
            }
        }
    }

    /**
     * @param key
     * @param kv
     * @param isSeries true if this is validating a series key value, false if it's an attribute value
     */
    private void verifyKeyValue(Keyable key, KeyValue kv, boolean isSeries) {
        if (!isValidKeyValue(kv)) {
            String component = isSeries ? "Dimension" : "Attribtue";
            throw new SdmxSemmanticException("Illegal Series: " + key.getShortCode() + " - The Value '" + kv.getCode() + "' for " + component + " '" + kv.getConcept() + "' is disallowed");
        }
    }

    /**
     * Is valid key value boolean.
     *
     * @param kv the kv
     * @return the boolean
     */
    public boolean isValidKeyValue(KeyValue kv) {
        Set<String> filterValues = null;
        Set<String> keepValues = null;
        if (filterConceptValues != null) {
            filterValues = filterConceptValues.get(kv.getConcept());
        }
        if (onlyAllowConceptValues != null) {
            keepValues = onlyAllowConceptValues.get(kv.getConcept());
        }

        boolean filterValBaseOnExclude = filterValues != null && filterValues.contains(kv.getCode());
        boolean filterValBaseOnInclude = keepValues != null && !keepValues.contains(kv.getCode());

        if (filterValBaseOnExclude || filterValBaseOnInclude) {
            return false;
        }
        return true;
    }


    private void createFilterMaps() {
        //1. Key Set Constraints
        if (constraint.getIncludedSeriesKeys() != null) {
            if (!ObjectUtil.validCollection(constraint.getIncludedSeriesKeys().getConstrainedDataKeys())) {
                acceptNothing = true;
            } else {
                //TODO what if there are include series and exclude series in the same constraint?
                Set<SeriesKey> seriesKeys = createFilteredSeries(constraint.getIncludedSeriesKeys());
                if (ObjectUtil.validCollection(seriesKeys)) {
                    onlyAllowDimensionValueMap = new HashMap<String, ContentConstraintModel.DimensionValues>();
                    for (DimensionBean dim : dsd.getDimensions(SDMX_STRUCTURE_TYPE.DIMENSION)) {
                        onlyAllowDimensionValueMap.put(dim.getId(), new DimensionValues(dim.getId(), seriesKeys));
                    }
                }
            }
        }

        if (constraint.getExcludedSeriesKeys() != null) {
            Set<SeriesKey> seriesKeys = createFilteredSeries(constraint.getExcludedSeriesKeys());

            if (ObjectUtil.validCollection(seriesKeys)) {
                filterDimensionValueMap = new HashMap<String, ContentConstraintModel.DimensionValues>();
                for (DimensionBean dim : dsd.getDimensions(SDMX_STRUCTURE_TYPE.DIMENSION)) {
                    filterDimensionValueMap.put(dim.getId(), new DimensionValues(dim.getId(), seriesKeys));
                }
            }
        }


        //2. Cube Region Constraints
        if (constraint.getExcludedCubeRegion() != null) {
            filterConceptValues = new HashMap<String, Set<String>>();
            //TODO Excluded Cube Regions & isIncluded
            addKeysToConstraint(constraint.getExcludedCubeRegion().getKeyValues(), false);
            addKeysToConstraint(constraint.getExcludedCubeRegion().getAttributeValues(), false);
        }
        if (constraint.getIncludedCubeRegion() != null) {
            onlyAllowConceptValues = new HashMap<String, Set<String>>();
            //TODO Excluded Cube Regions & isIncluded
            addKeysToConstraint(constraint.getIncludedCubeRegion().getKeyValues(), true);
            addKeysToConstraint(constraint.getIncludedCubeRegion().getAttributeValues(), true);
        }

        //Reporting Period
        if (constraint.getReferencePeriod() != null) {
            if (constraint.getReferencePeriod().getStartTime() != null) {
                this.dateFrom = constraint.getReferencePeriod().getStartTime().getDate();
            }
            if (constraint.getReferencePeriod().getEndTime() != null) {
                this.dateTo = constraint.getReferencePeriod().getEndTime().getDate();
            }
        }
    }

    private void addKeysToConstraint(Collection<KeyValues> kvs, boolean isInclude) {
        Map<String, Set<String>> keyValuesSet;
        if (isInclude) {
            keyValuesSet = onlyAllowConceptValues;
        } else {
            keyValuesSet = filterConceptValues;
        }
        for (KeyValues currentKeyValues : kvs) {
            String dimensionId = currentKeyValues.getId();
            Set<String> currentAllowValues = keyValuesSet.get(dimensionId);
            if (currentAllowValues == null) {
                currentAllowValues = new HashSet<String>();
                keyValuesSet.put(dimensionId, currentAllowValues);
            }
            currentAllowValues.addAll(currentKeyValues.getValues());
        }
    }


    private Set<SeriesKey> createFilteredSeries(ConstraintDataKeySetBean cdks) {
        Map<String, KeyValue> kvs = new HashMap<String, KeyValue>();
        Set<SeriesKey> seriesKeys = new HashSet<SeriesKey>();

        for (ConstrainedDataKeyBean dataKey : cdks.getConstrainedDataKeys()) {
            SeriesKey sk = new SeriesKey();
            for (KeyValue kv : dataKey.getKeyValues()) {
                String curKv = kv.getConcept() + ":" + kv.getCode();
                KeyValue skv = kvs.get(curKv);
                if (skv == null) {
                    skv = kv;
                    kvs.put(curKv, skv);
                }
                sk.addKeyValue(skv);
            }
            seriesKeys.add(sk);
        }
        return seriesKeys;
    }

    private class DimensionValues {
        private String dimensionId;
        private Map<String, Set<SeriesKey>> valueToKey = new HashMap<String, Set<SeriesKey>>();
        private Set<SeriesKey> wildcardedForDimension = new HashSet<SeriesKey>();

        /**
         * Instantiates a new Dimension values.
         *
         * @param dimensionId the dimension id
         * @param seriesKeys  the series keys
         */
        public DimensionValues(String dimensionId, Set<SeriesKey> seriesKeys) {
            this.dimensionId = dimensionId;
            for (SeriesKey sk : seriesKeys) {
                addSeriesKey(sk);
            }
        }

        /**
         * Add series key.
         *
         * @param sk the sk
         */
        public void addSeriesKey(SeriesKey sk) {
            String dimVal = sk.getValueForConcept(dimensionId);
            if (!ObjectUtil.validString(dimVal) || dimVal.equals(ContentConstraintBean.WILDCARD_CODE)) {
                wildcardedForDimension.add(sk);
            } else {
                Set<SeriesKey> currentSeriesForValue = valueToKey.get(dimVal);
                if (currentSeriesForValue == null) {
                    currentSeriesForValue = new HashSet<SeriesKey>();
                    valueToKey.put(dimVal, currentSeriesForValue);
                }
                currentSeriesForValue.add(sk);
            }
        }

        /**
         * Gets valid series for value.
         *
         * @param value the value
         * @return the valid series for value
         */
        public Set<SeriesKey> getValidSeriesForValue(String value) {
            Set<SeriesKey> returnSet = new HashSet<SeriesKey>();
            Set<SeriesKey> validSeries = valueToKey.get(value);
            if (validSeries != null) {
                returnSet.addAll(validSeries);
            }
            returnSet.addAll(wildcardedForDimension);
            return returnSet;
        }
    }

    private class SeriesKey {
        private List<KeyValue> keyValues = new ArrayList<KeyValue>();
        private Map<String, String> conceptValueMap = new HashMap<String, String>();

        private void addKeyValue(KeyValue kv) {
            this.keyValues.add(kv);
            conceptValueMap.put(kv.getConcept(), kv.getCode());
        }

        private String getValueForConcept(String dimensionId) {
            return conceptValueMap.get(dimensionId);
        }
    }
}
