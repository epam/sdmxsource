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
package org.sdmxsource.sdmx.sdmxbeans.model.superbeans.metadata;

import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxReferenceException;
import org.sdmxsource.sdmx.api.manager.retrieval.IdentifiableRetrievalManager;
import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.api.model.beans.base.TextTypeWrapper;
import org.sdmxsource.sdmx.api.model.beans.codelist.CodelistBean;
import org.sdmxsource.sdmx.api.model.beans.conceptscheme.ConceptBean;
import org.sdmxsource.sdmx.api.model.beans.metadatastructure.MetadataAttributeBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.metadata.ReportedAttributeBean;
import org.sdmxsource.sdmx.api.model.superbeans.metadata.ReportedAttributeSuperBean;
import org.sdmxsource.sdmx.sdmxbeans.model.superbeans.base.SuperBeanImpl;
import org.sdmxsource.sdmx.util.beans.reference.StructureReferenceBeanImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * The type Reported attribute super bean.
 */
public class ReportedAttributeSuperBeanImpl extends SuperBeanImpl implements ReportedAttributeSuperBean {
    private static final long serialVersionUID = -1884960818440120857L;
    private List<ReportedAttributeSuperBean> reportedAttributes = new ArrayList<ReportedAttributeSuperBean>();
    private ReportedAttributeBean builtFrom;
    private ConceptBean concept;
    private CodelistBean codelist;

    /**
     * Instantiates a new Reported attribute super bean.
     *
     * @param correspondingMA  the corresponding ma
     * @param builtFrom        the built from
     * @param retrievalManager the retrieval manager
     */
    public ReportedAttributeSuperBeanImpl(MetadataAttributeBean correspondingMA, ReportedAttributeBean builtFrom, IdentifiableRetrievalManager retrievalManager) {
        super(builtFrom);
        this.builtFrom = builtFrom;
        concept = retrievalManager.getIdentifiableBean(correspondingMA.getConceptRef(), ConceptBean.class);
        if (correspondingMA.hasCodedRepresentation()) {
            this.codelist = retrievalManager.getIdentifiableBean(correspondingMA.getRepresentation().getRepresentation(), CodelistBean.class);
        }
        if (builtFrom.getReportedAttributes() != null) {
            for (ReportedAttributeBean currentRA : builtFrom.getReportedAttributes()) {
                MetadataAttributeBean maBean = getMetadataAttributeForRepotedAttribute(currentRA, correspondingMA.getMetadataAttributes());
                reportedAttributes.add(new ReportedAttributeSuperBeanImpl(maBean, currentRA, retrievalManager));
            }
        }
    }

    private MetadataAttributeBean getMetadataAttributeForRepotedAttribute(ReportedAttributeBean reportedAttribute, List<MetadataAttributeBean> mAttributeBeans) {
        for (MetadataAttributeBean currentMAttribute : mAttributeBeans) {
            if (currentMAttribute.getId().equals(reportedAttribute.getId())) {
                return currentMAttribute;
            }
        }
        MaintainableBean maint = reportedAttribute.getParent(MaintainableBean.class, false);
        StructureReferenceBean sRef = new StructureReferenceBeanImpl(maint, SDMX_STRUCTURE_TYPE.METADATA_ATTRIBUTE, reportedAttribute.getId());
        throw new SdmxReferenceException(maint, sRef);
    }

    @Override
    public ConceptBean getConcept() {
        return concept;
    }

    @Override
    public CodelistBean getCodelist() {
        return codelist;
    }

    @Override
    public boolean hasCodedRepresentation() {
        return this.codelist != null;
    }

    @Override
    public String getId() {
        return builtFrom.getId();
    }

    @Override
    public String getSimpleValue() {
        return builtFrom.getSimpleValue();
    }

    @Override
    public List<TextTypeWrapper> getMetadataText() {
        return builtFrom.getMetadataText();
    }

    @Override
    public boolean isPresentational() {
        return builtFrom.isPresentational();
    }

    @Override
    public boolean hasSimpleValue() {
        return builtFrom.hasSimpleValue();
    }

    @Override
    public List<ReportedAttributeSuperBean> getReportedAttributes() {
        return new ArrayList<ReportedAttributeSuperBean>(reportedAttributes);
    }

    @Override
    public ReportedAttributeBean getBuiltFrom() {
        return builtFrom;
    }

    @Override
    public Set<MaintainableBean> getCompositeBeans() {
        Set<MaintainableBean> returnSet = super.getCompositeBeans();
        if (concept != null) {
            returnSet.add(concept.getMaintainableParent());
        }
        if (codelist != null) {
            returnSet.add(codelist);
        }
        if (reportedAttributes != null) {
            for (ReportedAttributeSuperBean reportedAttribute : reportedAttributes) {
                returnSet.addAll(reportedAttribute.getCompositeBeans());
            }
        }
        return returnSet;
    }
}
