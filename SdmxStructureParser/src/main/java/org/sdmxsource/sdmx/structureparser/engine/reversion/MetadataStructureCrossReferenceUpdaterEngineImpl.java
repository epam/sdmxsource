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

import org.sdmxsource.sdmx.api.model.beans.metadatastructure.MetadataStructureDefinitionBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.mutable.metadatastructure.*;
import org.sdmxsource.sdmx.structureparser.engine.MetadataStructureCrossReferenceUpdaterEngine;

import java.util.List;
import java.util.Map;


/**
 * The type Metadata structure cross reference updater engine.
 */
public class MetadataStructureCrossReferenceUpdaterEngineImpl extends RepresentationCrossReferenceUpdater implements MetadataStructureCrossReferenceUpdaterEngine {

    @Override
    public MetadataStructureDefinitionBean updateReferences(MetadataStructureDefinitionBean maintianable, Map<StructureReferenceBean, StructureReferenceBean> updateReferences,
                                                            String newVersionNumber) {
        MetadataStructureDefinitionMutableBean msdMutable = maintianable.getMutableInstance();
        msdMutable.setVersion(newVersionNumber);

        if (msdMutable.getMetadataTargets() != null) {
            for (MetadataTargetMutableBean metadataTarget : msdMutable.getMetadataTargets()) {
                if (metadataTarget.getIdentifiableTargetBean() != null) {
                    for (IdentifiableTargetMutableBean identifiableTarget : metadataTarget.getIdentifiableTargetBean()) {
                        updateRepresentationReference(identifiableTarget.getRepresentation(), updateReferences);

                        if (identifiableTarget.getConceptRef() != null) {
                            StructureReferenceBean sRef = updateReferences.get(identifiableTarget.getConceptRef());
                            if (sRef != null) {
                                identifiableTarget.setConceptRef(sRef);
                            }
                        }
                    }
                }
            }
        }
        if (msdMutable.getReportStructures() != null) {
            for (ReportStructureMutableBean rs : msdMutable.getReportStructures()) {
                updateMetadataAttributes(rs.getMetadataAttributes(), updateReferences);
            }
        }
        return msdMutable.getImmutableInstance();
    }

    private void updateMetadataAttributes(List<MetadataAttributeMutableBean> metadataAttributes, Map<StructureReferenceBean, StructureReferenceBean> updateReferences) {
        if (metadataAttributes != null) {
            for (MetadataAttributeMutableBean currentMa : metadataAttributes) {
                updateRepresentationReference(currentMa.getRepresentation(), updateReferences);

                if (currentMa.getConceptRef() != null) {
                    StructureReferenceBean sRef = updateReferences.get(currentMa.getConceptRef());
                    if (sRef != null) {
                        currentMa.setConceptRef(sRef);
                    }
                }
            }
        }
    }
}
