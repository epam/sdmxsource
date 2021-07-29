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
package org.sdmxsource.sdmx.structureparser.builder.xmlBeans.v2;

import org.sdmx.resources.sdmxml.schemas.v20.structure.*;
import org.sdmxsource.sdmx.api.constants.SDMX_STRUCTURE_TYPE;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.model.beans.mapping.*;
import org.sdmxsource.sdmx.api.model.beans.reference.CrossReferenceBean;
import org.sdmxsource.sdmx.api.model.beans.reference.MaintainableRefBean;

import java.util.List;


/**
 * The type Structure set xml bean builder.
 */
public class StructureSetXmlBeanBuilder extends AbstractBuilder {

    /**
     * Build structure set type.
     *
     * @param buildFrom the build from
     * @return the structure set type
     * @throws SdmxException the sdmx exception
     */
    public StructureSetType build(StructureSetBean buildFrom) throws SdmxException {
        StructureSetType builtObj = StructureSetType.Factory.newInstance();

        //MAINTAINABLE ATTRIBUTES
        if (validString(buildFrom.getAgencyId())) {
            builtObj.setAgencyID(buildFrom.getAgencyId());
        }
        if (validString(buildFrom.getId())) {
            builtObj.setId(buildFrom.getId());
        }
        if (buildFrom.getUri() != null) {
            builtObj.setUri(buildFrom.getUri().toString());
        } else if (buildFrom.getStructureURL() != null) {
            builtObj.setUri(buildFrom.getStructureURL().toString());
        } else if (buildFrom.getServiceURL() != null) {
            builtObj.setUri(buildFrom.getStructureURL().toString());
        }
        if (validString(buildFrom.getUrn())) {
            builtObj.setUrn(buildFrom.getUrn());
        }
        if (validString(buildFrom.getVersion())) {
            builtObj.setVersion(buildFrom.getVersion());
        }
        if (buildFrom.getStartDate() != null) {
            builtObj.setValidFrom(buildFrom.getStartDate().getDate());
        }
        if (buildFrom.getEndDate() != null) {
            builtObj.setValidTo(buildFrom.getEndDate().getDate());
        }
        if (validCollection(buildFrom.getNames())) {
            builtObj.setNameArray(getTextType(buildFrom.getNames()));
        }
        if (validCollection(buildFrom.getDescriptions())) {
            builtObj.setDescriptionArray(getTextType(buildFrom.getDescriptions()));
        }
        if (hasAnnotations(buildFrom)) {
            builtObj.setAnnotations(getAnnotationsType(buildFrom));
        }
        if (buildFrom.isExternalReference().isSet()) {
            builtObj.setIsExternalReference(buildFrom.isExternalReference().isTrue());
        }
        if (buildFrom.isFinal().isSet()) {
            builtObj.setIsFinal(buildFrom.isFinal().isTrue());
        }

        //MAPS
        processRelatedStructures(builtObj, buildFrom);
        if (buildFrom.getStructureMapList() != null) {
            for (StructureMapBean each : buildFrom.getStructureMapList())
                processStructureMap(builtObj, each);
        }
        if (buildFrom.getCodelistMapList() != null) {
            for (CodelistMapBean each : buildFrom.getCodelistMapList())
                processCodelistMap(builtObj, each);
        }
        if (buildFrom.getCategorySchemeMapList() != null) {
            for (CategorySchemeMapBean each : buildFrom.getCategorySchemeMapList())
                processCategorySchemeMap(builtObj, each);
        }
        if (buildFrom.getConceptSchemeMapList() != null) {
            for (ConceptSchemeMapBean each : buildFrom.getConceptSchemeMapList())
                processConceptSchemeMap(builtObj, each);
        }
        if (buildFrom.getOrganisationSchemeMapList() != null) {
            for (OrganisationSchemeMapBean each : buildFrom.getOrganisationSchemeMapList())
                processOrganisationSchemeMap(builtObj, each);
        }
        return builtObj;
    }

