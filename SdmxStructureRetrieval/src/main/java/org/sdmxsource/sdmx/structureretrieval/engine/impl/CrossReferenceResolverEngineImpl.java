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
package org.sdmxsource.sdmx.structureretrieval.engine.impl;

import org.apache.log4j.Logger;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.CrossReferenceException;
import org.sdmxsource.sdmx.api.exception.SdmxReferenceException;
import org.sdmxsource.sdmx.api.manager.retrieval.IdentifiableRetrievalManager;
import org.sdmxsource.sdmx.api.manager.retrieval.ProvisionBeanRetrievalManager;
import org.sdmxsource.sdmx.api.manager.retrieval.SdmxBeanRetrievalManager;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;
import org.sdmxsource.sdmx.api.model.beans.base.*;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.registry.ProvisionAgreementBean;
import org.sdmxsource.sdmx.api.model.beans.registry.RegistrationBean;
import org.sdmxsource.sdmx.structureretrieval.engine.CrossReferenceResolverEngine;
import org.sdmxsource.sdmx.structureretrieval.engine.MaintainableCrossReferenceRetrieverEngine;
import org.sdmxsource.sdmx.structureretrieval.manager.InMemoryRetrievalManager;
import org.sdmxsource.sdmx.util.beans.container.SdmxBeansImpl;
import org.sdmxsource.sdmx.util.beans.reference.StructureReferenceBeanImpl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * The type Cross reference resolver engine.
 */
public class CrossReferenceResolverEngineImpl implements CrossReferenceResolverEngine {
    private static final Logger LOG = Logger.getLogger(CrossReferenceResolverEngineImpl.class);
    private final MaintainableCrossReferenceRetrieverEngine maintainableCrossReferenceRetrieverEngine;
    private boolean resolveAgencies;
    private Map<IdentifiableBean, Set<IdentifiableBean>> crossReferences = new HashMap<IdentifiableBean, Set<IdentifiableBean>>();
    private Map<String, IdentifiableBean> allIdentifiables = new HashMap<String, IdentifiableBean>();
    private Map<String, AgencyBean> agencies = new HashMap<String, AgencyBean>();

    /**
     * Default Constructor
     */
    public CrossReferenceResolverEngineImpl() {
        maintainableCrossReferenceRetrieverEngine = null;
    }

    /**
     * Constructor which pre-populates cache
     *
     * @param beans the beans
     */
    public CrossReferenceResolverEngineImpl(SdmxBeans beans) {
        this();
        addBeansToMap(beans);
    }

    private void addBeansToMap(SdmxBeans beans) {
        for (MaintainableBean maint : beans.getAllMaintainables()) {
            addMaintainableToMap(maint);
        }
    }

    private void addMaintainableToMap(MaintainableBean maint) {
        allIdentifiables.put(maint.getUrn(), maint);
        for (IdentifiableBean identifiableBean : maint.getIdentifiableComposites()) {
            if (identifiableBean.getStructureType() == SDMX_STRUCTURE_TYPE.AGENCY) {
                AgencyBean acy = (AgencyBean) identifiableBean;
                agencies.put(acy.getFullId(), acy);
            }
            allIdentifiables.put(identifiableBean.getUrn(), identifiableBean);
        }
    }


    private void resetMaps() {
        crossReferences = new HashMap<IdentifiableBean, Set<IdentifiableBean>>();
        //		 maintainables = new HashSet<MaintainableBean>();
        agencies = new HashMap<String, AgencyBean>();
    }

    @Override
    public Map<IdentifiableBean, Set<CrossReferenceBean>> getMissingCrossReferences(SdmxBeans beans, int numberLevelsDeep, IdentifiableRetrievalManager retrievalManager) {
        Map<IdentifiableBean, Set<CrossReferenceBean>> returnMap = new HashMap<IdentifiableBean, Set<CrossReferenceBean>>();
        resolveReferences(beans, false, numberLevelsDeep, retrievalManager, returnMap);
        return returnMap;
    }

    @Override
    public Set<IdentifiableBean> resolveReferences(ProvisionAgreementBean provision, IdentifiableRetrievalManager structRetrievalManager) {
        if (structRetrievalManager == null) {
            throw new IllegalArgumentException("StructureRetrievalManager can not be null");
        }

        Set<IdentifiableBean> returnSet = new HashSet<IdentifiableBean>();
        if (provision.getStructureUseage() != null) {
            IdentifiableBean structureUseage = structRetrievalManager.getIdentifiableBean(provision.getStructureUseage());

            if (structureUseage == null) {
                throw new CrossReferenceException(provision.getStructureUseage());
            }
            returnSet.add(structureUseage);
        }

        if (provision.getDataproviderRef() != null) {
            IdentifiableBean dataProvider = structRetrievalManager.getIdentifiableBean(provision.getDataproviderRef());
            if (dataProvider == null) {
                throw new CrossReferenceException(provision.getDataproviderRef());
            }
            returnSet.add(dataProvider);
        }
        return returnSet;
    }

