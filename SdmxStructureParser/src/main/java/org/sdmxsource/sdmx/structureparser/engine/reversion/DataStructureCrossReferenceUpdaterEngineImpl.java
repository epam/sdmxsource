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
package org.sdmxsource.sdmx.structureparser.engine.reversion;

import org.sdmxsource.sdmx.api.model.beans.datastructure.DataStructureBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.mutable.datastructure.DataStructureMutableBean;
import org.sdmxsource.sdmx.structureparser.engine.DataStructureCrossReferenceUpdaterEngine;

import java.util.Map;


/**
 * The type Data structure cross reference updater engine.
 */
public class DataStructureCrossReferenceUpdaterEngineImpl extends ComponentCrossReferenceUpdater implements DataStructureCrossReferenceUpdaterEngine {

    @Override
    public DataStructureBean updateReferences(DataStructureBean maintianable, Map<StructureReferenceBean, StructureReferenceBean> updateReferences, String newVersionNumber) {
        DataStructureMutableBean dsd = maintianable.getMutableInstance();
        dsd.setVersion(newVersionNumber);

        if (dsd.getDimensionList() != null && dsd.getDimensionList().getDimensions() != null) {
            updateComponentReferneces(dsd.getDimensionList().getDimensions(), updateReferences);
        }
        if (dsd.getAttributeList() != null && dsd.getAttributeList().getAttributes() != null) {
            updateComponentReferneces(dsd.getAttributeList().getAttributes(), updateReferences);
        }
        if (dsd.getMeasureList() != null && dsd.getMeasureList().getPrimaryMeasure() != null) {
            updateComponentReferneces(dsd.getMeasureList().getPrimaryMeasure(), updateReferences);
        }
        return dsd.getImmutableInstance();
    }

}
