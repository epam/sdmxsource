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
package org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v21;

import org.sdmx.resources.sdmxml.schemas.v21.structure.ReportingCategoryType;
import org.sdmx.resources.sdmxml.schemas.v21.structure.ReportingTaxonomyType;
import org.sdmxsource.sdmx.api.builder.Builder;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.model.beans.categoryscheme.ReportingCategoryBean;
import org.sdmxsource.sdmx.api.model.beans.categoryscheme.ReportingTaxonomyBean;
import org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v21.assemblers.MaintainableBeanAssembler;
import org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v21.assemblers.ReportingCategoryBeanAssembler;


/**
 * The type Reporting taxonomy xml bean builder.
 */
public class ReportingTaxonomyXmlBeanBuilder extends MaintainableBeanAssembler implements Builder<ReportingTaxonomyType, ReportingTaxonomyBean> {

    private final ReportingCategoryBeanAssembler reportingCategoryBeanAssemblerBean = new ReportingCategoryBeanAssembler();

    @Override
    public ReportingTaxonomyType build(ReportingTaxonomyBean buildFrom) throws SdmxException {
        // Create outgoing build
        ReportingTaxonomyType returnType = ReportingTaxonomyType.Factory.newInstance();
        if (buildFrom.isPartial()) {
            returnType.setIsPartial(true);
        }
        // Populate it from inherited super
        assembleMaintainable(returnType, buildFrom);
        // Populate it using this class's specifics
        for (ReportingCategoryBean eachReportingCategoryBean : buildFrom.getItems()) {
            ReportingCategoryType newReportingCategoryType = returnType.addNewReportingCategory();
            reportingCategoryBeanAssemblerBean.assemble(newReportingCategoryType, eachReportingCategoryBean);
        }
        return returnType;
    }
}
