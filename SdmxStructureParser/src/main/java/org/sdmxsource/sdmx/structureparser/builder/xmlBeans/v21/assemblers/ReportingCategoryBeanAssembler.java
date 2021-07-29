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
package org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v21.assemblers;

import org.sdmx.resources.sdmxml.schemas.v21.structure.ReportingCategoryType;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.model.beans.categoryscheme.ReportingCategoryBean;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.util.ObjectUtil;


/**
 * Assembles a ReportingCategoryBean to facilitate Builder&lt;K,V&gt; for the top level bean.
 * v2.1 SDMX uses inheritance, so thus can we, unlike in previous versions.
 * Builder&lt;K,V&gt; doesn't lend itself to inheritance.
 */
public class ReportingCategoryBeanAssembler extends MaintainableBeanAssembler implements Assembler<ReportingCategoryType, ReportingCategoryBean> {

    @Override
    public void assemble(ReportingCategoryType assembleInto, ReportingCategoryBean assembleFrom) throws SdmxException {
        // Populate it from inherited super - NOTE downgraded to identifiable from nameable
        assembleNameable(assembleInto, assembleFrom);
        if (ObjectUtil.validCollection(assembleFrom.getStructuralMetadata())) {
            for (CrossReferenceBean xsRef : assembleFrom.getStructuralMetadata()) {
                setReference(assembleInto.addNewStructuralMetadata().addNewRef(), xsRef);
            }
        }
        if (ObjectUtil.validCollection(assembleFrom.getProvisioningMetadata())) {
            for (CrossReferenceBean xsRef : assembleFrom.getProvisioningMetadata()) {
                setReference(assembleInto.addNewProvisioningMetadata().addNewRef(), xsRef);
            }
        }
        for (ReportingCategoryBean eachReportingCategoryBean : assembleFrom.getItems()) {
            ReportingCategoryType eachReportingCategory = assembleInto.addNewReportingCategory();
            this.assemble(eachReportingCategory, eachReportingCategoryBean);
        }
    }

}
