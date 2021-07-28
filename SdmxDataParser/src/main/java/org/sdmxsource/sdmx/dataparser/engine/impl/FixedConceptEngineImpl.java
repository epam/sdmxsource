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

import org.sdmxsource.sdmx.api.engine.DataReaderEngine;
import org.sdmxsource.sdmx.api.model.data.KeyValue;
import org.sdmxsource.sdmx.api.model.data.Keyable;
import org.sdmxsource.sdmx.api.model.data.Observation;
import org.sdmxsource.sdmx.dataparser.engine.FixedConceptEngine;
import org.sdmxsource.sdmx.sdmxbeans.model.data.KeyValueImpl;

import java.util.*;


/**
 * The type Fixed concept engine.
 */
public class FixedConceptEngineImpl implements FixedConceptEngine {

    @Override
    public List<KeyValue> getFixedConcepts(DataReaderEngine dre, boolean includeObs, boolean includeAttributes) {
        dre.reset();

        Map<String, String> conceptMap = new HashMap<String, String>();
        Set<String> skipConcepts = new HashSet<String>();

        while (dre.moveNextKeyable()) {
            Keyable key = dre.getCurrentKey();
            if (includeAttributes) {
                processKeyValues(key.getAttributes(), conceptMap, skipConcepts);
            }
            processKeyValues(key.getKey(), conceptMap, skipConcepts);
            if (includeObs) {
                while (dre.moveNextObservation()) {
                    Observation obs = dre.getCurrentObservation();
                    if (includeAttributes) {
                        processKeyValues(obs.getAttributes(), conceptMap, skipConcepts);
                    }
                    if (obs.isCrossSection()) {
                        processKeyValue(obs.getCrossSectionalValue(), conceptMap, skipConcepts);
                    }
                }
            }
        }
        List<KeyValue> fixedKeyValues = new ArrayList<KeyValue>();
        for (String fixedConcept : conceptMap.keySet()) {
            fixedKeyValues.add(new KeyValueImpl(conceptMap.get(fixedConcept), fixedConcept));
        }
        return fixedKeyValues;
    }

    private void processKeyValues(List<KeyValue> kvs, Map<String, String> conceptMap, Set<String> skipConcepts) {
        for (KeyValue kv : kvs) {
            processKeyValue(kv, conceptMap, skipConcepts);
        }
    }

    private void processKeyValue(KeyValue kv, Map<String, String> conceptMap, Set<String> skipConcepts) {
        String currentConcept = kv.getConcept();
        if (skipConcepts.contains(currentConcept)) {
            return;
        }
        if (!conceptMap.containsKey(currentConcept)) {
            conceptMap.put(currentConcept, kv.getCode());
            return;
        }
        if (!conceptMap.get(currentConcept).equals(kv.getCode())) {
            conceptMap.remove(currentConcept);
            skipConcepts.add(currentConcept);
        }
    }
}
