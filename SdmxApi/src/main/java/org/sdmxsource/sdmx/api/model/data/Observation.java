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

import java.util.Date;
import java.util.List;


/**
 * An Observation is a data element that defines a time and value, an Observation may also have attributes attached to it.
 * <p>
 * An Observation can be compared to another, with regards to time.  An observation with a later time period, will return a value &gt; 1
 *
 * @author Matt Nelson
 */
public interface Observation extends Attributable, Comparable<Observation> {

    /**
     * Returns the parent series key for this observation.  The returned object can not be null.
     *
     * @return series key
     */
    Keyable getSeriesKey();

    /**
     * Returns the value of the observation
     *
     * @return observation value
     */
    String getObservationValue();

    /**
     * Returns the observation time in a SDMX time format
     *
     * @return obs time
     */
    String getObsTime();

    /**
     * Returns true if this is a cross sectional observation,
     * the call to get the obs time will still return the observation time of the cross section, also
     * available on the series key, however there is an additional cross sectional attribute available
     *
     * @return boolean
     */
    boolean isCrossSection();

    /**
     * Returns the cross sectional key value
     *
     * @return cross sectional value
     */
    KeyValue getCrossSectionalValue();

    /**
     * Returns the time format the observation value is in
     *
     * @return obs time format
     */
    TIME_FORMAT getObsTimeFormat();

    /**
     * Returns the observation time as a Date Object
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
