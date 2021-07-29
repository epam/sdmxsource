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
package org.sdmxsource.sdmx.util.beans;

import org.sdmxsource.sdmx.api.constants.ATTRIBUTE_ATTACHMENT_LEVEL;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.model.beans.datastructure.AttributeBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataStructureBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DimensionBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.GroupBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Data structure util.
 */
public class DataStructureUtil {

    /**
     * Gets time concept.
     *
     * @param dataStructureBean the data structure bean
     * @return the time concept
     */
    public static String getTimeConcept(DataStructureBean dataStructureBean) {
        if (dataStructureBean.getTimeDimension() != null) {
            return dataStructureBean.getTimeDimension().getId();
        }
        return null;
    }

    /**
     * Gets measure concept.
     *
     * @param dataStructureBean the data structure bean
     * @return the measure concept
     */
    public static String getMeasureConcept(DataStructureBean dataStructureBean) {
        return dataStructureBean.getPrimaryMeasure().getId();
    }

    /**
     * Returns the series key concepts.
     *
     * @param dataStructureBean the data structure bean
     * @return series key concepts
     */
    public static List<String> getSeriesKeyConcepts(DataStructureBean dataStructureBean) {
        List<String> keyConcepts = new ArrayList<String>();
        for (DimensionBean currentDimension : dataStructureBean.getDimensions(SDMX_STRUCTURE_TYPE.DIMENSION)) {
            keyConcepts.add(currentDimension.getId());
        }
        return keyConcepts;
    }

    /**
     * Returns the concept id's belonging to each series attributes in the order they appear in the DataStructureBean,
     * followed by the concept id's belonging to each group attributes in the order of the groups,
     * and attributes within each group.
     * <p>
     * A concept id will not be added to the list twice, so if a concept id appears in a series attribute
     * and a group attribute, then the first occurrence will be added to the list, which will be the series
     * attribute, the second occurrence will not be added.
     *
     * @param dataStructureBean the data structure bean
     * @return series and group attribute concepts
     */
    public static List<String> getSeriesAndGroupAttributeConcepts(DataStructureBean dataStructureBean) {
        List<String> attributeConcepts = new ArrayList<String>();
        if (dataStructureBean.getDimensionGroupAttributes() != null) {
            for (AttributeBean currentAttribute : dataStructureBean.getDimensionGroupAttributes()) {
                attributeConcepts.add(currentAttribute.getId());
            }
            for (AttributeBean currentAttribute : dataStructureBean.getGroupAttributes()) {
                if (!attributeConcepts.contains(currentAttribute.getId())) {
                    attributeConcepts.add(currentAttribute.getId());
                }
            }
        }
        return attributeConcepts;
    }

    /**
     * Returns the concepts of all the dimensions and attributes that are not attached at the observation level.
     *
     * @param dataStructureBean the data structure bean
     * @return series attribute concepts
     */
    public static List<String> getSeriesAttributeConcepts(DataStructureBean dataStructureBean) {
        List<String> keyConcepts = new ArrayList<String>();
        for (AttributeBean currentBean : dataStructureBean.getDimensionGroupAttributes()) {
            keyConcepts.add(currentBean.getId());
        }
        return keyConcepts;
    }

    /**
     * Returns the group id, along with a list of concepts that belong to the group key.
     *
     * @param dataStructureBean the data structure bean
     * @return group concepts
     */
    public static Map<String, List<String>> getGroupConcepts(DataStructureBean dataStructureBean) {
        Map<String, List<String>> returnMap = new HashMap<String, List<String>>();
        if (dataStructureBean.getGroups() != null) {
            for (GroupBean currentBean : dataStructureBean.getGroups()) {
                returnMap.put(currentBean.getId(), currentBean.getDimensionRefs());
            }
        }
        return returnMap;
    }

    /**
     * Returns the concepts of all the dimensions and attributes that are not attached at the observation level.
     *
     * @param dataStructureBean the data structure bean
     * @return group attribute concepts
     */
    public static List<String> getGroupAttributeConcepts(DataStructureBean dataStructureBean) {
        List<String> keyConcepts = new ArrayList<String>();
        for (AttributeBean currentBean : dataStructureBean.getGroupAttributes()) {
            keyConcepts.add(currentBean.getId());
        }
        return keyConcepts;
    }

    /**
     * Returns the observation concepts including the measure concept,
     * and all the attribute concepts that are attached to the observation.
     * The measure concept is always the first concept in the list, the others are added in the order they appear
     * in the DataStructureBean.
     *
     * @param dataStructureBean the data structure bean
     * @return observation concepts
     */
    public static List<String> getObservationConcepts(DataStructureBean dataStructureBean) {
        List<String> obsConcepts = new ArrayList<String>();
        obsConcepts.add(getMeasureConcept(dataStructureBean));
        if (dataStructureBean.getAttributes() != null) {
            for (AttributeBean currentAttribute : dataStructureBean.getAttributes()) {
                String conceptId = currentAttribute.getId();
                if (currentAttribute.getAttachmentLevel() == ATTRIBUTE_ATTACHMENT_LEVEL.OBSERVATION) {
                    if (!obsConcepts.contains(conceptId)) {
                        obsConcepts.add(conceptId);
                    }
                }
            }
        }
        return obsConcepts;
    }
}