    private void processRelatedStructures(StructureSetType structureSetType, StructureSetBean buildFrom) {
        if (buildFrom.getRelatedStructures() != null) {
            RelatedStructuresType relatedStructuresType = structureSetType.addNewRelatedStructures();
            RelatedStructuresBean relatedStructures = buildFrom.getRelatedStructures();
            //KEY FAMILY REF
            for (CrossReferenceBean ref : relatedStructures.getDataStructureRef()) {
                KeyFamilyRefType kfRefType = relatedStructuresType.addNewKeyFamilyRef();
                setDataStructureRefAttributes(kfRefType, ref);
            }

            //METADATA STRUCTURE REF
            for (CrossReferenceBean ref : relatedStructures.getMetadataStructureRef()) {
                MetadataStructureRefType refType = relatedStructuresType.addNewMetadataStructureRef();
                setMetadataStructureRefAttributes(refType, ref);
            }

            //CONCEPT SCHEME REF
            for (CrossReferenceBean ref : relatedStructures.getConceptSchemeRef()) {
                ConceptSchemeRefType refType = relatedStructuresType.addNewConceptSchemeRef();
                setConceptSchemeRefAttributes(refType, ref);
            }

            //CATEGORY SCHEME REF
            for (CrossReferenceBean ref : relatedStructures.getCategorySchemeRef()) {
                CategorySchemeRefType refType = relatedStructuresType.addNewCategorySchemeRef();
                setCategorySchemeRefAttributes(refType, ref);
            }

            //ORGANISATION SCHEME REF
            for (CrossReferenceBean ref : relatedStructures.getOrgSchemeRef()) {
                OrganisationSchemeRefType refType = relatedStructuresType.addNewOrganisationSchemeRef();
                setOrganisationSchemeRefAttributes(refType, ref);
            }

            //HCL REF
            for (CrossReferenceBean ref : relatedStructures.getHierCodelistRef()) {
                HierarchicalCodelistRefType refType = relatedStructuresType.addNewHierarchicalCodelistRef();
                setHclRefAttributes(refType, ref);
            }
        }
    }

    private void processStructureMap(StructureSetType structureSetType, StructureMapBean buildFrom) {
        StructureMapType structureMapType = structureSetType.addNewStructureMap();

        structureMapType.setIsExtension(buildFrom.isExtension());

        if (validString(buildFrom.getId())) {
            structureMapType.setId(buildFrom.getId());
        }
        if (validCollection(buildFrom.getNames())) {
            structureMapType.setNameArray(getTextType(buildFrom.getNames()));
        }
        if (validCollection(buildFrom.getDescriptions())) {
            structureMapType.setDescriptionArray(getTextType(buildFrom.getDescriptions()));
        }
        if (buildFrom.getSourceRef() != null) {
            if (buildFrom.getSourceRef().getTargetReference() == SDMX_STRUCTURE_TYPE.DSD) {
                KeyFamilyRefType kfRefType = structureMapType.addNewKeyFamilyRef();
                setDataStructureRefAttributes(kfRefType, buildFrom.getSourceRef());
            } else if (buildFrom.getSourceRef().getTargetReference() == SDMX_STRUCTURE_TYPE.MSD) {
                MetadataStructureRefType refType = structureMapType.addNewMetadataStructureRef();
                setMetadataStructureRefAttributes(refType, buildFrom.getSourceRef());
            }
        }
        if (buildFrom.getTargetRef() != null) {
            if (buildFrom.getTargetRef().getTargetReference() == SDMX_STRUCTURE_TYPE.DSD) {
                KeyFamilyRefType kfRefType = structureMapType.addNewTargetKeyFamilyRef();
                setDataStructureRefAttributes(kfRefType, buildFrom.getTargetRef());
            } else if (buildFrom.getTargetRef().getTargetReference() == SDMX_STRUCTURE_TYPE.MSD) {
                MetadataStructureRefType refType = structureMapType.addNewTargetMetadataStructureRef();
                setMetadataStructureRefAttributes(refType, buildFrom.getTargetRef());
            }
        }
        if (validCollection(buildFrom.getComponents())) {
            for (ComponentMapBean componentMapBean : buildFrom.getComponents()) {
                ComponentMapType componentMapType = structureMapType.addNewComponentMap();
                processComponent(componentMapType, componentMapBean);
            }
        }
        if (hasAnnotations(buildFrom)) {
            structureMapType.setAnnotations(getAnnotationsType(buildFrom));
        }
    }

