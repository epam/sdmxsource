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
package org.sdmxsource.sdmx.sdmxbeans.model.beans.mapping;

import org.sdmx.resources.sdmxml.schemas.v20.structure.*;
import org.sdmx.resources.sdmxml.schemas.v21.common.StructureOrUsageReferenceType;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxSemmanticException;
import org.sdmxsource.sdmx.api.model.beans.base.SDMXBean;
import org.sdmxsource.sdmx.api.model.beans.base.SdmxStructureBean;
import org.sdmxsource.sdmx.api.model.beans.mapping.RelatedStructuresBean;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.reference.StructureReferenceBean;
import org.sdmxsource.sdmx.api.model.mutable.mapping.RelatedStructuresMutableBean;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.SdmxStructureBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.util.RefUtil;
import org.sdmxsource.sdmx.util.beans.reference.CrossReferenceBeanImpl;
import org.sdmxsource.util.ObjectUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * The type Related structures bean.
 */
public class RelatedStructuresBeanImpl extends SdmxStructureBeanImpl implements RelatedStructuresBean {
    private static final long serialVersionUID = 1L;
    private List<CrossReferenceBean> keyFamilyRef = new ArrayList<CrossReferenceBean>();
    private List<CrossReferenceBean> metadataStructureRef = new ArrayList<CrossReferenceBean>();
    private List<CrossReferenceBean> conceptSchemeRef = new ArrayList<CrossReferenceBean>();
    private List<CrossReferenceBean> categorySchemeRef = new ArrayList<CrossReferenceBean>();
    private List<CrossReferenceBean> orgSchemeRef = new ArrayList<CrossReferenceBean>();
    private List<CrossReferenceBean> hierCodelistRef = new ArrayList<CrossReferenceBean>();

    /**
     * Instantiates a new Related structures bean.
     *
     * @param bean   the bean
     * @param parent the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM MUTABLE BEANS			 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public RelatedStructuresBeanImpl(RelatedStructuresMutableBean bean, SdmxStructureBean parent) {
        super(bean, parent);
        keyFamilyRef = createCrossReferenceList(bean.getDataStructureRef());
        metadataStructureRef = createCrossReferenceList(bean.getMetadataStructureRef());
        conceptSchemeRef = createCrossReferenceList(bean.getConceptSchemeRef());
        categorySchemeRef = createCrossReferenceList(bean.getCategorySchemeRef());
        orgSchemeRef = createCrossReferenceList(bean.getOrgSchemeRef());
        hierCodelistRef = createCrossReferenceList(bean.getHierCodelistRef());
    }

    /**
     * Instantiates a new Related structures bean.
     *
     * @param relStrucTypeList the rel struc type list
     * @param parent           the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2.1 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public RelatedStructuresBeanImpl(List<StructureOrUsageReferenceType> relStrucTypeList, SdmxStructureBean parent) {
        super(SDMX_STRUCTURE_TYPE.RELATED_STRUCTURES, parent);

        for (StructureOrUsageReferenceType relStrucType : relStrucTypeList) {
            CrossReferenceBean structureReference = RefUtil.createReference(this, relStrucType);
            switch (structureReference.getTargetReference()) {
                case DSD:
                    keyFamilyRef.add(structureReference);
                    break;
                case MSD:
                    metadataStructureRef.add(structureReference);
                    break;
                case CONCEPT_SCHEME:
                    conceptSchemeRef.add(structureReference);
                    break;
                case CATEGORY_SCHEME:
                    categorySchemeRef.add(structureReference);
                    break;
                case ORGANISATION_UNIT_SCHEME:
                    orgSchemeRef.add(structureReference);
                    break;
                case HIERARCHICAL_CODELIST:
                    hierCodelistRef.add(structureReference);
                    break;
                default:
                    throw new SdmxSemmanticException("RelatedStructuresBean can not reference : " + structureReference.getTargetReference().getType());
            }
        }
    }


    /**
     * Instantiates a new Related structures bean.
     *
     * @param relStrucType the rel struc type
     * @param parent       the parent
     */
///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////BUILD FROM V2 SCHEMA				 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public RelatedStructuresBeanImpl(RelatedStructuresType relStrucType, SdmxStructureBean parent) {
        super(SDMX_STRUCTURE_TYPE.RELATED_STRUCTURES, parent);
        // get list of key family ref
        if (relStrucType.getKeyFamilyRefList() != null) {
            for (KeyFamilyRefType keyFam : relStrucType.getKeyFamilyRefList()) {
                if (ObjectUtil.validString(keyFam.getURN())) {
                    keyFamilyRef.add(new CrossReferenceBeanImpl(this, keyFam.getURN()));
                } else {
                    keyFamilyRef.add(new CrossReferenceBeanImpl(this, keyFam.getKeyFamilyAgencyID(), keyFam.getKeyFamilyID(), keyFam.getVersion(), SDMX_STRUCTURE_TYPE.DSD));
                }
            }
        }

        // get list of metadata structure ref
        if (relStrucType.getMetadataStructureRefList() != null) {
            for (MetadataStructureRefType metStruc : relStrucType.getMetadataStructureRefList()) {
                if (ObjectUtil.validString(metStruc.getURN())) {
                    metadataStructureRef.add(new CrossReferenceBeanImpl(this, metStruc.getURN()));
                } else {
                    metadataStructureRef.add(new CrossReferenceBeanImpl(this, metStruc.getMetadataStructureAgencyID(), metStruc.getMetadataStructureID(), metStruc.getVersion(), SDMX_STRUCTURE_TYPE.MSD));
                }
            }
        }

