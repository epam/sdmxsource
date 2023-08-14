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

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.sdmxsource.sdmx.api.constants.ExceptionCode;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.constants.STRUCTURE_QUERY_DETAIL;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.exception.SdmxNotImplementedException;
import org.sdmxsource.sdmx.api.manager.retrieval.*;
import org.sdmxsource.sdmx.api.manager.retrieval.crossreference.CrossReferencedRetrievalManager;
import org.sdmxsource.sdmx.api.manager.retrieval.crossreference.CrossReferencingRetrievalManager;
import org.sdmxsource.sdmx.api.model.ResolutionSettings.RESOLVE_CROSS_REFERENCES;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;
import org.sdmxsource.sdmx.api.model.beans.base.*;
import org.sdmxsource.sdmx.api.model.beans.categoryscheme.CategorisationBean;
import org.sdmxsource.sdmx.api.model.beans.categoryscheme.CategorySchemeBean;
import org.sdmxsource.sdmx.api.model.beans.categoryscheme.ReportingTaxonomyBean;
import org.sdmxsource.sdmx.api.model.beans.codelist.CodelistBean;
import org.sdmxsource.sdmx.api.model.beans.codelist.HierarchicalCodelistBean;
import org.sdmxsource.sdmx.api.model.beans.conceptscheme.ConceptSchemeBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataStructureBean;
import org.sdmxsource.sdmx.api.model.beans.datastructure.DataflowBean;
import org.sdmxsource.sdmx.api.model.beans.mapping.StructureSetBean;
import org.sdmxsource.sdmx.api.model.beans.metadatastructure.MetadataFlowBean;
import org.sdmxsource.sdmx.api.model.beans.metadatastructure.MetadataStructureDefinitionBean;
import org.sdmxsource.sdmx.api.model.beans.process.ProcessBean;
import org.sdmxsource.sdmx.api.model.beans.reference.MaintainableRefBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.registry.*;
import org.sdmxsource.sdmx.api.model.header.HeaderBean;
import org.sdmxsource.sdmx.api.model.query.RESTStructureQuery;
import org.sdmxsource.sdmx.structureretrieval.engine.CrossReferenceResolverEngine;
import org.sdmxsource.sdmx.structureretrieval.engine.impl.CrossReferenceResolverEngineImpl;
import org.sdmxsource.sdmx.structureretrieval.manager.crossreference.BeanCrossReferencedRetrievalManager;
import org.sdmxsource.sdmx.util.beans.container.SdmxBeansImpl;
import org.sdmxsource.sdmx.util.beans.reference.MaintainableRefBeanImpl;
import org.sdmxsource.util.ObjectUtil;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * The type Base sdmx bean retrieval manager.
 */
public abstract class BaseSdmxBeanRetrievalManager extends IdentifiableRetrievalManagerImpl implements SdmxBeanRetrievalManager {
    /**
     * The Cross reference retrieval manager.
     */
    protected CrossReferencedRetrievalManager crossReferenceRetrievalManager;
    /**
     * The Cross referencing retrieval manager.
     */
    protected CrossReferencingRetrievalManager crossReferencingRetrievalManager;
    /**
     * The Service retrieval manager.
     */
    protected ServiceRetrievalManager serviceRetrievalManager;
    private Logger LOG = LoggerFactory.getLogger(BaseSdmxBeanRetrievalManager.class);
    private SdmxBeanRetrievalManager proxy;
    private RegistrationBeanRetrievalManager registrationRetrievalManager;
    private HeaderRetrievalManager headerRetrievalManager;
    private ExternalReferenceRetrievalManager externalReferenceRetrievalManager;


    /**
     * Instantiates a new Base sdmx bean retrieval manager.
     */
    public BaseSdmxBeanRetrievalManager() {
        super.retrievalManager = this;
        super.externalReferenceRetrievalManager = externalReferenceRetrievalManager;
        if (crossReferenceRetrievalManager == null) {
            crossReferenceRetrievalManager = new BeanCrossReferencedRetrievalManager(this);
        }
    }

