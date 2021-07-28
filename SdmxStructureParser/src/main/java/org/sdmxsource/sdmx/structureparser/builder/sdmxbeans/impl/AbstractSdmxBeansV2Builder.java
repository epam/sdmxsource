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
package org.sdmxsource.sdmx.structureparser.builder.sdmxbeans.impl;

import org.sdmx.resources.sdmxml.schemas.v20.structure.*;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.MaintainableBeanException;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;
import org.sdmxsource.sdmx.api.model.beans.categoryscheme.CategoryBean;
import org.sdmxsource.sdmx.api.model.beans.categoryscheme.CategorySchemeBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataflowBean;
import org.sdmxsource.sdmx.api.model.beans.metadatastructure.MetadataFlowBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.categoryscheme.CategorisationBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.categoryscheme.CategorySchemeBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.datastructure.DataflowBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.metadatastructure.MetadataflowBeanImpl;
import org.sdmxsource.util.ObjectUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * The type Abstract sdmx beans v 2 builder.
 */
public abstract class AbstractSdmxBeansV2Builder extends AbstractSdmxBeansBuilder {

    ///////////////////////////////////////////////////////////////////////////////////////////////
    //////////			VERSION 2.0 METHODS FOR STRUCTURES      	///////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Creates category schemes and categorisations based on the input category schemes
     *
     * @param catSchemes - if null will not add anything to the beans container
     * @param beans      - to add category schemes and categorisations to
     */
    protected void processCategorySchemes(CategorySchemesType catSchemes, SdmxBeans beans) {
        Set<String> urns = new HashSet<String>();
        if (catSchemes != null && ObjectUtil.validCollection(catSchemes.getCategorySchemeList())) {
            for (CategorySchemeType currentType : catSchemes.getCategorySchemeList()) {
                try {
                    CategorySchemeBean csBean = new CategorySchemeBeanImpl(currentType);
                    addIfNotDuplicateURN(beans, urns, csBean);
                    if (currentType.getCategoryList() != null) {
                        processCategory(beans, currentType.getCategoryList(), csBean.getItems());
                    }
                } catch (Throwable th) {
                    throw new MaintainableBeanException(th, SDMX_STRUCTURE_TYPE.CATEGORY_SCHEME, currentType.getAgencyID(), currentType.getId(), currentType.getVersion());
                }
            }
        }
    }

    /**
     * Creates dataflows and categorisations based on the input dataflows
     *
     * @param dfType - if null will not add anything to the beans container
     * @param beans  - to add dataflows and categorisations to
     */
    protected void processDataflows(DataflowsType dfType, SdmxBeans beans) {
        Set<String> urns = new HashSet<String>();
        if (dfType != null && ObjectUtil.validCollection(dfType.getDataflowList())) {
            for (DataflowType currentType : dfType.getDataflowList()) {
                try {
                    DataflowBean currentDataflow = new DataflowBeanImpl(currentType);
                    addIfNotDuplicateURN(beans, urns, currentDataflow);

                    //CATEGORISATIONS FROM DATAFLOWS
                    if (currentType.getCategoryRefList() != null) {
                        for (CategoryRefType cateogryRefType : currentType.getCategoryRefList()) {
                            beans.addCategorisation(new CategorisationBeanImpl(currentDataflow, cateogryRefType));
                        }
                    }
                } catch (Throwable th) {
                    throw new MaintainableBeanException(th, SDMX_STRUCTURE_TYPE.DATAFLOW, currentType.getAgencyID(), currentType.getId(), currentType.getVersion());
                }
            }
        }
    }

    /**
     * Creates metadataflows and categorisations based on the input metadataflows
     *
     * @param mdfType - if null will not add anything to the beans container
     * @param beans   - to add metadataflow and categorisations to
     */
    protected void processMetadataFlows(MetadataflowsType mdfType, SdmxBeans beans) {
        Set<String> urns = new HashSet<String>();
        if (mdfType != null && ObjectUtil.validCollection(mdfType.getMetadataflowList())) {
            for (MetadataflowType currentType : mdfType.getMetadataflowList()) {
                try {
                    MetadataFlowBean currentMetadataflow = new MetadataflowBeanImpl(currentType);

                    addIfNotDuplicateURN(beans, urns, currentMetadataflow);

                    //CATEGORISATIONS FROM METADATAFLOWS
                    if (currentType.getCategoryRefList() != null) {
                        for (CategoryRefType cateogryRefType : currentType.getCategoryRefList()) {
                            beans.addCategorisation(new CategorisationBeanImpl(currentMetadataflow, cateogryRefType));
                        }
                    }
                } catch (Throwable th) {
                    throw new MaintainableBeanException(th, SDMX_STRUCTURE_TYPE.METADATA_FLOW, currentType.getAgencyID(), currentType.getId(), currentType.getVersion());
                }
            }
        }
    }


    /**
     * Create Categorisations from Version 2.0 categories, adds the categorisations to the beans container
     *
     * @param beans         container to add to
     * @param categoryTypes category types to filter on
     * @param categoryBeans category beans to process
     */
    protected void processCategory(SdmxBeans beans, List<CategoryType> categoryTypes, List<CategoryBean> categoryBeans) {
        if (categoryTypes != null) {
            for (CategoryType cat : categoryTypes) {
                CategoryBean processingCatBean = null;
                for (CategoryBean currentCatBean : categoryBeans) {
                    if (currentCatBean.getId().equals(cat.getId())) {
                        processingCatBean = currentCatBean;
                        break;
                    }
                }
                processCategory(beans, cat, processingCatBean);
            }
        }
    }

    /**
     * Create Categorisations from Version 2.0 category, adds the categorisation to the beans container
     *
     * @param beans        container to add to
     * @param categoryType the category type
     * @param categoryBean the category bean
     */
    protected void processCategory(SdmxBeans beans, CategoryType categoryType, CategoryBean categoryBean) {
        processCategory(beans, categoryType.getCategoryList(), categoryBean.getItems());

        if (categoryType.getDataflowRefList() != null) {
            for (DataflowRefType dfRef : categoryType.getDataflowRefList()) {
                beans.addCategorisation(new CategorisationBeanImpl(categoryBean, dfRef));
            }
        }
        if (categoryType.getMetadataflowRefList() != null) {
            for (MetadataflowRefType mdfRef : categoryType.getMetadataflowRefList()) {
                beans.addCategorisation(new CategorisationBeanImpl(categoryBean, mdfRef));
            }
        }
    }


}
