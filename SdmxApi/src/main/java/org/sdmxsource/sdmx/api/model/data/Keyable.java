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
package org.sdmxsource.sdmx.api.model.data;

import org.sdmxsource.sdmx.api.constants.TIME_FORMAT;
import org.sdmxsource.sdmx.api.model.beans.base.AnnotationBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataStructureBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataflowBean;

import java.util.Date;
import java.util.List;


/**
 * A Keyable is a dataset item that is represented by a Key.  The only two items are SeriesKey and GroupKey, and this
 * item could reflect either, to determine if this is a seriesKey use the <code>isSeries()</code> method.
 *
 * @author Matt Nelson
 */
public interface Keyable extends Attributable {

    /**
     * Returns the data structure to which this key belongs, this will not be null
     *
     * @return data structure
     */
    DataStructureBean getDataStructure();

    /**
     * Returns the dataflow to which this key belongs.  This may be null
     * if the dataflow is unknown or not applicable.
     *
     * @return dataflow
     */
    DataflowBean getDataflow();

    /**
     * Returns a colon delimited string representing this key.
     * <p>
     * Example - if the Key Values are:
     * 1. FREQ = A
     * 2. COUNTRY = UK
     * 3. SEX = M
     * <p>
     * The Short code would be A:UK:M
     *
     * @return short code
     */
    String getShortCode();

    /**
     * Returns the list of key values for this key
     *
     * @return key
     */
    List<KeyValue> getKey();

    /**
     * Returns the key value for the dimension Id - returns null if the dimension id is not part of the key
     *
     * @param dimensionId the dimension id
     * @return key value
     */
    String getKeyValue(String dimensionId);

    /**
     * Returns true if this key belongs to a series key, if false then it belongs to a group
     *
     * @return boolean
     */
    boolean isSeries();

    /**
     * Returns true if this key is a time series key, if false, then this KeyableArtifact will contain
     * a time
     *
     * @return boolean
     */
    boolean isTimeSeries();

    /**
     * Returns the time format for the key, returns null if this is unknown
     *
     * @return time format
     */
    TIME_FORMAT getTimeFormat();

    /**
     * Reutns the name (or id) of this group, if this is a group (i.e. isSeries() returns null)
     *
     * @return group name
     */
    String getGroupName();

    /**
     * Returns the concept that is being cross sectionalised on , this is only relevant if isTimeSeries() is false.
     * <p>
     * If isTimeSeries() is true, then this call will return null
     *
     * @return cross section concept
     */
    String getCrossSectionConcept();

    /**
     * Returns the observation time in a SDMX time format, this is only relevant if isTimeSeries() is false.
     * <p>
     * If isTimeSeries() is true, then this call will return null
     *
     * @return obs time
     */
    String getObsTime();

    /**
     * Returns the observation time as a Date Object, this is only relevant if isTimeSeries() is false.
     * <p>
     * If isTimeSeries() is true, then this call will return null
     *
     * @return obs as time date
     */
    Date getObsAsTimeDate();

    /**
     * Returns a copy of the annotations list.
     *
     * @return annotations, or an empty list if there are none
     */
    List<AnnotationBean> getAnnotations();


}