    private void processComponent(ComponentMapType componentMapType, ComponentMapBean componentMapBean) {
        if (componentMapBean.getMapConceptRef() != null) {
            componentMapType.setMapConceptRef(componentMapBean.getMapConceptRef());
        }
        if (componentMapBean.getMapTargetConceptRef() != null) {
            componentMapType.setMapTargetConceptRef(componentMapBean.getMapTargetConceptRef());
        }
        if (componentMapBean.getRepMapRef() != null) {
            if (componentMapBean.getRepMapRef().getToTextFormat() != null) {
                TextFormatType textForamtType = TextFormatType.Factory.newInstance();
                componentMapType.setToTextFormat(textForamtType);
                super.populateTextFormatType(textForamtType, componentMapBean.getRepMapRef().getToTextFormat());
            }
            if (componentMapBean.getRepMapRef().getToValueType() != null) {
                switch (componentMapBean.getRepMapRef().getToValueType()) {
                    case DESCRIPTION:
                        componentMapType.setToValueType(ToValueTypeType.DESCRIPTION);
                        break;
                    case NAME:
                        componentMapType.setToValueType(ToValueTypeType.NAME);
                        break;
                    case VALUE:
                        componentMapType.setToValueType(ToValueTypeType.VALUE);
                        break;
                }
            }
        }

    }

    private void processCodelistMap(StructureSetType structureSetType, CodelistMapBean buildFrom) {
        CodelistMapType codelistMap = structureSetType.addNewCodelistMap();

        if (validString(buildFrom.getId())) {
            codelistMap.setId(buildFrom.getId());
        }
        if (validCollection(buildFrom.getNames())) {
            codelistMap.setNameArray(getTextType(buildFrom.getNames()));
        }
        if (validCollection(buildFrom.getDescriptions())) {
            codelistMap.setDescriptionArray(getTextType(buildFrom.getDescriptions()));
        }
        if (buildFrom.getSourceRef() != null) {
            if (buildFrom.getSourceRef().getTargetReference() == SDMX_STRUCTURE_TYPE.CODE_LIST) {
                CodelistRefType refType = codelistMap.addNewCodelistRef();
                setCodelistRefAttributes(refType, buildFrom.getSourceRef());
            } else if (buildFrom.getSourceRef().getTargetReference() == SDMX_STRUCTURE_TYPE.HIERARCHICAL_CODELIST) {
                HierarchicalCodelistRefType refType = codelistMap.addNewHierarchicalCodelistRef();
                setHclRefAttributes(refType, buildFrom.getSourceRef());
            }
        }
        if (buildFrom.getTargetRef() != null) {
            if (buildFrom.getTargetRef().getTargetReference() == SDMX_STRUCTURE_TYPE.CODE_LIST) {
                CodelistRefType refType = codelistMap.addNewTargetCodelistRef();
                setCodelistRefAttributes(refType, buildFrom.getTargetRef());
            } else if (buildFrom.getTargetRef().getTargetReference() == SDMX_STRUCTURE_TYPE.HIERARCHICAL_CODELIST) {
                HierarchicalCodelistRefType refType = codelistMap.addNewTargetHierarchicalCodelistRef();
                setHclRefAttributes(refType, buildFrom.getTargetRef());
            }
        }
        if (validCollection(buildFrom.getItems())) {
            for (ItemMapBean itemMapBean : buildFrom.getItems()) {
                CodeMapType codeMaptype = codelistMap.addNewCodeMap();
                if (validString(itemMapBean.getSourceId())) {
                    codeMaptype.setMapCodeRef(itemMapBean.getSourceId());
                }
                if (validString(itemMapBean.getTargetId())) {
                    codeMaptype.setMapTargetCodeRef(itemMapBean.getTargetId());
                }
            }
        }
        if (hasAnnotations(buildFrom)) {
            codelistMap.setAnnotations(getAnnotationsType(buildFrom));
        }
    }

