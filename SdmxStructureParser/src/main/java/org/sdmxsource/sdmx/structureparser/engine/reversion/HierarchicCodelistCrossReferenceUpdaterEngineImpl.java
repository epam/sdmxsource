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

import org.sdmxsource.sdmx.api.model.beans.codelist.HierarchicalCodelistBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.mutable.base.HierarchyMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.codelist.HierarchicalCodelistMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.reference.CodeRefMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.reference.CodelistRefMutableBean;
import org.sdmxsource.sdmx.structureparser.engine.HierarchicCodelistCrossReferenceUpdaterEngine;

import java.util.List;
import java.util.Map;


/**
 * The type Hierarchic codelist cross reference updater engine.
 */
public class HierarchicCodelistCrossReferenceUpdaterEngineImpl implements HierarchicCodelistCrossReferenceUpdaterEngine {

    @Override
    public HierarchicalCodelistBean updateReferences(HierarchicalCodelistBean maintianable, Map<StructureReferenceBean, StructureReferenceBean> updateReferences, String newVersionNumber) {
        HierarchicalCodelistMutableBean hierarchicalCodelistMutableBean = maintianable.getMutableInstance();
        hierarchicalCodelistMutableBean.setVersion(newVersionNumber);

        if (hierarchicalCodelistMutableBean.getCodelistRef() != null) {
            for (CodelistRefMutableBean codelistRef : hierarchicalCodelistMutableBean.getCodelistRef()) {
                if (codelistRef.getCodelistReference() != null) {
                    StructureReferenceBean sRef = updateReferences.get(codelistRef.getCodelistReference());
                    if (sRef != null) {
                        codelistRef.setCodelistReference(sRef);
                    }
                }
            }
        }
        if (hierarchicalCodelistMutableBean.getHierarchies() != null) {
            for (HierarchyMutableBean hierarchyMutable : hierarchicalCodelistMutableBean.getHierarchies()) {
                updateCodeRefs(hierarchyMutable.getHierarchicalCodeBeans(), updateReferences);
            }
        }
        return hierarchicalCodelistMutableBean.getImmutableInstance();
    }

    private void updateCodeRefs(List<CodeRefMutableBean> codeRef, Map<StructureReferenceBean, StructureReferenceBean> updateReferences) {
        if (codeRef != null) {
            for (CodeRefMutableBean currentCodeRef : codeRef) {
                updateCodeRefs(currentCodeRef.getCodeRefs(), updateReferences);

                if (currentCodeRef.getCodeReference() != null) {
                    StructureReferenceBean sRef = updateReferences.get(currentCodeRef.getCodeReference());
                    if (sRef != null) {
                        currentCodeRef.setCodeReference(sRef);
                    }
                }
            }
        }
    }
}