    @Override
    public Map<String, Set<MaintainableBean>> getMissingAgencies(SdmxBeans beans, IdentifiableRetrievalManager retrievalManager) {
        Set<String> agencyIds = new HashSet<String>();
        for (AgencyBean acy : beans.getAgencies()) {
            agencyIds.add(acy.getFullId());
        }

        Map<String, Set<MaintainableBean>> returnMap = new HashMap<String, Set<MaintainableBean>>();
        for (MaintainableBean currentMaint : beans.getAllMaintainables()) {
            String referencedAgencyId = currentMaint.getAgencyId();
            if (!agencyIds.contains(referencedAgencyId)) {
                if (retrievalManager != null) {
                    try {
                        AgencyBean acy = resolveAgency(referencedAgencyId, retrievalManager);
                        if (acy != null) {
                            agencyIds.add(acy.getFullId());
                            continue;
                        }
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                }
                Set<MaintainableBean> maintainables = returnMap.get(referencedAgencyId);
                if (maintainables == null) {
                    maintainables = new HashSet<MaintainableBean>();
                    returnMap.put(referencedAgencyId, maintainables);
                }
                maintainables.add(currentMaint);
            }
        }
        return returnMap;
    }

    @Override
    public Set<IdentifiableBean> resolveReferences(RegistrationBean registation, ProvisionBeanRetrievalManager provRetrievalManager) {

        Set<IdentifiableBean> returnSet = new HashSet<IdentifiableBean>();

        if (registation.getProvisionAgreementRef() != null) {
            if (provRetrievalManager == null) {
                throw new IllegalArgumentException("ProvisionRetrievalManager can not be null");
            }
            ProvisionAgreementBean provision = provRetrievalManager.getProvision(registation);
            if (provision == null) {
                throw new CrossReferenceException(registation.getProvisionAgreementRef());
            }
            returnSet.add(provision);
        }

        return returnSet;
    }

    @Override
    public Map<IdentifiableBean, Set<IdentifiableBean>> resolveReferences(SdmxBeans beans,
                                                                          boolean resolveAgencies,
                                                                          int numberLevelsDeep,
                                                                          IdentifiableRetrievalManager retrievalManager) throws CrossReferenceException {
        return resolveReferences(beans, resolveAgencies, numberLevelsDeep, retrievalManager, null);
    }

    private Map<IdentifiableBean, Set<IdentifiableBean>> resolveReferences(SdmxBeans beans,
                                                                           boolean resolveAgencies,
                                                                           int numberLevelsDeep,
                                                                           IdentifiableRetrievalManager retrievalManager,
                                                                           Map<IdentifiableBean, Set<CrossReferenceBean>> populateMap) throws CrossReferenceException {
        LOG.info("Resolve References, bean retrieval manager: " + retrievalManager);
        resetMaps();
        this.resolveAgencies = resolveAgencies;
        Map<IdentifiableBean, Set<IdentifiableBean>> returnMap = null;
        int numberBeansLast = 0;
        int numberReferencesLast = 0;

        int numberBeansCurrent = -1;
        int numberReferencesCurrent = -1;

        SdmxBeans allBeans = beans;
        int currentLevel = 1;
        do {
            numberBeansLast = numberBeansCurrent;
            numberReferencesLast = numberReferencesCurrent;

            LOG.debug("numberBeansLast= " + numberBeansLast);
            LOG.debug("numberReferencesLast= " + numberReferencesLast);

            returnMap = resolveReferencesInternal(allBeans, retrievalManager, populateMap);
            numberBeansCurrent = returnMap.size();
            numberReferencesCurrent = countValues(returnMap);
            allBeans = new SdmxBeansImpl(beans);
            for (Set<IdentifiableBean> currentBeanSet : returnMap.values()) {
                for (IdentifiableBean currentBean : currentBeanSet) {
                    allBeans.addIdentifiable(currentBean);
                }
            }
            LOG.debug("numberBeansLast= " + numberBeansLast);
            LOG.debug("numberReferencesLast= " + numberReferencesLast);
            LOG.debug("numberBeansCurrent= " + numberBeansCurrent);
            LOG.debug("numberReferencesCurrent= " + numberReferencesCurrent);
            LOG.debug("currentLevel= " + currentLevel);
            LOG.debug("numberLevelsDeep= " + numberLevelsDeep);
            if (currentLevel == numberLevelsDeep) {
                break;
            }
            currentLevel++;

        } while (numberBeansCurrent != numberBeansLast || numberReferencesCurrent != numberReferencesLast);

        return returnMap;
    }

    @Override
    public Set<IdentifiableBean> resolveReferences(MaintainableBean bean,
                                                   boolean resolveAgencies,
                                                   int numberLevelsDeep,
                                                   IdentifiableRetrievalManager retrievalManager) throws CrossReferenceException {
        resetMaps();
        SdmxBeans beans = new SdmxBeansImpl();
        beans.addIdentifiable(bean);
        Map<IdentifiableBean, Set<IdentifiableBean>> references = resolveReferences(beans, resolveAgencies, numberLevelsDeep, retrievalManager);

        Set<IdentifiableBean> returnSet = new HashSet<IdentifiableBean>();
        for (IdentifiableBean key : references.keySet()) {
            returnSet.addAll(references.get(key));
        }
        return returnSet;
    }

    private int countValues(Map<IdentifiableBean, Set<IdentifiableBean>> map) {
        int numberReferences = 0;
        for (Set<IdentifiableBean> refences : map.values()) {
            numberReferences += refences.size();
        }
        return numberReferences;
    }

    private Map<IdentifiableBean, Set<IdentifiableBean>> resolveReferencesInternal(SdmxBeans beans, IdentifiableRetrievalManager retrievalManager, Map<IdentifiableBean, Set<CrossReferenceBean>> populateMissingMap) throws CrossReferenceException {
        for (AgencyBean currentAgency : beans.getAgencies()) {
            agencies.put(currentAgency.getFullId(), currentAgency);
        }

        //Add all the top level beans to the maintainables list
        //		maintainables.addAll(beans.getAllMaintainables());
        addBeansToMap(beans);

        //LOOP THROUGH ALL THE BEANS AND RESOLVE ALL THE REFERENCES
        if (resolveAgencies) {
            for (MaintainableBean currentBean : beans.getAllMaintainables()) {
                try {
                    resolveAgency(currentBean, retrievalManager);
                } catch (CrossReferenceException e) {
                    throw new SdmxReferenceException(getAgencyRef(currentBean.getAgencyId()));
                }
            }
        }
        Set<MaintainableBean> loopSet = new HashSet<MaintainableBean>();
        loopSet.addAll(beans.getAllMaintainables());
        SdmxBeanRetrievalManager retMan = new InMemoryRetrievalManager(beans);
        for (MaintainableBean currentMaintainable : loopSet) {
            LOG.debug("Resolving References For : " + currentMaintainable.getUrn());
            Set<CrossReferenceBean> crossReferences;
            if (maintainableCrossReferenceRetrieverEngine != null) {
                crossReferences = maintainableCrossReferenceRetrieverEngine.getCrossReferences(retMan, currentMaintainable);
            } else {
                crossReferences = currentMaintainable.getCrossReferences();
            }
            LOG.debug("Number of References : " + crossReferences.size());
            int i = 0;
            for (CrossReferenceBean crossReference : crossReferences) {
                i++;
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Resolving Reference " + i + ": " + crossReference.toString() + " - referenced from -" + crossReference.getReferencedFrom().getStructureType());
                }
                try {
                    storeRef(crossReference.getReferencedFrom(), resolveCrossReference(crossReference, retrievalManager));
                } catch (CrossReferenceException e) {
                    handleMissingReference(e, populateMissingMap);
                    if (populateMissingMap == null) {
                        throw e;
                    }
                }
            }
        }
        return crossReferences;
    }