    /**
     * Instantiates a new Base sdmx bean retrieval manager.
     *
     * @param retrievalManager the retrieval manager
     */
    public BaseSdmxBeanRetrievalManager(
            SdmxBeanRetrievalManager retrievalManager) {
        super(retrievalManager);
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
        acySch = getMaintainableBean(AgencySchemeBean.class, new MaintainableRefBeanImpl(agencyParentId, AgencySchemeBean.FIXED_ID, AgencySchemeBean.FIXED_VERSION));
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
    public <T extends MaintainableBean> T getMaintainableBean(Class<T> structureType, MaintainableRefBean ref) {
        return getMaintainableBean(structureType, ref, false, false);
    }


    @Override
    public MaintainableBean getMaintainableBean(StructureReferenceBean sRef) {
        return getMaintainableBean(sRef, false, false);
    }

    @Override
    public SdmxBeans getSdmxBeans(StructureReferenceBean sRef, RESOLVE_CROSS_REFERENCES resolveCrossReferences) {
        SdmxBeans beans = new SdmxBeansImpl();
        beans.addIdentifiables(getMaintainableBeans(sRef.getMaintainableStructureType().getMaintainableInterface(), sRef.getMaintainableReference()));

        switch (resolveCrossReferences) {
            case DO_NOT_RESOLVE:
                break;
            case RESOLVE_ALL:
                resolveReferences(beans, true);
                break;
            case RESOLVE_EXCLUDE_AGENCIES:
                resolveReferences(beans, false);
                break;
            default:
                throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "Unknown condition encountered for resolveCrossReferences. Value: " + resolveCrossReferences);
        }

        return beans;
    }

    @Override
    public <T extends MaintainableBean> Set<T> getMaintainableBeans(Class<T> structureType) {
        return getMaintainableBeans(structureType, null);
    }

    @Override
    public MaintainableBean getMaintainableBean(StructureReferenceBean sRef, boolean returnStub, boolean returnLatest) {
        if (sRef == null) {
            throw new IllegalArgumentException("getMaintainableBean was passed a null StructureReferenceBean this is not allowed");
        }
        return extractFromSet(getMaintainableBeans(sRef.getMaintainableStructureType().getMaintainableInterface(), sRef.getMaintainableReference(), returnLatest, returnStub));
    }

    @Override
    public <T extends MaintainableBean> T getMaintainableBean(Class<T> structureType, MaintainableRefBean ref, boolean returnStub, boolean returnLatest) {
        if (!returnLatest) {
            returnLatest = !ObjectUtil.validObject(ref.getVersion());
        }
        return extractFromSet(getMaintainableBeans(structureType, ref, returnLatest, returnStub));
    }

    @Override
    public <T extends MaintainableBean> Set<T> getMaintainableBeans(Class<T> structureType, MaintainableRefBean ref) {
        return getMaintainableBeans(structureType, ref, false, false);
    }


    @SuppressWarnings("unchecked")
    public <T extends MaintainableBean> Set<T> getMaintainableBeans(Class<T> structureType, MaintainableRefBean ref, boolean returnLatest, boolean returnStub) {
        Set<T> returnSet;
        if (returnLatest) {
            ref = new MaintainableRefBeanImpl(ref.getAgencyId(), ref.getMaintainableId(), null);
        }
        if (structureType == AgencySchemeBean.class) {
            returnSet = (Set<T>) getAgencySchemeBeans(ref, returnStub);
        } else if (structureType == AttachmentConstraintBean.class) {
            returnSet = (Set<T>) getAttachmentConstraints(ref, returnLatest, returnStub);
        } else if (structureType == ContentConstraintBean.class) {
            returnSet = (Set<T>) getContentConstraints(ref, returnLatest, returnStub);
        } else if (structureType == DataConsumerSchemeBean.class) {
            returnSet = (Set<T>) getDataConsumerSchemeBeans(ref, returnStub);
        } else if (structureType == DataProviderSchemeBean.class) {
            returnSet = (Set<T>) getDataProviderSchemeBeans(ref, returnStub);
        } else if (structureType == CategorisationBean.class) {
            returnSet = (Set<T>) getCategorisationBeans(ref, returnStub);
        } else if (structureType == CategorySchemeBean.class) {
            returnSet = (Set<T>) getCategorySchemeBeans(ref, returnLatest, returnStub);
        } else if (structureType == CodelistBean.class) {
            returnSet = (Set<T>) getCodelistBeans(ref, returnLatest, returnStub);
        } else if (structureType == ConceptSchemeBean.class) {
            returnSet = (Set<T>) getConceptSchemeBeans(ref, returnLatest, returnStub);
        } else if (structureType == DataflowBean.class) {
            returnSet = (Set<T>) getDataflowBeans(ref, returnLatest, returnStub);
        } else if (structureType == HierarchicalCodelistBean.class) {
            returnSet = (Set<T>) getHierarchicCodeListBeans(ref, returnLatest, returnStub);
        } else if (structureType == DataStructureBean.class) {
            returnSet = (Set<T>) getDataStructureBeans(ref, returnLatest, returnStub);
        } else if (structureType == MetadataFlowBean.class) {
            returnSet = (Set<T>) getMetadataflowBeans(ref, returnLatest, returnStub);
        } else if (structureType == MetadataStructureDefinitionBean.class) {
            returnSet = (Set<T>) getMetadataStructureBeans(ref, returnLatest, returnStub);
        } else if (structureType == OrganisationUnitSchemeBean.class) {
            returnSet = (Set<T>) getOrganisationUnitSchemeBeans(ref, returnLatest, returnStub);
        } else if (structureType == ProcessBean.class) {
            returnSet = (Set<T>) getProcessBeans(ref, returnLatest, returnStub);
        } else if (structureType == ReportingTaxonomyBean.class) {
            returnSet = (Set<T>) getReportingTaxonomyBeans(ref, returnLatest, returnStub);
        } else if (structureType == StructureSetBean.class) {
            returnSet = (Set<T>) getStructureSetBeans(ref, returnLatest, returnStub);
        } else if (structureType == ProvisionAgreementBean.class) {
            returnSet = (Set<T>) getProvisionAgreementBeans(ref, returnLatest, returnStub);
        } else if (structureType == RegistrationBean.class) {
            returnSet = (Set<T>) getRegistrationBeans(ref);
        } else if (structureType == SubscriptionBean.class) {
            returnSet = (Set<T>) getSubscriptionBeans(ref, false, false);
        } else if (structureType == null || structureType == MaintainableBean.class) {
            returnSet = (Set<T>) getAllMaintainables(ref, returnLatest, returnStub);
        } else {
            throw new SdmxNotImplementedException("getMaintainableBean for type: " + structureType);
        }
        if (returnStub && serviceRetrievalManager != null) {
            Set<T> stubSet = new HashSet<T>();
            for (T returnItm : returnSet) {
                if (returnItm.isExternalReference().isTrue()) {
                    stubSet.add(returnItm);
                } else {
                    stubSet.add((T) serviceRetrievalManager.createStub(returnItm));
                }
            }
            returnSet = stubSet;
        }
        return returnSet;

    }