    private void processCategorySchemeMap(StructureSetType structureSetType, CategorySchemeMapBean buildFrom) {
        CategorySchemeMapType catSchemeMap = structureSetType.addNewCategorySchemeMap();

        if (validString(buildFrom.getId())) {
            catSchemeMap.setId(buildFrom.getId());
        }
        if (validCollection(buildFrom.getNames())) {
            catSchemeMap.setNameArray(getTextType(buildFrom.getNames()));
        }
        if (validCollection(buildFrom.getDescriptions())) {
            catSchemeMap.setDescriptionArray(getTextType(buildFrom.getDescriptions()));
        }
        if (buildFrom.getSourceRef() != null) {
            CategorySchemeRefType catRefType = catSchemeMap.addNewCategorySchemeRef();
            setCategorySchemeRefAttributes(catRefType, buildFrom.getSourceRef());
        }
        if (buildFrom.getTargetRef() != null) {
            CategorySchemeRefType catRefType = catSchemeMap.addNewTargetCategorySchemeRef();
            setCategorySchemeRefAttributes(catRefType, buildFrom.getTargetRef());
        }
        if (validCollection(buildFrom.getCategoryMaps())) {
            for (CategoryMapBean categoryMapBean : buildFrom.getCategoryMaps()) {
                CategoryMapType categoryMapType = catSchemeMap.addNewCategoryMap();
                if (validString(categoryMapBean.getAlias())) {
                    categoryMapType.setCategoryAlias(categoryMapBean.getAlias());
                }
                if (categoryMapBean.getSourceId() != null) {
                    CategoryIDType categoryIdType = categoryMapType.addNewCategoryID();
                    setCategoryIdAttributes(categoryIdType, categoryMapBean.getSourceId(), 0);
                }
                if (categoryMapBean.getTargetId() != null) {
                    CategoryIDType categoryIdType = categoryMapType.addNewTargetCategoryID();
                    setCategoryIdAttributes(categoryIdType, categoryMapBean.getTargetId(), 0);
                }
            }
        }
        if (hasAnnotations(buildFrom)) {
            catSchemeMap.setAnnotations(getAnnotationsType(buildFrom));
        }
    }

    private void setCategoryIdAttributes(CategoryIDType categoryIdType, List<String> categoryIds, int currentPos) {
        if (categoryIds != null && categoryIds.size() > currentPos) {
            categoryIdType.setID(categoryIds.get(currentPos));
        }
        int nextPos = currentPos++;
        if (categoryIds.size() > nextPos) {
            CategoryIDType subType = categoryIdType.addNewCategoryID();
            setCategoryIdAttributes(subType, categoryIds, nextPos);
        }
    }

