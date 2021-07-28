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
package org.sdmxsource.sdmx.dataparser.engine.impl;

import org.sdmxsource.sdmx.api.constants.TIME_FORMAT;
import org.sdmxsource.sdmx.api.engine.DataReaderEngine;
import org.sdmxsource.sdmx.api.model.data.Observation;
import org.sdmxsource.sdmx.dataparser.engine.ReportedDateEngine;

import java.util.*;


/**
 * The type Reported date engine.
 */
public class ReportedDateEngineImpl implements ReportedDateEngine {

    @Override
    public Map<TIME_FORMAT, List<String>> getAllReportedDates(DataReaderEngine dataReaderEngine) {
        dataReaderEngine.reset();

        Set<String> processedDates = new HashSet<String>();

        Map<TIME_FORMAT, Map<Date, String>> timeFormatToSortedMap = new HashMap<TIME_FORMAT, Map<Date, String>>();
        try {
            while (dataReaderEngine.moveNextKeyable()) {
                while (dataReaderEngine.moveNextObservation()) {
                    Observation obs = dataReaderEngine.getCurrentObservation();
                    String obsTime = obs.getObsTime();

                    //Check we have not already procesed this date
                    if (!processedDates.contains(obsTime)) {
                        TIME_FORMAT obsTimeFormat = obs.getObsTimeFormat();
                        Map<Date, String> sortedMap;

                        //Get the correct sorted map (or create it if it does not yet exist)
                        if (timeFormatToSortedMap.containsKey(obsTimeFormat)) {
                            sortedMap = timeFormatToSortedMap.get(obsTimeFormat);
                        } else {
                            sortedMap = new TreeMap<Date, String>();
                            timeFormatToSortedMap.put(obsTimeFormat, sortedMap);
                        }
                        sortedMap.put(obs.getObsAsTimeDate(), obsTime);
                        processedDates.add(obsTime);
                    }
                }
            }

            //Create The ReponseMap
            Map<TIME_FORMAT, List<String>> responseMap = new HashMap<TIME_FORMAT, List<String>>();
            for (TIME_FORMAT currentTimeFormat : timeFormatToSortedMap.keySet()) {
                List<String> sortedDateList = new ArrayList<String>();
                responseMap.put(currentTimeFormat, sortedDateList);
                Map<Date, String> sortedMap = timeFormatToSortedMap.get(currentTimeFormat);
                for (Date sortedMapKey : sortedMap.keySet()) {
                    sortedDateList.add(sortedMap.get(sortedMapKey));
                }
            }
            return responseMap;
        } finally {
            dataReaderEngine.reset();
        }
    }

}