    /**
     * Gets all maintainables.
     *
     * @param ref          the ref
     * @param returnLatest the return latest
     * @param returnStubs  the return stubs
     * @return the all maintainables
     */
    protected Set<MaintainableBean> getAllMaintainables(MaintainableRefBean ref, boolean returnLatest, boolean returnStubs) {
        Set<MaintainableBean> q = new HashSet<MaintainableBean>();
        for (SDMX_STRUCTURE_TYPE currentMaintainable : SDMX_STRUCTURE_TYPE.getMaintainableStructureTypes()) {
            if (currentMaintainable != SDMX_STRUCTURE_TYPE.REGISTRATION && currentMaintainable != SDMX_STRUCTURE_TYPE.SUBSCRIPTION) {
                q.addAll(getMaintainableBeans(currentMaintainable.getMaintainableInterface(), ref, returnLatest, returnStubs));
            }
        }
        return q;
    }

    @Override
    public SdmxBeans getMaintainables(RESTStructureQuery complexQuery) {
        LOG.info("Query for maintainables: " + complexQuery);

        boolean isAllStubs = complexQuery.getStructureQueryMetadata().getStructureQueryDetail() == STRUCTURE_QUERY_DETAIL.ALL_STUBS;
        boolean isRefStubs = isAllStubs || complexQuery.getStructureQueryMetadata().getStructureQueryDetail() == STRUCTURE_QUERY_DETAIL.REFERENCED_STUBS;
        boolean isLatest = complexQuery.getStructureQueryMetadata().isReturnLatest();

        SDMX_STRUCTURE_TYPE type = complexQuery.getStructureReference().getMaintainableStructureType();
        // Emergency fix - if the type is NULL we'll ask for ANY SdmxStructureType
        if (type == null) {
            type = SDMX_STRUCTURE_TYPE.ANY;
        }
        ;
        MaintainableRefBean ref = complexQuery.getStructureReference().getMaintainableReference();
        Set<MaintainableBean> queryResultMaintainables;
        if (type == SDMX_STRUCTURE_TYPE.ANY) {
            queryResultMaintainables = getAllMaintainables(ref, isLatest, isAllStubs);
        } else if (type == SDMX_STRUCTURE_TYPE.ORGANISATION_SCHEME) {
            SdmxBeans beans = new SdmxBeansImpl();
            beans.addIdentifiables(getMaintainableBeans(AgencySchemeBean.class, ref, isLatest, isAllStubs));
            beans.addIdentifiables(getMaintainableBeans(DataProviderSchemeBean.class, ref, isLatest, isAllStubs));
            beans.addIdentifiables(getMaintainableBeans(OrganisationUnitSchemeBean.class, ref, isLatest, isAllStubs));
            beans.addIdentifiables(getMaintainableBeans(DataConsumerSchemeBean.class, ref, isLatest, isAllStubs));
            queryResultMaintainables = beans.getAllMaintainables();
        } else {
            //This creates a new hash set because the use of generics precludes doing it any other way
            queryResultMaintainables = new HashSet<MaintainableBean>(getMaintainableBeans(type.getMaintainableInterface(), ref, isLatest, isAllStubs));
        }

        Set<MaintainableBean> resolvedStubs = new HashSet<MaintainableBean>();
        if (externalReferenceRetrievalManager != null && !isAllStubs) {
            for (MaintainableBean currentMaint : queryResultMaintainables) {
                if (currentMaint.isExternalReference().isTrue()) {
                    currentMaint = externalReferenceRetrievalManager.resolveFullStructure(currentMaint);
                    resolvedStubs.add(currentMaint);
                }
            }
        }
        resolvedStubs.addAll(queryResultMaintainables);
        queryResultMaintainables = resolvedStubs;

        //Is this query for a identifiable child of the maintainable?  i.e if it is not referencing a maintainable, then it must be a child identifiable
        boolean isPartial;
        if (complexQuery.getStructureReference().getTargetReference() == null) {
            isPartial = false;
        } else {
            isPartial = !complexQuery.getStructureReference().getTargetReference().isMaintainable() && complexQuery.getStructureReference().getTargetReference().isIdentifiable();
        }

        //Create partial lists of the query result maintainables
        Set<MaintainableBean> partialBeans = new HashSet<MaintainableBean>();
        if (isPartial) {
            String identifiableId = complexQuery.getStructureReference().getFullId();

            Set<String> identSet = new HashSet<String>();
            identSet.add(identifiableId);
            for (MaintainableBean maint : queryResultMaintainables) {
                ItemSchemeBean isBean = ((ItemSchemeBean) maint).filterItems(identSet, true);
                if (isBean.getItems().size() > 0) {
                    partialBeans.add(isBean);
                }
            }
            queryResultMaintainables = partialBeans;
        }

        LOG.info("Returned " + queryResultMaintainables.size() + " results");

        HeaderBean header = null;
        if (headerRetrievalManager != null) {
            header = headerRetrievalManager.getHeader();
        }
        SdmxBeans referencedBeans = new SdmxBeansImpl(header);
        SdmxBeans referenceMerge = new SdmxBeansImpl();

        switch (complexQuery.getStructureQueryMetadata().getStructureReferenceDetail()) {
            case NONE:
                LOG.info("Reference detail NONE");
                break;
            case PARENTS:
                LOG.info("Reference detail PARENTS");
                resolveParents(queryResultMaintainables, referencedBeans, isRefStubs);
                break;
            case PARENTS_SIBLINGS:
                LOG.info("Reference detail PARENTS_SIBLINGS");
                resolveParents(queryResultMaintainables, referencedBeans, isRefStubs);
                resolveChildren(referencedBeans.getAllMaintainables(), referenceMerge, isRefStubs);
                referencedBeans.merge(referenceMerge);
                break;
            case CHILDREN:
                LOG.info("Reference detail CHILDREN");
                resolveChildren(queryResultMaintainables, referencedBeans, isRefStubs);
                break;
            case DESCENDANTS:
                LOG.info("Reference detail DESCENDANTS");
                resolveDescendants(queryResultMaintainables, referencedBeans, isRefStubs);
                break;
            case ALL:
                LOG.info("Reference detail ALL");
                resolveParents(queryResultMaintainables, referencedBeans, isRefStubs);
                resolveDescendants(queryResultMaintainables, referenceMerge, isRefStubs);
                referencedBeans.merge(referenceMerge);
                break;
            case SPECIFIC:
                LOG.info("Reference detail Children");
                resolveSpecific(queryResultMaintainables, referencedBeans, complexQuery.getStructureQueryMetadata().getSpecificStructureReference(), isRefStubs);
                break;
        }

        referencedBeans.addIdentifiables(queryResultMaintainables);

        // Determine if we are returning stubs or not
        if (isAllStubs && (referencedBeans != null)) {

            // It is not necessary to specify a serviceRetrievalManager so it may be null at this stage
            if (serviceRetrievalManager == null) {
                throw new SdmxNotImplementedException("Cannot return stubs since no ServiceRetrievalManager has been supplied!");
            }

            Set<MaintainableBean> stubSet = new HashSet<MaintainableBean>();

            for (MaintainableBean currentMaint : referencedBeans.getAllMaintainables(null)) {
                stubSet.add(serviceRetrievalManager.createStub(currentMaint));
            }
            referencedBeans = new SdmxBeansImpl(header);
            referencedBeans.addIdentifiables(stubSet);
        }


        LOG.info("Result Size : " + referencedBeans.getAllMaintainables().size());
        return referencedBeans;
    }

