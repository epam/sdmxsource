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
package org.sdmxsource.sdmx.structureretrieval.manager;

import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.CrossReferenceException;
import org.sdmxsource.sdmx.api.exception.SdmxReferenceException;
import org.sdmxsource.sdmx.api.manager.retrieval.ExternalReferenceRetrievalManager;
import org.sdmxsource.sdmx.api.manager.retrieval.IdentifiableRetrievalManager;
import org.sdmxsource.sdmx.api.manager.retrieval.SdmxBeanRetrievalManager;
import org.sdmxsource.sdmx.api.model.beans.base.AgencyBean;
import org.sdmxsource.sdmx.api.model.beans.base.AgencySchemeBean;
import org.sdmxsource.sdmx.api.model.beans.base.IdentifiableBean;
import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.util.beans.reference.StructureReferenceBeanImpl;

import java.util.HashSet;
import java.util.Set;


/**
 * The type Identifiable retrieval manager.
 */
public class IdentifiableRetrievalManagerImpl implements IdentifiableRetrievalManager {
    /**
     * The Retrieval manager.
     */
    protected SdmxBeanRetrievalManager retrievalManager;
    /**
     * The External reference retrieval manager.
     */
    protected ExternalReferenceRetrievalManager externalReferenceRetrievalManager;


    /**
     * Instantiates a new Identifiable retrieval manager.
     */
    public IdentifiableRetrievalManagerImpl() {
    }

    /**
     * Instantiates a new Identifiable retrieval manager.
     *
     * @param retrievalManager the retrieval manager
     */
    public IdentifiableRetrievalManagerImpl(SdmxBeanRetrievalManager retrievalManager) {
        this(retrievalManager, null);
    }

    /**
     * Instantiates a new Identifiable retrieval manager.
     *
     * @param retrievalManager                  the retrieval manager
     * @param externalReferenceRetrievalManager the external reference retrieval manager
     */
    public IdentifiableRetrievalManagerImpl(SdmxBeanRetrievalManager retrievalManager, ExternalReferenceRetrievalManager externalReferenceRetrievalManager) {
        this.retrievalManager = retrievalManager;
        this.externalReferenceRetrievalManager = externalReferenceRetrievalManager;
    }


    @Override
    public AgencyBean getAgency(String id) {
        AgencySchemeBean acySch = null;

        String agencyId = id;
        String agencyParentId = AgencySchemeBean.DEFAULT_SCHEME;
        if (id.contains(".")) {
            //Sub Agency, get parent Scheme
            int lastDotIdx = id.lastIndexOf(".");
            agencyParentId = id.substring(0, lastDotIdx);
            agencyId = id.substring(lastDotIdx + 1);
        }
        StructureReferenceBean sRef = new StructureReferenceBeanImpl(agencyParentId, AgencySchemeBean.FIXED_ID, AgencySchemeBean.FIXED_VERSION, SDMX_STRUCTURE_TYPE.AGENCY_SCHEME);
        acySch = getIdentifiableBean(sRef, AgencySchemeBean.class);
        if (acySch != null) {
            for (AgencyBean acy : acySch.getItems()) {
                if (acy.getId().equals(agencyId)) {
                    return acy;
                }
            }
        }
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getIdentifiableBean(CrossReferenceBean crossRef, Class<T> structureType) throws CrossReferenceException {
        IdentifiableBean bean = getIdentifiableBean(crossRef);
        if (structureType.isAssignableFrom(bean.getClass())) {
            return (T) bean;
        }
        throw new CrossReferenceException(crossRef);
    }

    @Override
    public IdentifiableBean getIdentifiableBean(CrossReferenceBean crossReferenceBean) {
        try {
            IdentifiableBean identifiableBean = getIdentifiableBean((StructureReferenceBean) crossReferenceBean);
            if (identifiableBean != null) {
                return identifiableBean;
            }
        } catch (CrossReferenceException e) {
        }
        //Catch all
        throw new CrossReferenceException(crossReferenceBean);
    }


    /**
     * Gets identifiable bean.
     *
     * @param sRef the s ref
     * @return the identifiable bean
     */
    public IdentifiableBean getIdentifiableBean(StructureReferenceBean sRef) {
        MaintainableBean maint = retrievalManager.getMaintainableBean(sRef);
        if (maint == null) {
            return null;
        }
        if (maint.isExternalReference().isTrue()) {
            if (externalReferenceRetrievalManager != null) {
                maint = externalReferenceRetrievalManager.resolveFullStructure(maint);
            }
        }
        String targetURN = sRef.getTargetUrn();
        if (maint.getUrn().equals(targetURN)) {
            return maint;
        }

        for (IdentifiableBean currentIdent : maint.getIdentifiableComposites()) {
            if (currentIdent.getUrn().equals(targetURN)) {
                return currentIdent;
            }
        }
        return null;
    }

    @Override
    public Set<? extends IdentifiableBean> getIdentifiableBeans(StructureReferenceBean sRef) {
        if (sRef.getTargetReference().isMaintainable()) {
            return retrievalManager.getMaintainableBeans(sRef.getTargetReference().getMaintainableInterface(), sRef);
        }
        Set<IdentifiableBean> idents = new HashSet<IdentifiableBean>();
        for (MaintainableBean currentMaint : retrievalManager.getMaintainableBeans(sRef.getMaintainableStructureType().getMaintainableInterface(), sRef.getMaintainableReference())) {
            for (IdentifiableBean comp : currentMaint.getIdentifiableComposites()) {
                if (comp.getStructureType() == sRef.getTargetReference()) {
                    if (sRef.getChildReference() != null && sRef.getChildReference().getId() != null) {
                        if (comp.getId().equals(sRef.getChildReference().getId())) {
                            idents.add(comp);
                        }
                    } else {
                        idents.add(comp);
                    }
                }
            }
        }
        return idents;
    }


    @Override
    @SuppressWarnings("unchecked")
    public <T> T getIdentifiableBean(StructureReferenceBean sRef, Class<T> structureType) throws CrossReferenceException {
        IdentifiableBean bean = getIdentifiableBean(sRef);
        if (bean == null) {
            return null;
        }

        if (structureType.isAssignableFrom(bean.getClass())) {
            return (T) bean;
        }
        throw new SdmxReferenceException(sRef);
    }
}
