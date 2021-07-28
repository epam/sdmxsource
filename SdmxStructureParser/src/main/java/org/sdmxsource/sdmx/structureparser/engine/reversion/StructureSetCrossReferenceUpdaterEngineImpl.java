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

import org.sdmxsource.sdmx.api.model.beans.mapping.StructureSetBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.mutable.base.SchemeMapMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.mapping.RelatedStructuresMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.mapping.StructureSetMutableBean;
import org.sdmxsource.sdmx.structureparser.engine.StructureSetCrossReferenceUpdaterEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * The type Structure set cross reference updater engine.
 */
public class StructureSetCrossReferenceUpdaterEngineImpl implements StructureSetCrossReferenceUpdaterEngine {

    @Override
    public StructureSetBean updateReferences(StructureSetBean maintianable, Map<StructureReferenceBean, StructureReferenceBean> updateReferences, String newVersionNumber) {
        StructureSetMutableBean ss = maintianable.getMutableInstance();
        ss.setVersion(newVersionNumber);

        updateSchemeMap(ss.getCategorySchemeMapList(), updateReferences);
        updateSchemeMap(ss.getCodelistMapList(), updateReferences);
        updateSchemeMap(ss.getConceptSchemeMapList(), updateReferences);
        updateSchemeMap(ss.getOrganisationSchemeMapList(), updateReferences);
        updateSchemeMap(ss.getStructureMapList(), updateReferences);

        if (ss.getRelatedStructures() != null) {
            RelatedStructuresMutableBean relatedStructures = ss.getRelatedStructures();
            relatedStructures.setCategorySchemeRef(updateRelatedStructures(relatedStructures.getCategorySchemeRef(), updateReferences));
            relatedStructures.setConceptSchemeRef(updateRelatedStructures(relatedStructures.getConceptSchemeRef(), updateReferences));
            relatedStructures.setHierCodelistRef(updateRelatedStructures(relatedStructures.getHierCodelistRef(), updateReferences));
            relatedStructures.setDataStructureRef(updateRelatedStructures(relatedStructures.getDataStructureRef(), updateReferences));
            relatedStructures.setMetadataStructureRef(updateRelatedStructures(relatedStructures.getMetadataStructureRef(), updateReferences));
            relatedStructures.setOrgSchemeRef(updateRelatedStructures(relatedStructures.getOrgSchemeRef(), updateReferences));
        }

        return ss.getImmutableInstance();
    }

    private List<StructureReferenceBean> updateRelatedStructures(List<StructureReferenceBean> sRefList, Map<StructureReferenceBean, StructureReferenceBean> updateReferences) {
        List<StructureReferenceBean> newReferences = new ArrayList<StructureReferenceBean>();
        if (sRefList != null) {
            for (StructureReferenceBean currentSRef : sRefList) {
                StructureReferenceBean updatedRef = updateReferences.get(currentSRef);
                if (updatedRef != null) {
                    newReferences.add(updatedRef);
                } else {
                    newReferences.add(currentSRef);
                }
            }
        }
        return newReferences;
    }

    private void updateSchemeMap(List<? extends SchemeMapMutableBean> schemeMaps, Map<StructureReferenceBean, StructureReferenceBean> updateReferences) {
        if (schemeMaps != null) {
            for (SchemeMapMutableBean currentMap : schemeMaps) {
                StructureReferenceBean newTarget = updateReferences.get(currentMap.getSourceRef());
                if (newTarget != null) {
                    currentMap.setSourceRef(newTarget);
                }
                newTarget = updateReferences.get(currentMap.getTargetRef());
                if (newTarget != null) {
                    currentMap.setTargetRef(newTarget);
                }
            }
        }
    }
}