    private void createPartialBean(MaintainableBean maint, String[] id) {
        switch (maint.getStructureType()) {
            case CODE_LIST:
                break;
        }
    }

    private void resolveSpecific(Set<? extends MaintainableBean> resolveFor, SdmxBeans referencedBeans, SDMX_STRUCTURE_TYPE specificType, boolean returnStub) {
        LOG.info("Resolving Child Structures");
        for (MaintainableBean currentMaintainable : resolveFor) {
            LOG.debug("Resolving Children of " + currentMaintainable.getUrn());
            referencedBeans.addIdentifiables(getCrossReferenceRetrievalManager().getCrossReferencedStructures(currentMaintainable, returnStub, specificType));
            if (getCrossReferencingRetrievalManager() != null) {
                referencedBeans.addIdentifiables(getCrossReferencingRetrievalManager().getCrossReferencingStructures(currentMaintainable, returnStub, specificType));
            }
        }
        LOG.info(referencedBeans.getAllMaintainables().size() + " children found");
    }

    private void resolveDescendants(Set<? extends MaintainableBean> resolveFor, SdmxBeans referencedBeans, boolean returnStub) {
        int numBeans = -2;
        while (numBeans != resolveFor.size()) {
            numBeans = referencedBeans.getAllMaintainables().size();
            resolveChildren(resolveFor, referencedBeans, returnStub);
            resolveFor = referencedBeans.getAllMaintainables();
        }
        LOG.info(referencedBeans.getAllMaintainables().size() + " descendants found");
    }

