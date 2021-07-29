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

import org.sdmxsource.sdmx.api.model.beans.categoryscheme.ReportingTaxonomyBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.mutable.categoryscheme.ReportingCategoryMutableBean;
import org.sdmxsource.sdmx.api.model.mutable.categoryscheme.ReportingTaxonomyMutableBean;
import org.sdmxsource.sdmx.structureparser.engine.ReportingTaxonomyBeanCrossReferenceUpdaterEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * The type Reporting taxonomy bean cross reference updater engine.
 */
public class ReportingTaxonomyBeanCrossReferenceUpdaterEngineImpl implements ReportingTaxonomyBeanCrossReferenceUpdaterEngine {

    @Override
    public ReportingTaxonomyBean updateReferences(ReportingTaxonomyBean maintianable, Map<StructureReferenceBean, StructureReferenceBean> updateReferences, String newVersionNumber) {
        ReportingTaxonomyMutableBean reportingTaxonomy = maintianable.getMutableInstance();
        reportingTaxonomy.setVersion(newVersionNumber);

        updateReportingCategories(reportingTaxonomy.getItems(), updateReferences);

        return reportingTaxonomy.getImmutableInstance();
    }

    private void updateReportingCategories(List<ReportingCategoryMutableBean> reportingCategories, Map<StructureReferenceBean, StructureReferenceBean> updateReferences) {
        if (reportingCategories != null) {
            for (ReportingCategoryMutableBean reportingCategory : reportingCategories) {
                updateReportingCategories(reportingCategory.getItems(), updateReferences);
                reportingCategory.setProvisioningMetadata(updateRelatedStructures(reportingCategory.getProvisioningMetadata(), updateReferences));
                reportingCategory.setStructuralMetadata(updateRelatedStructures(reportingCategory.getStructuralMetadata(), updateReferences));
            }
        }

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

}