    /**
     * Handles a missing reference either by throwing an exception, if the populateMissingMap, or by populating the map, if both the map is not null and the reference exception has reference to the
     * cross referenced artifact.
     */
    private void handleMissingReference(CrossReferenceException e, Map<IdentifiableBean, Set<CrossReferenceBean>> populateMissingMap) throws CrossReferenceException {
        if (populateMissingMap != null && e.getCrossReference() != null) {
            CrossReferenceBean crossReference = e.getCrossReference();
            Set<CrossReferenceBean> missingRefences = populateMissingMap.get(crossReference.getReferencedFrom());
            if (missingRefences == null) {
                missingRefences = new HashSet<CrossReferenceBean>();
                if (crossReference.getReferencedFrom().getStructureType().isIdentifiable()) {
                    populateMissingMap.put((IdentifiableBean) crossReference.getReferencedFrom(), missingRefences);
                } else {
                    populateMissingMap.put(crossReference.getReferencedFrom().getParent(IdentifiableBean.class, true), missingRefences);
                }
            }
            missingRefences.add(crossReference);
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////AGENCY REFERENCES                                            ///////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void resolveAgency(MaintainableBean maint, IdentifiableRetrievalManager structRetrievalManager) throws CrossReferenceException {
        if (!resolveAgencies) {
            return;
        }
        if (maint.getAgencyId().equals(AgencyBean.DEFAULT_AGENCY)) {
            return;
        }
        AgencyBean agency = resolveAgency(maint.getAgencyId(), structRetrievalManager);
        this.agencies.put(agency.getId(), agency);
        storeRef(maint, agency);
    }

    private AgencyBean resolveAgency(String agencyId, IdentifiableRetrievalManager identifiableRetrievalManager) throws CrossReferenceException {
        if (agencies.containsKey(agencyId)) {
            return agencies.get(agencyId);
        }
        AgencyBean agency = null;
        if (identifiableRetrievalManager != null) {
            String[] split = agencyId.split("\\.");
            String parentAgencyId = AgencySchemeBean.DEFAULT_SCHEME;
            String targetAgencyId = agencyId;
            if (split.length > 1) {
                targetAgencyId = split[split.length - 1];
                split[split.length - 1] = null;
                String concat = "";
                parentAgencyId = "";
                for (String currentSplit : split) {
                    if (currentSplit != null) {
                        parentAgencyId += concat + currentSplit;
                    }
                    concat = ".";
                }
            }
            StructureReferenceBean agencyRef = new StructureReferenceBeanImpl(parentAgencyId, AgencySchemeBean.FIXED_ID, AgencySchemeBean.FIXED_VERSION, SDMX_STRUCTURE_TYPE.AGENCY, targetAgencyId);
            agency = identifiableRetrievalManager.getIdentifiableBean(agencyRef, AgencyBean.class);
        }
        if (agency == null) {
            throw new SdmxReferenceException(getAgencyRef(agencyId));
        }
        return agency;
    }

    private StructureReferenceBean getAgencyRef(String agencyId) {
        String parentAgency = AgencyBean.DEFAULT_AGENCY;
        if (agencyId.contains(".")) {
            parentAgency = agencyId.substring(0, agencyId.indexOf("."));
            agencyId = agencyId.substring(agencyId.indexOf(".") + 1);
        }
        return new StructureReferenceBeanImpl(parentAgency, AgencySchemeBean.DEFAULT_SCHEME, AgencySchemeBean.DEFAULT_VERSION, SDMX_STRUCTURE_TYPE.AGENCY, agencyId);
    }

    @Override
    public IdentifiableBean resolveCrossReference(CrossReferenceBean crossReference, IdentifiableRetrievalManager structRetrievalManager) throws CrossReferenceException {
        if (crossReference.getTargetReference() == SDMX_STRUCTURE_TYPE.AGENCY) {
            return resolveAgency(crossReference.getFullId(), structRetrievalManager);
        }
        //1. Try local Maps
        IdentifiableBean resolvedIdentifiable = resolveMaintainableFromLocalMaps(crossReference);
        if (resolvedIdentifiable != null) {
            return resolvedIdentifiable;
        }

        IdentifiableBean identifiableBean = null;
        if (structRetrievalManager != null) {
            LOG.info("IdentifiableBean '" + crossReference + "' not found locally, check IdentifiableRetrievalManager");
            identifiableBean = structRetrievalManager.getIdentifiableBean(crossReference);
        }
        if (identifiableBean == null) {
            throw new CrossReferenceException(crossReference);
        }
        addMaintainableToMap(identifiableBean.getMaintainableParent());
        return identifiableBean;
    }

    private IdentifiableBean resolveMaintainableFromLocalMaps(CrossReferenceBean xsRef) throws CrossReferenceException {
        return allIdentifiables.get(xsRef.getTargetUrn());
    }

    private void storeRef(SDMXBean referencedFrom, IdentifiableBean reference) {
        IdentifiableBean refFromIdentifiable;
        if (referencedFrom.getStructureType().isIdentifiable()) {
            refFromIdentifiable = (IdentifiableBean) referencedFrom;
        } else {
            refFromIdentifiable = referencedFrom.getParent(IdentifiableBean.class, true);
        }
        Set<IdentifiableBean> refList;
        if (crossReferences.containsKey(refFromIdentifiable)) {
            refList = crossReferences.get(refFromIdentifiable);
        } else {
            refList = new HashSet<IdentifiableBean>();
            crossReferences.put(refFromIdentifiable, refList);
        }
        refList.add(reference);
    }
}