    private void resolveChildren(Set<? extends MaintainableBean> resolveFor, SdmxBeans referencedBeans, boolean returnStub) {
        LOG.info("Resolving Child Structures");
        for (MaintainableBean currentMaintainable : resolveFor) {
            LOG.debug("Resolving Children of " + currentMaintainable.getUrn());
            referencedBeans.addIdentifiables(getCrossReferenceRetrievalManager().getCrossReferencedStructures(currentMaintainable, returnStub));
        }
        LOG.info(referencedBeans.getAllMaintainables().size() + " children found");
    }


    private void resolveParents(Set<? extends MaintainableBean> resolveFor, SdmxBeans referencedBeans, boolean returnStub) {
        LOG.info("Resolving Parents Structures");
        for (MaintainableBean currentMaintainable : resolveFor) {
            LOG.debug("Resolving Parents of " + currentMaintainable.getUrn());
            if (getCrossReferencingRetrievalManager() == null) {
                throw new SdmxNotImplementedException("Resolve parents not supported");
            }
            referencedBeans.addIdentifiables(getCrossReferencingRetrievalManager().getCrossReferencingStructures(currentMaintainable, returnStub));
        }
        LOG.info(referencedBeans.getAllMaintainables().size() + " parents found");
    }

    private void resolveReferences(SdmxBeans beans, boolean resolveAgencies) {
        CrossReferenceResolverEngine resolver = new CrossReferenceResolverEngineImpl();

        Map<IdentifiableBean, Set<IdentifiableBean>> crossReferenceMap = resolver.resolveReferences(beans, resolveAgencies, 0, this);

        for (IdentifiableBean key : crossReferenceMap.keySet()) {
            beans.addIdentifiable(key);
            for (IdentifiableBean value : crossReferenceMap.get(key)) {
                beans.addIdentifiable(value);
            }
        }
    }

    /*
     * If the set is of size 1, then returns the element in the set.
     * Returns null if the set is null or of size 0.
     * @throws SdmxException if the set contains more then 1 element
     */
    @SuppressWarnings("unchecked")
    private <T extends MaintainableBean> T extractFromSet(Set<T> set) {
        if (!ObjectUtil.validCollection(set)) {
            return null;
        }
        if (set.size() == 1) {
            return (T) set.toArray()[0];
        }
        throw new SdmxException("Did not expect more then 1 structure from query, got " + set.size() + " strutures.");
    }