    private void processConceptSchemeMap(StructureSetType structureSetType, ConceptSchemeMapBean buildFrom) {
        ConceptSchemeMapType conceptSchemeMap = structureSetType.addNewConceptSchemeMap();

        if (validString(buildFrom.getId())) {
            conceptSchemeMap.setId(buildFrom.getId());
        }
        if (validCollection(buildFrom.getNames())) {
            conceptSchemeMap.setNameArray(getTextType(buildFrom.getNames()));
        }
        if (validCollection(buildFrom.getDescriptions())) {
            conceptSchemeMap.setDescriptionArray(getTextType(buildFrom.getDescriptions()));
        }
        if (buildFrom.getSourceRef() != null) {
            ConceptSchemeRefType refType = conceptSchemeMap.addNewConceptSchemeRef();
            setConceptSchemeRefAttributes(refType, buildFrom.getSourceRef());
        }
        if (buildFrom.getTargetRef() != null) {
            ConceptSchemeRefType refType = conceptSchemeMap.addNewTargetConceptSchemeRef();
            setConceptSchemeRefAttributes(refType, buildFrom.getTargetRef());
        }
        if (validCollection(buildFrom.getItems())) {
            for (ItemMapBean itemMapBean : buildFrom.getItems()) {
                ConceptMapType conceptMaptype = conceptSchemeMap.addNewConceptMap();
                if (validString(itemMapBean.getSourceId())) {
                    conceptMaptype.setConceptID(itemMapBean.getSourceId());
                }
                if (validString(itemMapBean.getTargetId())) {
                    conceptMaptype.setTargetConceptID(itemMapBean.getTargetId());
                }
            }
        }
        if (hasAnnotations(buildFrom)) {
            conceptSchemeMap.setAnnotations(getAnnotationsType(buildFrom));
        }
    }

    private void processOrganisationSchemeMap(StructureSetType structureSetType, OrganisationSchemeMapBean buildFrom) {
        OrganisationSchemeMapType orgSchemeMap = structureSetType.addNewOrganisationSchemeMap();

        if (validString(buildFrom.getId())) {
            orgSchemeMap.setId(buildFrom.getId());
        }
        if (validCollection(buildFrom.getNames())) {
            orgSchemeMap.setNameArray(getTextType(buildFrom.getNames()));
        }
        if (validCollection(buildFrom.getDescriptions())) {
            orgSchemeMap.setDescriptionArray(getTextType(buildFrom.getDescriptions()));
        }
        if (buildFrom.getSourceRef() != null) {
            OrganisationSchemeRefType refType = orgSchemeMap.addNewOrganisationSchemeRef();
            setOrganisationSchemeRefAttributes(refType, buildFrom.getSourceRef());
        }
        if (buildFrom.getTargetRef() != null) {
            OrganisationSchemeRefType refType = orgSchemeMap.addNewTargetOrganisationSchemeRef();
            setOrganisationSchemeRefAttributes(refType, buildFrom.getTargetRef());
        }
        if (validCollection(buildFrom.getItems())) {
            for (ItemMapBean itemMapBean : buildFrom.getItems()) {
                OrganisationMapType orgMaptype = orgSchemeMap.addNewOrganisationMap();
                if (validString(itemMapBean.getSourceId())) {
                    orgMaptype.setOrganisationID(itemMapBean.getSourceId());
                }
                if (validString(itemMapBean.getTargetId())) {
                    orgMaptype.setTargetOrganisationID(itemMapBean.getTargetId());
                }
            }
        }
        if (hasAnnotations(buildFrom)) {
            orgSchemeMap.setAnnotations(getAnnotationsType(buildFrom));
        }
    }

    private void setDataStructureRefAttributes(KeyFamilyRefType kfRefType, CrossReferenceBean ref) {
        MaintainableRefBean mRef = ref.getMaintainableReference();
        if (validString(mRef.getAgencyId())) {
            kfRefType.setKeyFamilyAgencyID(mRef.getAgencyId());
        }
        if (validString(mRef.getMaintainableId())) {
            kfRefType.setKeyFamilyID(mRef.getMaintainableId());
        }
        if (validString(ref.getTargetUrn())) {
            kfRefType.setURN(ref.getTargetUrn());
        }
        if (validString(mRef.getVersion())) {
            kfRefType.setVersion(mRef.getVersion());
        }
    }