        // get list of concept scheme ref
        if (relStrucType.getConceptSchemeRefList() != null) {
            for (ConceptSchemeRefType conStruc : relStrucType.getConceptSchemeRefList()) {
                if (ObjectUtil.validString(conStruc.getURN())) {
                    conceptSchemeRef.add(new CrossReferenceBeanImpl(this, conStruc.getURN()));
                } else {
                    conceptSchemeRef.add(new CrossReferenceBeanImpl(this, conStruc.getAgencyID(), conStruc.getConceptSchemeID(), conStruc.getVersion(), SDMX_STRUCTURE_TYPE.CONCEPT_SCHEME));
                }
            }
        }

        // get list of category scheme ref
        if (relStrucType.getCategorySchemeRefList() != null) {
            for (CategorySchemeRefType catStruc : relStrucType.getCategorySchemeRefList()) {
                if (ObjectUtil.validString(catStruc.getURN())) {
                    categorySchemeRef.add(new CrossReferenceBeanImpl(this, catStruc.getURN()));
                } else {
                    categorySchemeRef.add(new CrossReferenceBeanImpl(this, catStruc.getAgencyID(), catStruc.getCategorySchemeID(), catStruc.getVersion(), SDMX_STRUCTURE_TYPE.CATEGORY_SCHEME));
                }
            }
        }

        // get list of organisation scheme ref
        if (relStrucType.getOrganisationSchemeRefList() != null) {
            for (OrganisationSchemeRefType orgScheme : relStrucType.getOrganisationSchemeRefList()) {
                if (ObjectUtil.validString(orgScheme.getURN())) {
                    orgSchemeRef.add(new CrossReferenceBeanImpl(this, orgScheme.getURN()));
                } else {
                    orgSchemeRef.add(new CrossReferenceBeanImpl(this, orgScheme.getAgencyID(), orgScheme.getOrganisationSchemeID(), orgScheme.getVersion(), SDMX_STRUCTURE_TYPE.ORGANISATION_UNIT_SCHEME));
                }
            }
        }

        // get list of hierarchical codelist ref
        if (relStrucType.getHierarchicalCodelistRefList() != null) {
            for (HierarchicalCodelistRefType hierCode : relStrucType.getHierarchicalCodelistRefList()) {
                if (ObjectUtil.validString(hierCode.getURN())) {
                    hierCodelistRef.add(new CrossReferenceBeanImpl(this, hierCode.getURN()));
                } else {
                    hierCodelistRef.add(new CrossReferenceBeanImpl(this, hierCode.getAgencyID(), hierCode.getHierarchicalCodelistID(), hierCode.getVersion(), SDMX_STRUCTURE_TYPE.HIERARCHICAL_CODELIST));
                }
            }
        }
    }

    private List<CrossReferenceBean> createCrossReferenceList(List<StructureReferenceBean> structureReferences) {
        List<CrossReferenceBean> retrurnList = new ArrayList<CrossReferenceBean>();
        if (structureReferences != null) {
            for (StructureReferenceBean currentStructureReference : structureReferences) {
                retrurnList.add(new CrossReferenceBeanImpl(this, currentStructureReference));
            }
        }
        return retrurnList;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////DEEP EQUALS							 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean deepEquals(SDMXBean bean, boolean includeFinalProperties) {
        if (bean == null) {
            return false;
        }
        if (bean.getStructureType() == this.getStructureType()) {
            RelatedStructuresBean that = (RelatedStructuresBean) bean;
            if (!ObjectUtil.equivalentCollection(keyFamilyRef, that.getDataStructureRef())) {
                return false;
            }
            if (!ObjectUtil.equivalentCollection(metadataStructureRef, that.getMetadataStructureRef())) {
                return false;
            }
            if (!ObjectUtil.equivalentCollection(conceptSchemeRef, that.getConceptSchemeRef())) {
                return false;
            }
            if (!ObjectUtil.equivalentCollection(categorySchemeRef, that.getCategorySchemeRef())) {
                return false;
            }
            if (!ObjectUtil.equivalentCollection(orgSchemeRef, that.getOrgSchemeRef())) {
                return false;
            }
            if (!ObjectUtil.equivalentCollection(hierCodelistRef, that.getHierCodelistRef())) {
                return false;
            }
            return true;
        }
        return false;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////GETTERS								 //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected Set<CrossReferenceBean> getCrossReferenceInternal() {
        Set<CrossReferenceBean> references = new HashSet<CrossReferenceBean>();
        references.addAll(keyFamilyRef);
        references.addAll(metadataStructureRef);
        references.addAll(conceptSchemeRef);
        references.addAll(categorySchemeRef);
        references.addAll(orgSchemeRef);
        references.addAll(hierCodelistRef);
        return references;
    }

    @Override
    public List<CrossReferenceBean> getDataStructureRef() {
        return new ArrayList<CrossReferenceBean>(keyFamilyRef);
    }

    @Override
    public List<CrossReferenceBean> getMetadataStructureRef() {
        return new ArrayList<CrossReferenceBean>(metadataStructureRef);
    }

    @Override
    public List<CrossReferenceBean> getConceptSchemeRef() {
        return new ArrayList<CrossReferenceBean>(conceptSchemeRef);
    }

    @Override
    public List<CrossReferenceBean> getCategorySchemeRef() {
        return new ArrayList<CrossReferenceBean>(categorySchemeRef);
    }

    @Override
    public List<CrossReferenceBean> getOrgSchemeRef() {
        return new ArrayList<CrossReferenceBean>(orgSchemeRef);
    }

    @Override
    public List<CrossReferenceBean> getHierCodelistRef() {
        return new ArrayList<CrossReferenceBean>(hierCodelistRef);
    }
}