    /**
     * Gets attachment constraints.
     *
     * @param ref          the ref
     * @param returnLatest the return latest
     * @param returnStub   the return stub
     * @return the attachment constraints
     */
    protected Set<AttachmentConstraintBean> getAttachmentConstraints(MaintainableRefBean ref, boolean returnLatest, boolean returnStub) {
        if (getProxy() != null) {
            return getProxy().getMaintainableBeans(AttachmentConstraintBean.class, ref, returnLatest, returnStub);
        }
        throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "getAttachmentConstraints");
    }


    /**
     * Gets categorisation beans.
     *
     * @param ref        the ref
     * @param returnStub the return stub
     * @return the categorisation beans
     */
    protected Set<CategorisationBean> getCategorisationBeans(MaintainableRefBean ref, boolean returnStub) {
        if (getProxy() != null) {
            return getProxy().getMaintainableBeans(CategorisationBean.class, ref, false, returnStub);
        }
        throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "getCategorisationBeans");
    }


    /**
     * Gets codelist beans.
     *
     * @param ref          the ref
     * @param returnLatest the return latest
     * @param returnStub   the return stub
     * @return the codelist beans
     */
    protected Set<CodelistBean> getCodelistBeans(MaintainableRefBean ref, boolean returnLatest, boolean returnStub) {
        if (getProxy() != null) {
            return getProxy().getMaintainableBeans(CodelistBean.class, ref, returnLatest, returnStub);
        }
        throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "getCodelistBeans");
    }


    /**
     * Gets concept scheme beans.
     *
     * @param ref          the ref
     * @param returnLatest the return latest
     * @param returnStub   the return stub
     * @return the concept scheme beans
     */
    protected Set<ConceptSchemeBean> getConceptSchemeBeans(MaintainableRefBean ref, boolean returnLatest, boolean returnStub) {
        if (getProxy() != null) {
            return getProxy().getMaintainableBeans(ConceptSchemeBean.class, ref, returnLatest, returnStub);
        }
        throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "getConceptSchemeBeans");
    }


    /**
     * Gets content constraints.
     *
     * @param ref          the ref
     * @param returnLatest the return latest
     * @param returnStub   the return stub
     * @return the content constraints
     */
    protected Set<ContentConstraintBean> getContentConstraints(MaintainableRefBean ref, boolean returnLatest, boolean returnStub) {
        if (getProxy() != null) {
            return getProxy().getMaintainableBeans(ContentConstraintBean.class, ref, returnLatest, returnStub);
        }
        throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "getContentConstraints");
    }


    /**
     * Gets category scheme beans.
     *
     * @param ref          the ref
     * @param returnLatest the return latest
     * @param returnStub   the return stub
     * @return the category scheme beans
     */
    protected Set<CategorySchemeBean> getCategorySchemeBeans(MaintainableRefBean ref, boolean returnLatest, boolean returnStub) {
        if (getProxy() != null) {
            return getProxy().getMaintainableBeans(CategorySchemeBean.class, ref, returnLatest, returnStub);
        }
        throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "getCategorySchemeBeans");
    }


    /**
     * Gets dataflow beans.
     *
     * @param ref          the ref
     * @param returnLatest the return latest
     * @param returnStub   the return stub
     * @return the dataflow beans
     */
    protected Set<DataflowBean> getDataflowBeans(MaintainableRefBean ref, boolean returnLatest, boolean returnStub) {
        if (getProxy() != null) {
            return getProxy().getMaintainableBeans(DataflowBean.class, ref, returnLatest, returnStub);
        }
        throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "getDataflowBeans");
    }


    /**
     * Gets hierarchic code list beans.
     *
     * @param ref          the ref
     * @param returnLatest the return latest
     * @param returnStub   the return stub
     * @return the hierarchic code list beans
     */
    protected Set<HierarchicalCodelistBean> getHierarchicCodeListBeans(MaintainableRefBean ref, boolean returnLatest, boolean returnStub) {
        if (getProxy() != null) {
            return getProxy().getMaintainableBeans(HierarchicalCodelistBean.class, ref, returnLatest, returnStub);
        }
        throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "getHierarchicCodeListBeans");
    }


    /**
     * Gets metadataflow beans.
     *
     * @param ref          the ref
     * @param returnLatest the return latest
     * @param returnStub   the return stub
     * @return the metadataflow beans
     */
    protected Set<MetadataFlowBean> getMetadataflowBeans(MaintainableRefBean ref, boolean returnLatest, boolean returnStub) {
        if (getProxy() != null) {
            return getProxy().getMaintainableBeans(MetadataFlowBean.class, ref, returnLatest, returnStub);
        }
        throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "getMetadataflowBeans");
    }


    /**
     * Gets data structure beans.
     *
     * @param ref          the ref
     * @param returnLatest the return latest
     * @param returnStub   the return stub
     * @return the data structure beans
     */
    protected Set<DataStructureBean> getDataStructureBeans(MaintainableRefBean ref, boolean returnLatest, boolean returnStub) {
        if (getProxy() != null) {
            return getProxy().getMaintainableBeans(DataStructureBean.class, ref, returnLatest, returnStub);
        }
        throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "getDataStructureBeans");
    }


    /**
     * Gets metadata structure beans.
     *
     * @param ref          the ref
     * @param returnLatest the return latest
     * @param returnStub   the return stub
     * @return the metadata structure beans
     */
    protected Set<MetadataStructureDefinitionBean> getMetadataStructureBeans(MaintainableRefBean ref, boolean returnLatest, boolean returnStub) {
        if (getProxy() != null) {
            return getProxy().getMaintainableBeans(MetadataStructureDefinitionBean.class, ref, returnLatest, returnStub);
        }
        throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "getMetadataStructureBeans");
    }


    /**
     * Gets organisation unit scheme beans.
     *
     * @param ref          the ref
     * @param returnLatest the return latest
     * @param returnStub   the return stub
     * @return the organisation unit scheme beans
     */
    protected Set<OrganisationUnitSchemeBean> getOrganisationUnitSchemeBeans(MaintainableRefBean ref, boolean returnLatest, boolean returnStub) {
        if (getProxy() != null) {
            return getProxy().getMaintainableBeans(OrganisationUnitSchemeBean.class, ref, returnLatest, returnStub);
        }
        throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "getOrganisationUnitSchemeBeans");
    }


    /**
     * Gets data provider scheme beans.
     *
     * @param ref        the ref
     * @param returnStub the return stub
     * @return the data provider scheme beans
     */
    protected Set<DataProviderSchemeBean> getDataProviderSchemeBeans(MaintainableRefBean ref, boolean returnStub) {
        if (getProxy() != null) {
            return getProxy().getMaintainableBeans(DataProviderSchemeBean.class, ref, false, returnStub);
        }
        throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "getDataProviderSchemeBeans");
    }


    /**
     * Gets data consumer scheme beans.
     *
     * @param ref        the ref
     * @param returnStub the return stub
     * @return the data consumer scheme beans
     */
    protected Set<DataConsumerSchemeBean> getDataConsumerSchemeBeans(MaintainableRefBean ref, boolean returnStub) {
        if (getProxy() != null) {
            return getProxy().getMaintainableBeans(DataConsumerSchemeBean.class, ref, false, returnStub);
        }
        throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "getDataConsumerSchemeBeans");
    }


    /**
     * Gets agency scheme beans.
     *
     * @param ref        the ref
     * @param returnStub the return stub
     * @return the agency scheme beans
     */
    protected Set<AgencySchemeBean> getAgencySchemeBeans(MaintainableRefBean ref, boolean returnStub) {
        if (getProxy() != null) {
            return getProxy().getMaintainableBeans(AgencySchemeBean.class, ref, false, returnStub);
        }
        throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "getAgencySchemeBeans");
    }


    /**
     * Gets process beans.
     *
     * @param ref          the ref
     * @param returnLatest the return latest
     * @param returnStub   the return stub
     * @return the process beans
     */
    protected Set<ProcessBean> getProcessBeans(MaintainableRefBean ref, boolean returnLatest, boolean returnStub) {
        if (getProxy() != null) {
            return getProxy().getMaintainableBeans(ProcessBean.class, ref, returnLatest, returnStub);
        }
        throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "getProcessBeans");
    }


    /**
     * Gets provision agreement beans.
     *
     * @param ref          the ref
     * @param returnLatest the return latest
     * @param returnStub   the return stub
     * @return the provision agreement beans
     */
    protected Set<ProvisionAgreementBean> getProvisionAgreementBeans(MaintainableRefBean ref, boolean returnLatest, boolean returnStub) {
        if (getProxy() != null) {
            return getProxy().getMaintainableBeans(ProvisionAgreementBean.class, ref, returnLatest, returnStub);
        }
        throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "getProvisionAgreementBeans");
    }


    /**
     * Gets reporting taxonomy beans.
     *
     * @param ref          the ref
     * @param returnLatest the return latest
     * @param returnStub   the return stub
     * @return the reporting taxonomy beans
     */
    protected Set<ReportingTaxonomyBean> getReportingTaxonomyBeans(MaintainableRefBean ref, boolean returnLatest, boolean returnStub) {
        if (getProxy() != null) {
            return getProxy().getMaintainableBeans(ReportingTaxonomyBean.class, ref, returnLatest, returnStub);
        }
        throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "getReportingTaxonomyBeans");
    }

    /**
     * Gets structure set beans.
     *
     * @param ref          the ref
     * @param returnLatest the return latest
     * @param returnStub   the return stub
     * @return the structure set beans
     */
    protected Set<StructureSetBean> getStructureSetBeans(MaintainableRefBean ref, boolean returnLatest, boolean returnStub) {
        if (getProxy() != null) {
            return getProxy().getMaintainableBeans(StructureSetBean.class, ref, returnLatest, returnStub);
        }
        throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "getStructureSetBeans");
    }

    /**
     * Gets subscription beans.
     *
     * @param ref          the ref
     * @param returnLatest the return latest
     * @param returnStub   the return stub
     * @return the subscription beans
     */
    protected Set<SubscriptionBean> getSubscriptionBeans(MaintainableRefBean ref, boolean returnLatest, boolean returnStub) {
        if (getProxy() != null) {
            return getProxy().getMaintainableBeans(SubscriptionBean.class, ref, returnLatest, returnStub);
        }
        throw new SdmxNotImplementedException(ExceptionCode.UNSUPPORTED, "getSubscriptionBean");
    }

    /**
     * Gets registration beans.
     *
     * @param ref the ref
     * @return the registration beans
     */
    protected Set<RegistrationBean> getRegistrationBeans(MaintainableRefBean ref) {
        if (registrationRetrievalManager != null) {
            return registrationRetrievalManager.getRegistrations(ref);
        } else if (getProxy() != null) {
            return getProxy().getMaintainableBeans(RegistrationBean.class, ref, false, false);
        }
        throw new SdmxNotImplementedException("getRegistrationBeans");
    }

    /**
     * Gets cross reference retrieval manager.
     *
     * @return the cross reference retrieval manager
     */
    protected CrossReferencedRetrievalManager getCrossReferenceRetrievalManager() {
        return crossReferenceRetrievalManager;
    }

    /**
     * Sets cross reference retrieval manager.
     *
     * @param crossReferenceRetrievalManager the cross reference retrieval manager
     */
    public void setCrossReferenceRetrievalManager(CrossReferencedRetrievalManager crossReferenceRetrievalManager) {
        this.crossReferenceRetrievalManager = crossReferenceRetrievalManager;
    }

    /**
     * Gets cross referencing retrieval manager.
     *
     * @return the cross referencing retrieval manager
     */
    protected CrossReferencingRetrievalManager getCrossReferencingRetrievalManager() {
        return crossReferencingRetrievalManager;
    }

    /**
     * Sets cross referencing retrieval manager.
     *
     * @param crossReferencingRetrievalManager the cross referencing retrieval manager
     */
    public void setCrossReferencingRetrievalManager(CrossReferencingRetrievalManager crossReferencingRetrievalManager) {
        this.crossReferencingRetrievalManager = crossReferencingRetrievalManager;
    }

    /**
     * Sets external reference retrieval manager.
     *
     * @param externalReferenceRetrievalManager the external reference retrieval manager
     */
    public void setExternalReferenceRetrievalManager(ExternalReferenceRetrievalManager externalReferenceRetrievalManager) {
        this.externalReferenceRetrievalManager = externalReferenceRetrievalManager;
    }

    /**
     * If set then will be used to create stubs
     *
     * @param serviceRetrievalManager the service retrieval manager
     */
    public void setServiceRetrievalManager(ServiceRetrievalManager serviceRetrievalManager) {
        this.serviceRetrievalManager = serviceRetrievalManager;
    }

    /**
     * Sets registration retrieval manager.
     *
     * @param registrationRetrievalManager the registration retrieval manager
     */
    public void setRegistrationRetrievalManager(RegistrationBeanRetrievalManager registrationRetrievalManager) {
        this.registrationRetrievalManager = registrationRetrievalManager;
    }

    /**
     * Sets header retrieval manager.
     *
     * @param headerRetrievalManager the header retrieval manager
     */
    public void setHeaderRetrievalManager(HeaderRetrievalManager headerRetrievalManager) {
        this.headerRetrievalManager = headerRetrievalManager;
    }

    /**
     * Gets proxy.
     *
     * @return the proxy
     */
    protected SdmxBeanRetrievalManager getProxy() {
        return this.proxy;
    }

    /**
     * Sets proxy.
     *
     * @param proxy the proxy
     */
    public void setProxy(SdmxBeanRetrievalManager proxy) {
        this.proxy = proxy;
    }
}