    private void setMetadataStructureRefAttributes(MetadataStructureRefType refType, CrossReferenceBean ref) {
        MaintainableRefBean mRef = ref.getMaintainableReference();
        if (validString(mRef.getAgencyId())) {
            refType.setMetadataStructureAgencyID(mRef.getAgencyId());
        }
        if (validString(mRef.getMaintainableId())) {
            refType.setMetadataStructureID(mRef.getMaintainableId());
        }
        if (validString(ref.getTargetUrn())) {
            refType.setURN(ref.getTargetUrn());
        }
        if (validString(mRef.getVersion())) {
            refType.setVersion(mRef.getVersion());
        }
    }

    private void setCodelistRefAttributes(CodelistRefType refType, CrossReferenceBean ref) {
        MaintainableRefBean mRef = ref.getMaintainableReference();
        if (validString(mRef.getAgencyId())) {
            refType.setAgencyID(mRef.getAgencyId());
        }
        if (validString(mRef.getMaintainableId())) {
            refType.setCodelistID(mRef.getMaintainableId());
        }
        if (validString(ref.getTargetUrn())) {
            refType.setURN(ref.getTargetUrn());
        }
        if (validString(mRef.getVersion())) {
            refType.setVersion(mRef.getVersion());
        }
    }

    private void setConceptSchemeRefAttributes(ConceptSchemeRefType refType, CrossReferenceBean ref) {
        MaintainableRefBean mRef = ref.getMaintainableReference();
        if (validString(mRef.getAgencyId())) {
            refType.setAgencyID(mRef.getAgencyId());
        }
        if (validString(mRef.getMaintainableId())) {
            refType.setConceptSchemeID(mRef.getMaintainableId());
        }
        if (validString(ref.getTargetUrn())) {
            refType.setURN(ref.getTargetUrn());
        }
        if (validString(mRef.getVersion())) {
            refType.setVersion(mRef.getVersion());
        }
    }

    private void setCategorySchemeRefAttributes(CategorySchemeRefType refType, CrossReferenceBean ref) {
        MaintainableRefBean mRef = ref.getMaintainableReference();
        if (validString(mRef.getAgencyId())) {
            refType.setAgencyID(mRef.getAgencyId());
        }
        if (validString(mRef.getMaintainableId())) {
            refType.setCategorySchemeID(mRef.getMaintainableId());
        }
        if (validString(ref.getTargetUrn())) {
            refType.setURN(ref.getTargetUrn());
        }
        if (validString(mRef.getVersion())) {
            refType.setVersion(mRef.getVersion());
        }
    }

    private void setOrganisationSchemeRefAttributes(OrganisationSchemeRefType refType, CrossReferenceBean ref) {
        MaintainableRefBean mRef = ref.getMaintainableReference();
        if (validString(mRef.getAgencyId())) {
            refType.setAgencyID(mRef.getAgencyId());
        }
        if (validString(mRef.getMaintainableId())) {
            refType.setOrganisationSchemeID(mRef.getMaintainableId());
        }
        if (validString(ref.getTargetUrn())) {
            refType.setURN(ref.getTargetUrn());
        }
        if (validString(mRef.getVersion())) {
            refType.setVersion(mRef.getVersion());
        }
    }

    private void setHclRefAttributes(HierarchicalCodelistRefType refType, CrossReferenceBean ref) {
        MaintainableRefBean mRef = ref.getMaintainableReference();
        if (validString(mRef.getAgencyId())) {
            refType.setAgencyID(mRef.getAgencyId());
        }
        if (validString(mRef.getMaintainableId())) {
            refType.setHierarchicalCodelistID(mRef.getMaintainableId());
        }
        if (validString(ref.getTargetUrn())) {
            refType.setURN(ref.getTargetUrn());
        }
        if (validString(mRef.getVersion())) {
            refType.setVersion(mRef.getVersion());
        }
    }
}
