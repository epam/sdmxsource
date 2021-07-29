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
import org.sdmxsource.sdmx.api.exception.SdmxNotImplementedException;
import org.sdmxsource.sdmx.api.manager.retrieval.ProvisionBeanRetrievalManager;
import org.sdmxsource.sdmx.api.manager.retrieval.SdmxBeanRetrievalManager;
import org.sdmxsource.sdmx.api.model.beans.base.DataProviderSchemeBean;
import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataStructureBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataflowBean;
import org.sdmxsource.sdmx.api.model.beans.metadatastructure.MetadataFlowBean;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.registry.ProvisionAgreementBean;
import org.sdmxsource.sdmx.api.model.beans.registry.RegistrationBean;
import org.sdmxsource.util.ObjectUtil;

import java.util.HashSet;
import java.util.Set;


/**
 * The type Provision retrieval manager from bean retrieval.
 */
public class ProvisionRetrievalManagerFromBeanRetrieval implements ProvisionBeanRetrievalManager {
    private SdmxBeanRetrievalManager beanRetrievalManager;

    /**
     * Instantiates a new Provision retrieval manager from bean retrieval.
     *
     * @param beanRetrievalManager the bean retrieval manager
     */
    public ProvisionRetrievalManagerFromBeanRetrieval(SdmxBeanRetrievalManager beanRetrievalManager) {
        this.beanRetrievalManager = beanRetrievalManager;
    }

    @Override
    public Set<ProvisionAgreementBean> getProvisions() {
        return beanRetrievalManager.getMaintainableBeans(ProvisionAgreementBean.class);
    }

    /**
     * Gets provisions by data structure.
     *
     * @param provisionRef the provision ref
     * @return the provisions by data structure
     */
    public Set<ProvisionAgreementBean> getProvisionsByDataStructure(StructureReferenceBean provisionRef) {
        Set<ProvisionAgreementBean> allProvision = beanRetrievalManager.getMaintainableBeans(ProvisionAgreementBean.class);

        Set<ProvisionAgreementBean> returnSet = new HashSet<ProvisionAgreementBean>();
        Set<DataStructureBean> keyFamilies = beanRetrievalManager.getMaintainableBeans(DataStructureBean.class);
        Set<DataflowBean> dataflows = beanRetrievalManager.getMaintainableBeans(DataflowBean.class);
        for (MaintainableBean currentDSD : keyFamilies) {
            if (provisionRef.isMatch(currentDSD)) {
                for (MaintainableBean currentFlow : dataflows) {
                    DataflowBean df = (DataflowBean) currentFlow;
                    if (df.getDataStructureRef().isMatch(currentDSD)) {
                        returnSet.addAll(getByReference(df, allProvision));
                    }
                }
            }
        }
        return returnSet;
    }

    /**
     * Gets provisions by data flow.
     *
     * @param provisionRef the provision ref
     * @return the provisions by data flow
     */
    public Set<ProvisionAgreementBean> getProvisionsByDataFlow(StructureReferenceBean provisionRef) {
        Set<ProvisionAgreementBean> allProvision = beanRetrievalManager.getMaintainableBeans(ProvisionAgreementBean.class);

        Set<ProvisionAgreementBean> returnSet = new HashSet<ProvisionAgreementBean>();
        Set<DataflowBean> dataflows = beanRetrievalManager.getMaintainableBeans(DataflowBean.class);
        for (MaintainableBean currentFlow : dataflows) {
            returnSet.addAll(getByReference(currentFlow, allProvision));
        }
        return returnSet;
    }

    /**
     * Gets provisions by data provider.
     *
     * @param provisionRef the provision ref
     * @return the provisions by data provider
     */
    public Set<ProvisionAgreementBean> getProvisionsByDataProvider(StructureReferenceBean provisionRef) {
        Set<ProvisionAgreementBean> returnSet = new HashSet<ProvisionAgreementBean>();
        Set<ProvisionAgreementBean> allProvisions = beanRetrievalManager.getMaintainableBeans(ProvisionAgreementBean.class);
        Set<DataProviderSchemeBean> dataProviderSchemes = beanRetrievalManager.getMaintainableBeans(DataProviderSchemeBean.class);
        for (MaintainableBean currentDps : dataProviderSchemes) {
            if (provisionRef.isMatch(currentDps)) {
                for (MaintainableBean currentProvision : allProvisions) {
                    ProvisionAgreementBean prov = (ProvisionAgreementBean) currentProvision;
                    if (prov.getDataproviderRef().equals(provisionRef)) {
                        returnSet.add(prov);
                    }
                }
            }
        }
        return returnSet;
    }

    /**
     * Gets provisions by provision.
     *
     * @param provisionRef the provision ref
     * @return the provisions by provision
     */
    public Set<ProvisionAgreementBean> getProvisionsByProvision(StructureReferenceBean provisionRef) {
        return beanRetrievalManager.getMaintainableBeans(ProvisionAgreementBean.class, provisionRef.getMaintainableReference());
    }


    @Override
    public Set<ProvisionAgreementBean> getProvisions(StructureReferenceBean provisionRef) {
        SDMX_STRUCTURE_TYPE targetReference = provisionRef.getTargetReference();
        switch (targetReference) {
            case DSD:
                return getProvisionsByDataStructure(provisionRef);
            case DATAFLOW:
                return getProvisionsByDataFlow(provisionRef);
            case DATA_PROVIDER:
                return getProvisionsByDataProvider(provisionRef);
            case PROVISION_AGREEMENT:
                return getProvisionsByProvision(provisionRef);
            default:
                throw new SdmxNotImplementedException("Can not get Provisons by stucture type:" + targetReference.getType());
        }
    }

    @Override
    public ProvisionAgreementBean getProvision(RegistrationBean registration) {
        Set<ProvisionAgreementBean> provisions = getProvisions(registration.getProvisionAgreementRef());
        if (ObjectUtil.validCollection(provisions)) {
            if (provisions.size() > 1) {
                throw new RuntimeException("Only one provision agreement expected from reference : " + registration.getProvisionAgreementRef());
            }
            return (ProvisionAgreementBean) provisions.toArray()[0];
        }
        return null;
    }

    @Override
    public Set<ProvisionAgreementBean> getProvisions(DataflowBean dataflow) {
        Set<ProvisionAgreementBean> allProvisions = beanRetrievalManager.getMaintainableBeans(ProvisionAgreementBean.class);
        return getByReference(dataflow, allProvisions);
    }

    @Override
    public Set<ProvisionAgreementBean> getProvisions(MetadataFlowBean metadataflow) {
        Set<ProvisionAgreementBean> allProvisions = beanRetrievalManager.getMaintainableBeans(ProvisionAgreementBean.class);
        return getByReference(metadataflow, allProvisions);
    }


    private Set<ProvisionAgreementBean> getByReference(MaintainableBean bean, Set<ProvisionAgreementBean> allProvisions) {
        Set<ProvisionAgreementBean> returnSet = new HashSet<ProvisionAgreementBean>();
        for (MaintainableBean currentProvision : allProvisions) {
            ProvisionAgreementBean provision = (ProvisionAgreementBean) currentProvision;
            if (isMatch(provision, bean)) {
                returnSet.add(provision);
            }
        }
        return returnSet;
    }

    private boolean isMatch(ProvisionAgreementBean provision, MaintainableBean bean) {
        for (CrossReferenceBean currentRef : provision.getCrossReferences()) {
            if (currentRef.isMatch(bean)) {
                return true;
            }
        }
        return false;
    }
}
