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
package org.sdmxsource.sdmx.dataparser.transform;

import org.sdmxsource.sdmx.api.engine.DataReaderEngine;
import org.sdmxsource.sdmx.api.engine.DataWriterEngine;
import org.sdmxsource.sdmx.api.model.data.Keyable;
import org.sdmxsource.sdmx.api.model.data.Observation;

import java.util.Date;

/**
 * Supports data transformation
 */
public interface DataReaderWriterTransform {


    /**
     * Copy to writer.
     *
     * @param dataReaderEngine  the data reader engine
     * @param dataWriterEngine  the data writer engine
     * @param includeObs        the include obs
     * @param includeattributes the includeattributes
     * @param maxObs            the max obs
     * @param dateFrom          the date from
     * @param dateTo            the date to
     * @param copyHeader        the copy header
     * @param closeWriter       the close writer
     */
    void copyToWriter(DataReaderEngine dataReaderEngine,
                      DataWriterEngine dataWriterEngine,
                      boolean includeObs,
                      boolean includeattributes,
                      Integer maxObs,
                      Date dateFrom,
                      Date dateTo,
                      boolean copyHeader,
                      boolean closeWriter);

    /**
     * Copy dataset to writer.
     *
     * @param dataReaderEngine  the data reader engine
     * @param dataWriterEngine  the data writer engine
     * @param pivotDimension    the pivot dimension
     * @param includeObs        the include obs
     * @param maxObs            the max obs
     * @param dateFrom          the date from
     * @param dateTo            the date to
     * @param includeHeader     the include header
     * @param closeOnCompletion the close on completion
     */
    public void copyDatasetToWriter(DataReaderEngine dataReaderEngine,
                                    DataWriterEngine dataWriterEngine,
                                    String pivotDimension,
                                    boolean includeObs,
                                    Integer maxObs,
                                    Date dateFrom,
                                    Date dateTo,
                                    boolean includeHeader,
                                    boolean closeOnCompletion);

    /**
     * Copies the data held in the data reader engine to the data writer engine verbatium.
     * <p>
     * This will make an initial call on the reader engine to reset the position back to the start to ensure a full copy
     * <p>
     * The writer engine will be closed on completion, the reader engine will NOT be closed
     *
     * @param dataReaderEngine - the reader engine to read the data from
     * @param dataWriterEngine - the writer engine to write the data to
     * @param copyHeader       the copy header
     * @param closeWriter      the close writer
     */
    void copyToWriter(DataReaderEngine dataReaderEngine, DataWriterEngine dataWriterEngine, boolean copyHeader, boolean closeWriter);

    /**
     * Copies the data held in the data reader engine to the data writer engine - pivoting the data on the dimension supplied.
     * <p>
     * This will make an initial call on the reader engine to reset the position back to the start to ensure a full copy
     * <p>
     * The writer engine will be closed on completion, the reader engine will NOT be closed
     *
     * @param dataReaderEngine - the reader engine to read the data from
     * @param dataWriterEngine - the writer engine to write the data to
     * @param pivotDimnsion    the pivot dimnsion
     * @param closeWriter      the close writer
     */
    void copyToWriter(DataReaderEngine dataReaderEngine, DataWriterEngine dataWriterEngine, String pivotDimnsion, boolean closeWriter);


    /**
     * Writes the keyable to the writer engine, if the key is a series then it will also write any observations under the series
     *
     * @param dataReaderEngine the data reader engine
     * @param dataWriterEngine the data writer engine
     * @param keyable          the keyable
     * @param maxObs           if 0 or null then do not output observations, if less then 0 then there is no limit, if greater then 0 then limit the max obs to this number
     */
    void writeKeyableToWriter(DataReaderEngine dataReaderEngine, DataWriterEngine dataWriterEngine, Keyable keyable, Integer maxObs);

    /**
     * Writes the keyable to the writer engine, if the key is a series then it will also write any observations under the series.
     * <p>
     * Only writes the observations that fall between the two date parameters
     *
     * @param dataReaderEngine  the data reader engine
     * @param dataWriterEngine  the data writer engine
     * @param keyable           the keyable
     * @param maxObs            if 0 then do not output observations, if null or less then 0 then there is no limit, if greater then 0 then limit the max obs to this number
     * @param dateFrom          the date from
     * @param dateTo            the date to
     * @param includeattributes the includeattributes
     */
    void writeKeyableToWriter(DataReaderEngine dataReaderEngine, DataWriterEngine dataWriterEngine, Keyable keyable, Integer maxObs, Date dateFrom, Date dateTo,
                              boolean includeattributes);

    /**
     * Writes the keyable to the writer engine, if the key is a series then it will also write any observations under the series.
     * <p>
     * Only writes the observations that fall between the two date parameters
     *
     * @param dataReaderEngine the data reader engine
     * @param dataWriterEngine the data writer engine
     * @param keyable          the keyable
     * @param maxObs           if 0 then do not output observations, if null or less then 0 then there is no limit, if greater then 0 then limit the max obs to this number
     * @param dateFrom         the date from
     * @param dateTo           the date to
     */
    void writeKeyableToWriter(DataReaderEngine dataReaderEngine, DataWriterEngine dataWriterEngine, Keyable keyable, Integer maxObs, Date dateFrom, Date dateTo);

    /**
     * Writes the observation to the writer engine
     *
     * @param dataWriterEngine  the data writer engine
     * @param keyable           the keyable
     * @param obs               the obs
     * @param includeattributes the includeattributes
     */
    void writeObsToWriter(DataWriterEngine dataWriterEngine, Keyable keyable, Observation obs,
                          boolean includeattributes);

    /**
     * Writes the observation to the writer engine
     *
     * @param dataWriterEngine the data writer engine
     * @param keyable          the keyable
     * @param obs              the obs
     */
    void writeObsToWriter(DataWriterEngine dataWriterEngine, Keyable keyable, Observation obs);
}
